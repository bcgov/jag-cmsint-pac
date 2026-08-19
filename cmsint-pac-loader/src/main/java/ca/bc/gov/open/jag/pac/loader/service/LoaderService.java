package ca.bc.gov.open.jag.pac.loader.service;

import ca.bc.gov.open.jag.pac.loader.config.OrdsProperties;
import ca.bc.gov.open.jag.pac.loader.config.PacProperties;
import ca.bc.gov.open.pac.models.Client;
import ca.bc.gov.open.pac.models.ClientDto;
import ca.bc.gov.open.pac.models.eventStatus.PendingEventStatus;
import java.util.Arrays;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class LoaderService {
    private static final Logger log = LoggerFactory.getLogger(LoaderService.class);

    private final WebServiceTemplate webServiceTemplate;
    private final RestTemplate restTemplate;
    private final PacProperties pacProperties;
    private final OrdsProperties ordsProperties;
    private final AmqpTemplate rabbitTemplate;

    public LoaderService(
            WebServiceTemplate webServiceTemplate,
            RestTemplate restTemplate,
            OrdsProperties ordsProperties,
            PacProperties pacProperties,
            AmqpTemplate rabbitTemplate) {
        this.webServiceTemplate = webServiceTemplate;
        this.restTemplate = restTemplate;
        this.ordsProperties = ordsProperties;
        this.pacProperties = pacProperties;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void processPAC(ClientDto clientDto) {
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

    public void sendToQueue(Client client) {
        this.rabbitTemplate.convertAndSend(
                pacProperties.getExchangeName(), pacProperties.getPacRoutingKey(), client.Dto());
    }

    public void updateToConnectionError(Client client) {
        client.getStatus()
                .setRestTemplate(getRestTemplate())
                .setOrdsProperties(getOrdsProperties())
                .updateToConnectionError(client);
    }

    public void updateToApplicationError(Client client) {
        client.getStatus()
                .setRestTemplate(getRestTemplate())
                .setOrdsProperties(getOrdsProperties())
                .updateToApplicationError(client);
    }


    public WebServiceTemplate getWebServiceTemplate() {
        return webServiceTemplate;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public PacProperties getPacProperties() {
        return pacProperties;
    }

    public OrdsProperties getOrdsProperties() {
        return ordsProperties;
    }

    public AmqpTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof LoaderService)) return false;
        LoaderService other = (LoaderService) o;
        return other.canEqual(this)
                && Objects.equals(this.webServiceTemplate, other.webServiceTemplate)
                && Objects.equals(this.restTemplate, other.restTemplate)
                && Objects.equals(this.pacProperties, other.pacProperties)
                && Objects.equals(this.ordsProperties, other.ordsProperties)
                && Objects.equals(this.rabbitTemplate, other.rabbitTemplate);
    }

    protected boolean canEqual(Object other) {
        return other instanceof LoaderService;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                webServiceTemplate,
                restTemplate,
                pacProperties,
                ordsProperties,
                rabbitTemplate);
    }

    @Override
    public String toString() {
        return "LoaderService("
                + "webServiceTemplate=" + webServiceTemplate
                + ", restTemplate=" + restTemplate
                + ", pacProperties=" + pacProperties
                + ", ordsProperties=" + ordsProperties
                + ", rabbitTemplate=" + rabbitTemplate
                + ")";
    }
}
