package ca.bc.gov.open.pac.models.eventStatus;

import ca.bc.gov.open.pac.loader.EventLoader;
import ca.bc.gov.open.pac.loader.NewEventLoader;
import ca.bc.gov.open.pac.models.Client;
import ca.bc.gov.open.pac.models.LoaderPacPropertiesInterface;
import ca.bc.gov.open.pac.models.OrdsPropertiesInterface;
import java.io.Serializable;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;

@NoArgsConstructor
@Slf4j
public class NewEventStatus extends EventStatus implements Serializable {

    public static final String METHOD_NAME = "updateToPending";

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
