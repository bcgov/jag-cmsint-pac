package ca.bc.gov.pac.open.jag.pac.consumer.services;

import ca.bc.gov.open.pac.models.Client;
import ca.bc.gov.open.pac.models.OrdsErrorLog;
import ca.bc.gov.open.pac.models.RequestSuccessLog;
import ca.bc.gov.open.pac.models.exceptions.ORDSException;
import ca.bc.gov.pac.open.jag.pac.consumer.model.EventTypeEnum;
import ca.bc.gov.pac.open.jag.pac.consumer.model.SynchronizeClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
@Slf4j
public class PACService {
    @Value("${pac.ords.host}")
    private String cmsHost = "https://127.0.0.1/";

    @Value("${icon.pac-service-url}")
    private String pacServiceUrl = "https://127.0.0.1/";

    @Value("${pac.ords.endpoints.success}")
    private String ordsSuccessEndpoint;

    private final WebServiceTemplate webServiceTemplate;
    private final RestTemplate restTemplate;

    @Autowired
    public PACService(
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            WebServiceTemplate webServiceTemplate) {
        this.restTemplate = restTemplate;
        this.webServiceTemplate = webServiceTemplate;
    }

    // PACUpdate BPM
    public void processPAC(Client client) throws JsonProcessingException {

        SynchronizeClient synchronizeClient = composeSoapServiceRequestBody(client);

        invokeSoapService(synchronizeClient);

        pacSuccess(client);
        // End of BPM
    }

    private void pacSuccess(Client client, Client eventClient) throws JsonProcessingException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(cmsHost + ordsSuccessEndpoint)
                        .queryParam("clientNumber", client.getClientNumber())
                        .queryParam("eventSeqNum", client.getEventSeqNum())
                        .queryParam("computerSystemCd", client.getComputerSystemCd());
        try {
            restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    new HttpEntity<>(client, new HttpHeaders()),
                    new ParameterizedTypeReference<>() {});
            log.info(new RequestSuccessLog("Request Success", "updateSuccess").toString());
            if (eventClient.getStatus().equals("0")) log.info("PAC update success");
        } catch (Exception ex) {
            log.error(
                    new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "updateSuccess",
                                    ex.getMessage(),
                                    client)
                            .toString());
            throw new ORDSException();
        }
    }

    private void invokeSoapService(SynchronizeClient synchronizeClient)
            throws JsonProcessingException {
        // Invoke Soap Service
        try {
            webServiceTemplate.marshalSendAndReceive(pacServiceUrl, synchronizeClient);
            log.info(new RequestSuccessLog("Request Success", "synchronizeClient").toString());
        } catch (Exception ex) {
            log.error(
                    new OrdsErrorLog(
                                    "Error received from SOAP SERVICE - synchronizeClient",
                                    "pacUpdate",
                                    ex.getMessage(),
                                    synchronizeClient)
                            .toString());
        }
    }

    private SynchronizeClient composeSoapServiceRequestBody(Client client) {
        // Compose Soap Service Request Body
        SynchronizeClient synchronizeClient = null;
        try {
            synchronizeClient =
                    EventTypeEnum.valueOf(client.getEventTypeCode()).getSynchronizeClient(client);
        } catch (IllegalArgumentException e) {
            log.warn("Received EventTypeCode " + client.getEventTypeCode() + " is not expected");
        }
        return synchronizeClient;
    }

    private HttpEntity<Client> sendUpdateRequest(Client client) {
        UriComponentsBuilder builder;
        builder = UriComponentsBuilder.fromHttpUrl(cmsHost + ordsSuccessEndpoint);
        HttpEntity<Client> respClient =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.POST,
                        new HttpEntity<>(client, new HttpHeaders()),
                        new ParameterizedTypeReference<>() {});

        log.info(new RequestSuccessLog("Request Success", "pacUpdate").toString());

        return respClient;
    }

    private HttpEntity<Client> pacSuccess(Client client) {
        try {
            HttpEntity<Client> respClient = sendUpdateRequest(client);
            if (respClient.getBody().getStatus().equals("0")) {
                log.info("PAC update cancel");
                return null;
            }
            return respClient;
        } catch (Exception ex) {
            log.error(
                    new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "pacUpdate",
                                    ex.getMessage(),
                                    client)
                            .toString());
            throw new ORDSException();
        }
    }
}
