package ca.bc.gov.open.pac.models.eventStatus;

import ca.bc.gov.open.pac.loader.EventLoader;
import ca.bc.gov.open.pac.models.*;
import ca.bc.gov.open.pac.models.exceptions.ORDSException;
import ca.bc.gov.open.pac.models.ords.UpdateEntryEntity;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.ws.client.core.WebServiceTemplate;

public abstract class EventStatus {
    private static final Logger log = LoggerFactory.getLogger(EventStatus.class);

    protected transient RestTemplate restTemplate;
    protected transient OrdsPropertiesInterface ordsProperties;

    public EventStatus() {}

    public EventStatus(OrdsPropertiesInterface ordsProperties, RestTemplate restTemplate) {
        this.ordsProperties = ordsProperties;
        this.restTemplate = restTemplate;
    }

    /**
     * Both fields of this class are {@code transient}, and Lombok's {@code @EqualsAndHashCode}
     * excluded transient fields. The generated equals therefore compared no state at all, making
     * any two {@code EventStatus} instances equal regardless of subtype. That behaviour is
     * preserved here verbatim so existing tests keep passing.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof EventStatus)) return false;
        EventStatus other = (EventStatus) o;
        return other.canEqual(this);
    }

    protected boolean canEqual(Object other) {
        return other instanceof EventStatus;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public String getClientInfoLoggingStr(Client client) {
        return "Client:"
                + client.getClientNumber()
                + " EventTypeCd:"
                + client.getEventTypeCode()
                + " SeqNum:"
                + client.getEventSeqNum();
    }

    public Client updateToPending(Client client) {
        throw new UnsupportedOperationException(
                "The status of the event cannot be updated to Pending");
    }

    public Client updateToCompletedDuplicate(Client client) {
        throw new UnsupportedOperationException(
                "The status of the event cannot be updated to Completed Duplicate");
    }

    public Client updateToInProgress(Client client) {
        throw new UnsupportedOperationException(
                "The status of the event cannot be updated to In Progress");
    }

    public Client updateToCompletedOk(Client client) {
        throw new UnsupportedOperationException(
                "The status of the event cannot be updated to Completed OK");
    }

    public Client updateToConnectionError(Client client) {
        throw new UnsupportedOperationException(
                "The status of the event cannot be updated to Connection Error");
    }

    public Client updateToApplicationError(Client client) {
        throw new UnsupportedOperationException(
                "The status of the event cannot be updated to Application Error");
    }

    protected void updateStatusOnServer(Client client, EventStatusCode eventStatusCode) {
        URI url =
                UriComponentsBuilder.fromHttpUrl(
                                ordsProperties.getCmsBaseUrl()
                                        + ordsProperties.getEntriesEndpoint())
                        .build()
                        .toUri();

        try {
            restTemplate.put(url, new UpdateEntryEntity(client, eventStatusCode));
            log.info(new RequestSuccessLog("Request Success", url.getPath()).toString());
        } catch (Exception ex) {
            log.error(
                    new OrdsErrorLog(
                                    "Error received from ORDS",
                                    getMethodName(),
                                    ex.getMessage(),
                                    client)
                            .toString());
            throw new ORDSException(
                    "client "
                            + client
                            + "could failed to update the status to "
                            + eventStatusCode.getCode());
        }
    }

    protected abstract String getMethodName();

    public EventStatus setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        return this;
    }

    public EventStatus setOrdsProperties(OrdsPropertiesInterface ordsProperties) {
        this.ordsProperties = ordsProperties;
        return this;
    }

    public abstract EventLoader getLoader(
            WebServiceTemplate webServiceTemplate, LoaderPacPropertiesInterface pacProperties);
}
