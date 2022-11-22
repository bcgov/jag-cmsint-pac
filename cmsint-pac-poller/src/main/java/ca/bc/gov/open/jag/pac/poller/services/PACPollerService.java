package ca.bc.gov.open.jag.pac.poller.services;

import ca.bc.gov.open.jag.pac.poller.config.QueueConfig;
import ca.bc.gov.open.pac.models.Client;
import ca.bc.gov.open.pac.models.OrdsErrorLog;
import ca.bc.gov.open.pac.models.RequestSuccessLog;
import ca.bc.gov.open.pac.models.exceptions.ORDSException;
import ca.bc.gov.open.pac.models.ords.DemographicsEntity;
import ca.bc.gov.open.pac.models.ords.EventEntity;
import ca.bc.gov.open.pac.models.ords.NewerEventEntity;
import ca.bc.gov.open.pac.models.ords.OrdsProperties;
import ca.bc.gov.open.pac.models.ords.ProcessEntity;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
// @Slf4j
public class PACPollerService {
    private static final Logger log = LogManager.getLogger(PACPollerService.class);

    private final OrdsProperties ordProperties;

    private final Queue pacQueue;

    private final RestTemplate restTemplate;

    private final RabbitTemplate rabbitTemplate;

    private final AmqpAdmin amqpAdmin;

    private final QueueConfig queueConfig;

    public PACPollerService(
            OrdsProperties ordsProperties,
            @Qualifier("pac-queue") Queue pacQueue,
            RestTemplate restTemplate,
            RabbitTemplate rabbitTemplate,
            AmqpAdmin amqpAdmin,
            QueueConfig queueConfig) {
        this.ordProperties = ordsProperties;
        this.pacQueue = pacQueue;
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
        this.amqpAdmin = amqpAdmin;
        this.queueConfig = queueConfig;
    }

    @PostConstruct
    public void createQueues() {
        amqpAdmin.declareQueue(pacQueue);
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void pollOrdsForNewRecords() {
        log.info("Polling db for new records");

        try {
            List<ProcessEntity> processesEntity = getNewProcesses();

            log.info("Pulled " + processesEntity.size() + " new records");

            processesEntity.stream()
                    .map(this::getEventForProcess)
                    .map(client -> client.getStatus().updateToPending(client))
                    .map(this::getClientNewerSequence)
                    .map(this::getDemographicsInfo)
                    .forEach(this::sendToRabbitMq);

        } catch (Exception ex) {
            log.error("Failed to pull new records from the db: " + ex.getMessage());
        }
    }

    public Client getDemographicsInfo(Client client) {
        URI url =
                UriComponentsBuilder.fromHttpUrl(
                                ordProperties.getCmsBaseUrl()
                                        + ordProperties.getDemographicsEndpoint())
                        .queryParam("clientId", client.getClientNumber())
                        .queryParam("eventTypeCode", client.getEventTypeCode())
                        .build()
                        .toUri();
        try {
            DemographicsEntity demographicsEntity =
                    restTemplate.getForObject(url, DemographicsEntity.class);

            if (demographicsEntity == null)
                throw new NullPointerException("Response object from " + url.getPath() + "is null");

            return new Client(client, demographicsEntity);
        } catch (Exception ex) {
            logError(url.getPath(), ex, null);
            throw new ORDSException();
        }
    }

    public Client getClientNewerSequence(Client client) {
        URI url =
                UriComponentsBuilder.fromHttpUrl(
                                ordProperties.getCmsIntBaseUrl()
                                        + ordProperties.getProcessesEndpoint())
                        .queryParam("state", "NEW")
                        .build()
                        .toUri();

        try {
            NewerEventEntity newerEventEntity =
                    restTemplate.getForObject(url, NewerEventEntity.class);

            log.info(new RequestSuccessLog("Request Success", url.getPath()).toString());

            if (newerEventEntity == null)
                throw new NullPointerException("Response object from " + url.getPath() + "is null");

            if (!newerEventEntity.hasNewerEvent())
                client.getStatus().updateToCompletedDuplicate(client);

            return client;
        } catch (Exception ex) {
            logError(url.getPath(), ex, null);
            throw new ORDSException();
        }
    }

    public List<ProcessEntity> getNewProcesses() {
        URI url =
                UriComponentsBuilder.fromHttpUrl(
                                ordProperties.getCmsIntBaseUrl()
                                        + ordProperties.getProcessesEndpoint())
                        .queryParam("state", "NEW")
                        .build()
                        .toUri();
        try {

            ProcessEntity[] processEntityArray =
                    restTemplate.getForObject(url, ProcessEntity[].class);

            log.info(new RequestSuccessLog("Request Success", url.getPath()).toString());

            if (processEntityArray == null) return Collections.emptyList();

            return Arrays.asList(processEntityArray);

        } catch (Exception ex) {
            logError(url.getPath(), ex, null);
            throw new ORDSException();
        }
    }

    private void logError(String method, Exception ex, Object request) throws ORDSException {
        String ordsErrormessage =
                new OrdsErrorLog(
                                "Error received from ORDS",
                                "getEventType",
                                ex.getMessage(),
                                request)
                        .toString();

        log.error(ordsErrormessage);
    }

    public void sendToRabbitMq(Client client) {
        this.rabbitTemplate.convertAndSend(
                queueConfig.getTopicExchangeName(), queueConfig.getPacRoutingkey(), client);
    }

    //  Scheduled every minute in MS

    public Client getEventForProcess(ProcessEntity processEntity) throws ORDSException {
        URI url =
                UriComponentsBuilder.fromHttpUrl(
                                ordProperties.getCmsIntBaseUrl()
                                        + ordProperties.getEventsEndpoint())
                        .queryParam("clientId", processEntity.getClientNumber())
                        .queryParam("eventSeqNum", processEntity.getEventSeqNum())
                        .build()
                        .toUri();
        try {
            EventEntity eventEntity = restTemplate.getForObject(url, EventEntity.class);

            log.info(new RequestSuccessLog("Request Success", url.getPath()).toString());

            if (eventEntity == null)
                throw new NullPointerException("Response object from " + url.getPath() + "is null");

            return new Client(processEntity, eventEntity);

        } catch (Exception ex) {
            logError(url.getPath(), ex, null);
            throw new ORDSException();
        }
    }

    private Client pacUpdateClient(Client client) {
        URI url =
                UriComponentsBuilder.fromHttpUrl(
                                ordProperties.getCmsIntBaseUrl()
                                        + ordProperties.getSuccessEndpoint())
                        .build()
                        .toUri();
        try {
            HttpEntity<Client> respClient =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            new HttpEntity<>(client, new HttpHeaders()),
                            new ParameterizedTypeReference<>() {});

            log.info(
                    new RequestSuccessLog("Request Success", ordProperties.getSuccessEndpoint())
                            .toString());
            return respClient.getBody();

        } catch (Exception ex) {
            logError(url.getPath(), ex, null);
            throw new ORDSException();
        }
    }
}
