package ca.bc.gov.open.pac.models.eventStatus;

import ca.bc.gov.open.pac.loader.EventLoader;
import ca.bc.gov.open.pac.loader.InProgressEventLoader;
import ca.bc.gov.open.pac.models.Client;
import ca.bc.gov.open.pac.models.LoaderPacPropertiesInterface;
import ca.bc.gov.open.pac.models.OrdsPropertiesInterface;
import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;

@Slf4j
public class InProgressEventStatus extends EventStatus implements Serializable {

    public static final String METHOD_NAME = "updateToCompletedDuplicate";

    public InProgressEventStatus(
            OrdsPropertiesInterface ordsProperties, RestTemplate restTemplate) {
        super(ordsProperties, restTemplate);
    }

    @Override
    protected String getMethodName() {
        return METHOD_NAME;
    }

    @Override
    public Client updateToCompletedOk(Client client) {
        log.info(
                super.getClientInfoLoggingStr(client)
                        + " status to "
                        + EventStatusCode.COMPLETED_OK);
        client.setStatus(new InProgressEventStatus(ordsProperties, restTemplate));

        updateStatusOnServer(client, EventStatusCode.COMPLETED_OK);

        return client;
    }

    @Override
    public Client updateToConnectionError(Client client) {
        log.info(
                super.getClientInfoLoggingStr(client)
                        + " status to "
                        + EventStatusCode.CONNECTION_ERROR);

        client.setStatus(new ConnectionErrorEventStatus(ordsProperties, restTemplate));

        updateStatusOnServer(client, EventStatusCode.CONNECTION_ERROR);

        return client;
    }

    @Override
    public Client updateToApplicationError(Client client) {
        log.info(
                super.getClientInfoLoggingStr(client)
                        + " status to "
                        + EventStatusCode.APPLICATION_ERROR);

        client.setStatus(new ApplicationErrorEventStatus(ordsProperties, restTemplate));

        updateStatusOnServer(client, EventStatusCode.APPLICATION_ERROR);

        return client;
    }

    @Override
    public EventLoader getLoader(
            WebServiceTemplate webServiceTemplate, LoaderPacPropertiesInterface pacProperties) {
        return new InProgressEventLoader(webServiceTemplate, pacProperties);
    }
}
