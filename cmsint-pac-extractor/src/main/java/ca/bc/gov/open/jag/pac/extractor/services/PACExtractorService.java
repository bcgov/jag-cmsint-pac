package ca.bc.gov.open.jag.pac.extractor.services;

import ca.bc.gov.open.jag.pac.extractor.config.OrdsProperties;
import ca.bc.gov.open.jag.pac.extractor.config.QueueConfig;
import ca.bc.gov.open.pac.models.Client;
import ca.bc.gov.open.pac.models.OrdsErrorLog;
import ca.bc.gov.open.pac.models.RequestSuccessLog;
import ca.bc.gov.open.pac.models.eventStatus.CompletedDuplicateEventStatus;
import ca.bc.gov.open.pac.models.exceptions.ORDSException;
import ca.bc.gov.open.pac.models.ords.DemographicsEntity;
import ca.bc.gov.open.pac.models.ords.EventEntity;
import ca.bc.gov.open.pac.models.ords.NewerEventEntity;
import ca.bc.gov.open.pac.models.ords.ProcessEntity;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class PACExtractorService {

    private final OrdsProperties ordsProperties;

    private final Queue pacQueue;

    private final RestTemplate restTemplateCMSInt;

    private final RestTemplate restTemplateCMS;

    private final RabbitTemplate rabbitTemplate;

    private final AmqpAdmin amqpAdmin;

    private final QueueConfig queueConfig;

    private final ObjectMapper objectMapper;

    public PACExtractorService(
            OrdsProperties ordsProperties,
            @Qualifier("pac-queue") Queue pacQueue,
            @Qualifier("restTemplateCMSInt") RestTemplate restTemplateCMSInt,
            @Qualifier("restTemplateCMS") RestTemplate restTemplateCMS,
            RabbitTemplate rabbitTemplate,
            AmqpAdmin amqpAdmin,
            QueueConfig queueConfig,
            ObjectMapper objectMapper) {
        this.ordsProperties = ordsProperties;
        this.pacQueue = pacQueue;
        this.restTemplateCMSInt = restTemplateCMSInt;
        this.restTemplateCMS = restTemplateCMS;
        this.rabbitTemplate = rabbitTemplate;
        this.amqpAdmin = amqpAdmin;
        this.queueConfig = queueConfig;
        this.objectMapper = objectMapper;
    }

    private static class QueryParam {
        private final String name;
        private final List<String> values;

        public QueryParam(String name, String... values) {
            this.name = name;
            this.values = Arrays.asList(values);
        }
    }

    @PostConstruct
    public void createQueues() {
        amqpAdmin.declareQueue(pacQueue);
    }

    @Scheduled(cron = "${pac.extractor-interval-cron}")
    public void pollOrdsForNewRecords() {

        try {
            List<ProcessEntity> processesEntity = getNewProcesses(); // cmsintords/pac/v1/processes
            log.info("Pulled " + processesEntity.size() + " new records");

            // cmsintords/pac/v1/events
            // cmsords/pac/v1/entries
            // cmsords/pac/v1/events
            // cmsords/pac/v1/demographics
            for (ProcessEntity processEntity : processesEntity) {
                Client client = getEventForProcess(processEntity);
                Client updateToPending = client.getStatus()
                        .updateToPending(client);
                Client clientNewerSequence = getClientNewerSequence(updateToPending);
                if (clientNewerSequence.getStatus().getClass()
                        != CompletedDuplicateEventStatus.class) {
                    Client demographicsInfo = getDemographicsInfo(clientNewerSequence);
                    sendToRabbitMq(demographicsInfo);
                }
            }
        } catch (Exception ex) {
            log.error("Failed to pull new records from the db: " + ex.getMessage());
        }
    }

    public Client getDemographicsInfo(Client client) {
        URI url =
                getUri(
                        ordsProperties.getCmsBaseUrl() + ordsProperties.getDemographicsEndpoint(),
                        Arrays.asList(
                                new QueryParam("clientNumber", client.getClientNumber()),
                                new QueryParam("eventTypeCode", client.getEventTypeCode())));
        try {
            DemographicsEntity demographicsEntity =
                    restTemplateCMS.getForObject(url, DemographicsEntity.class);

            if (demographicsEntity == null) {
                throw new NullPointerException(
                        "Response object from " + url.getPath() + " is null");
            }
            // TODO:
            log.info("TODO: getDemographicsInfo: " + objectMapper.writeValueAsString(demographicsEntity));
            return new Client(client, demographicsEntity);
        } catch (Exception ex) {
            logError(url.getPath(), ex, null);
            throw new ORDSException();
        }
    }

    private URI getUri(String httpUrl, List<QueryParam> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(httpUrl);
        queryParams.forEach(queryParam -> builder.queryParam(queryParam.name, queryParam.values));
        return builder.build().toUri();
    }

    private URI getUri(String httpUrl, QueryParam queryParam) {
        return getUri(httpUrl, Collections.singletonList(queryParam));
    }

    private URI getUri(String httpUrl) {
        return getUri(httpUrl, Collections.emptyList());
    }

    public Client getClientNewerSequence(Client client) {
        URI url =
                getUri(
                        ordsProperties.getCmsBaseUrl() + ordsProperties.getEventsEndpoint(),
                        Arrays.asList(
                                new QueryParam("clientNumber", client.getClientNumber()),
                                new QueryParam("eventSeqNum", client.getEventSeqNum()),
                                new QueryParam("eventTypeCode", client.getEventTypeCode())));

        try {
            NewerEventEntity newerEventEntity =
                    restTemplateCMS.getForObject(url, NewerEventEntity.class);
            log.info(new RequestSuccessLog("Request Success", url.getPath()).toString());

            if (newerEventEntity == null) {
                throw new NullPointerException(
                        "Response object from " + url.getPath() + " is null");
            }

            if (newerEventEntity.hasNewerEvent()) {
                client.getStatus().updateToCompletedDuplicate(client);
            }

            return client;
        } catch (Exception ex) {
            logError(url.getPath(), ex, null);
            throw new ORDSException();
        }
    }

    public List<ProcessEntity> getNewProcesses() {
        URI url =
                getUri(
                        ordsProperties.getCmsIntBaseUrl() + ordsProperties.getProcessesEndpoint(),
                        new QueryParam("state", "NEW"));
        try {
            ProcessEntity[] processEntityArray =
                    restTemplateCMSInt.getForObject(url, ProcessEntity[].class);
            // For simplification, no success log on every polling attempt
            // log.info(new RequestSuccessLog("Request Success", url.getPath()).toString());

            if (processEntityArray == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(processEntityArray);
        } catch (Exception ex) {
            logError(url.getPath(), ex, null);
            throw new ORDSException();
        }
    }

    private void logError(String method, Exception ex, Object request) throws ORDSException {
        String ordsErrormessage =
                new OrdsErrorLog("Error received from ORDS", method, ex.getMessage(), request)
                        .toString();

        log.error(ordsErrormessage);
    }

    public void sendToRabbitMq(Client client) throws JsonProcessingException {
        if (client != null) {
            this.rabbitTemplate.convertAndSend(
                    queueConfig.getTopicExchangeName(), queueConfig.getPacRoutingkey(), client.Dto());
            log.info(
                    "Enqueued Client:"
                            + client.getClientNumber()
                            + " EventType:"
                            + client.getEventTypeCode()
                            + " SeqNum:"
                            + client.getEventSeqNum());
            // TODO:
            log.info("TODO: getDemographicsInfo: " + objectMapper.writeValueAsString(client.Dto()));
        }
    }

    public Client getEventForProcess(ProcessEntity processEntity) throws ORDSException {
        URI url =
                getUri(
                        ordsProperties.getCmsIntBaseUrl() + ordsProperties.getEventsEndpoint(),
                        Arrays.asList(
                                new QueryParam("eventSeqNum", processEntity.getEventSeqNum()),
                                new QueryParam("clientNumber", processEntity.getClientNumber())));
        try {
            EventEntity eventEntity = restTemplateCMSInt.getForObject(url, EventEntity.class);
            log.info(new RequestSuccessLog("Request Success", url.getPath()).toString());

            if (eventEntity == null) {
                throw new NullPointerException("Response object from " + url.getPath() + "is null");
            }
            return new Client(processEntity, eventEntity, restTemplateCMS, ordsProperties);
        } catch (Exception ex) {
            logError(url.getPath(), ex, null);
            throw new ORDSException();
        }
    }
}
