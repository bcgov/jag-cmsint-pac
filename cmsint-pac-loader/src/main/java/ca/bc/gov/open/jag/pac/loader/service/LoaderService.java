package ca.bc.gov.open.jag.pac.loader.service;

import ca.bc.gov.open.jag.pac.loader.config.OrdsProperties;
import ca.bc.gov.open.jag.pac.loader.config.PacProperties;
import ca.bc.gov.open.pac.models.Client;
import ca.bc.gov.open.pac.models.ClientDto;
import ca.bc.gov.open.pac.models.eventStatus.PendingEventStatus;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
@Slf4j
@Data
public class LoaderService {

    private final WebServiceTemplate webServiceTemplate;
    private final RestTemplate restTemplate;
    private final PacProperties pacProperties;
    private final OrdsProperties ordsProperties;
    private final AmqpTemplate rabbitTemplate;

    private ObjectMapper objectMapper;

    public LoaderService(
            WebServiceTemplate webServiceTemplate,
            RestTemplate restTemplate,
            OrdsProperties ordsProperties,
            PacProperties pacProperties,
            AmqpTemplate rabbitTemplate,
            ObjectMapper objectMapper) {
        this.webServiceTemplate = webServiceTemplate;
        this.restTemplate = restTemplate;
        this.ordsProperties = ordsProperties;
        this.pacProperties = pacProperties;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void processPAC(ClientDto clientDto) throws JsonProcessingException {
        Client client = clientDto.toClient();
        var status = client.getStatus().getClass();
        var statesThatShouldNotBeProcessed = Arrays.asList(PendingEventStatus.class);
        if (statesThatShouldNotBeProcessed.contains(status)) {
            sendToQueue(client);
            return;
        }

        client.getStatus()
                .setOrdsProperties(ordsProperties)
                .setRestTemplate(restTemplate)
                .getLoader(webServiceTemplate, pacProperties)
                .process(client);
    }

    public void sendToQueue(Client client) throws JsonProcessingException {
        // TODO:
        log.info(" sendToQueue: " + objectMapper.writeValueAsString(client.Dto()));

        this.rabbitTemplate.convertAndSend(
                pacProperties.getExchangeName(), pacProperties.getPacRoutingKey(), client.Dto());
    }

    public void updateToConnectionError(Client client) {
        client.getStatus()
                .setRestTemplate(getRestTemplate())
                .setOrdsProperties(getOrdsProperties())
                .updateToConnectionError(client);
    }
}
