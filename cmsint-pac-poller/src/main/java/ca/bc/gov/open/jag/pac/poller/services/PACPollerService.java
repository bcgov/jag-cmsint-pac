package ca.bc.gov.open.jag.pac.poller.services;

import ca.bc.gov.open.jag.pac.poller.config.OrdsProperties;
import ca.bc.gov.open.jag.pac.poller.config.QueueConfig;
import ca.bc.gov.open.pac.models.Client;
import ca.bc.gov.open.pac.models.OrdsErrorLog;
import ca.bc.gov.open.pac.models.RequestSuccessLog;
import ca.bc.gov.open.pac.models.exceptions.ORDSException;
import ca.bc.gov.open.pac.models.ords.EventTypeCodeEntity;
import ca.bc.gov.open.pac.models.ords.NewEventEntity;
import java.net.URI;
import java.util.Arrays;
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
import org.springframework.http.ResponseEntity;
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
            HttpEntity<NewEventEntity[]> eventsEntity = getNewEvents();

            if (eventsEntity.hasBody()) {
                List<NewEventEntity> newEventsEnity = Arrays.asList(eventsEntity.getBody());
                log.info("Pulled " + newEventsEnity.size() + " new records");

                newEventsEnity.stream().map(this::getEventType);
                //                        .map(this::pacUpdateClient)
                //                        .forEach(this::sendToRabbitMq);
            }
        } catch (Exception ex) {
            log.error("Failed to pull new records from the db: " + ex.getMessage());
        }
    }

    public HttpEntity<NewEventEntity[]> getNewEvents() {
        URI url =
                UriComponentsBuilder.fromHttpUrl(
                                ordProperties.getBaseUrl() + ordProperties.getEventsEndpoint())
                        .build()
                        .toUri();

        ResponseEntity<NewEventEntity[]> resp =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        NewEventEntity[].class);
        return resp;
    }

    public void sendToRabbitMq(Client client) {
        this.rabbitTemplate.convertAndSend(
                queueConfig.getTopicExchangeName(), queueConfig.getPacRoutingkey(), client);
    }

    //  Scheduled every minute in MS

    public Client getEventType(NewEventEntity newEventEntity) throws ORDSException {
        URI uri =
                UriComponentsBuilder.fromHttpUrl(
                                ordProperties.getBaseUrl() + ordProperties.getEventsTypeEndpoint())
                        .queryParam("clientNumber", newEventEntity.getClientNumber())
                        .queryParam("eventSeqNum", newEventEntity.getEventSeqNum())
                        .build()
                        .toUri();
        try {
            ResponseEntity<EventTypeCodeEntity> resp =
                    restTemplate.exchange(
                            uri,
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            EventTypeCodeEntity.class);

            log.info(new RequestSuccessLog("Request Success", "getEventType").toString());

            return new Client(newEventEntity, resp.getBody());

        } catch (Exception ex) {
            log.error(
                    new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getEventType",
                                    ex.getMessage(),
                                    newEventEntity)
                            .toString());
            throw new ORDSException();
        }
    }

    private Client pacUpdateClient(Client client) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(
                        ordProperties.getBaseUrl() + ordProperties.getSuccessEndpoint());
        try {
            HttpEntity<Client> respClient =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            new HttpEntity<>(client, new HttpHeaders()),
                            new ParameterizedTypeReference<>() {});

            log.info(
                    new RequestSuccessLog("Request Success", ordProperties.getSuccessEndpoint())
                            .toString());
            return respClient.getBody();

        } catch (Exception ex) {

            log.error(
                    new OrdsErrorLog(
                                    "Error received from ORDS",
                                    ordProperties.getSuccessEndpoint(),
                                    ex.getMessage(),
                                    client)
                            .toString());

            throw new ORDSException();
        }
    }
}
