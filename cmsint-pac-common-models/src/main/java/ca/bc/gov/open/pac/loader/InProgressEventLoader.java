package ca.bc.gov.open.pac.loader;

import ca.bc.gov.open.pac.models.*;
import ca.bc.gov.open.pac.models.eventTypeCode.EventTypeEnum;
import ca.bc.gov.open.pac.models.exceptions.ORDSException;
import com.health.phis.ws.SynchronizeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.WebServiceTemplate;

@Slf4j
public class InProgressEventLoader implements EventLoader {

    private final WebServiceTemplate webServiceTemplate;
    private final LoaderPacPropertiesInterface pacProperties;

    public InProgressEventLoader(
            WebServiceTemplate webServiceTemplate, LoaderPacPropertiesInterface pacProperties) {
        this.pacProperties = pacProperties;
        this.webServiceTemplate = webServiceTemplate;
    }

    private void invokeSoapService(SynchronizeClient synchronizeClient) {
        // Invoke Soap Service
        try {
            log.info("Client to SOAP Service: " + synchronizeClient.toString());

            webServiceTemplate.marshalSendAndReceive(
                    pacProperties.getServiceUrl(), synchronizeClient);
            log.info(new RequestSuccessLog("Request Success", "synchronizeClient").toString());
        } catch (Exception ex) {
            log.error(
                    new OrdsErrorLog(
                                    "Error received from SOAP SERVICE - synchronizeClient",
                                    "pacUpdate",
                                    ex.getMessage(),
                                    synchronizeClient)
                            .toString());
            throw new ORDSException(synchronizeClient.toString());
        }
    }

    private SynchronizeClient getSynchronizeClientEntity(Client client) {
        // Compose Soap Service Request Body
        if (EventTypeEnum.hasValue(client.getEventTypeCode())) {
            return EventTypeEnum.valueOf(client.getEventTypeCode()).getSynchronizeClient(client);
        }
        throw new IllegalArgumentException(
                "Received EventTypeCode " + client.getEventTypeCode() + " is not expected");
    }

    @Override
    public void process(Client client) {
        var SynchronizeClient = getSynchronizeClientEntity(client);
        invokeSoapService(SynchronizeClient);
        client.getStatus().updateToCompletedOk(client);
    }
}
