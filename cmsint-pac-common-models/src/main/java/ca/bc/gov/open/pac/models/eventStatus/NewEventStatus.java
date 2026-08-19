package ca.bc.gov.open.pac.models.eventStatus;

import ca.bc.gov.open.pac.loader.EventLoader;
import ca.bc.gov.open.pac.loader.NewEventLoader;
import ca.bc.gov.open.pac.models.Client;
import ca.bc.gov.open.pac.models.LoaderPacPropertiesInterface;
import ca.bc.gov.open.pac.models.OrdsPropertiesInterface;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;

public class NewEventStatus extends EventStatus implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(NewEventStatus.class);

    public static final String METHOD_NAME = "updateToPending";

    public NewEventStatus() {}

    public NewEventStatus(OrdsPropertiesInterface ordsProperties, RestTemplate restTemplate) {
        super(ordsProperties, restTemplate);
    }

    @Override
    public Client updateToPending(Client client) {
        log.info(super.getClientInfoLoggingStr(client) + " status to " + EventStatusCode.PENDING);

        client.setStatus(new PendingEventStatus(ordsProperties, restTemplate));

        updateStatusOnServer(client, EventStatusCode.PENDING);

        return client;
    }

    @Override
    protected String getMethodName() {
        return METHOD_NAME;
    }

    @Override
    public EventLoader getLoader(
            WebServiceTemplate webServiceTemplate, LoaderPacPropertiesInterface pacProperties) {
        return new NewEventLoader();
    }
}
