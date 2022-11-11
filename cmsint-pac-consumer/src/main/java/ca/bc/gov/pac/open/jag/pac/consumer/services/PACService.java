package ca.bc.gov.pac.open.jag.pac.consumer.services;

import ca.bc.gov.open.pac.models.Client;
import ca.bc.gov.open.pac.models.OrdsErrorLog;
import ca.bc.gov.open.pac.models.RequestSuccessLog;
import ca.bc.gov.open.pac.models.exceptions.ORDSException;
import ca.bc.gov.pac.open.jag.pac.consumer.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
    @Value("${icon.host}")
    private String cmsHost = "https://127.0.0.1/";

    @Value("${icon.cmsint-host}")
    private String cmsintHost = "https://127.0.0.1/";

    @Value("${icon.pac-service-url}")
    private String pacServiceUrl = "https://127.0.0.1/";

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

        String eventType = getEventType(client);

        Client eventClient = client.getCopyWithNewEventTypeCode(eventType);

        Optional<Client> updatedClientOptional = pacUpdate(eventClient);

        if (!updatedClientOptional.isPresent()) return;

        Client updatedClient = updatedClientOptional.get();

        SynchronizeClient synchronizeClient = composeSoapServiceRequestBody(updatedClient);

        invokeSoapService(synchronizeClient);

        pacSuccess(updatedClient);
        // End of BPM
    }

    private Optional<Client> pacUpdate(Client client) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(cmsHost + "pac/update");
        try {
            HttpEntity<Client> respClient =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            new HttpEntity<>(client, new HttpHeaders()),
                            new ParameterizedTypeReference<>() {});

            log.info(new RequestSuccessLog("Request Success", "pacUpdate").toString());

            if (respClient.getBody().getStatus().equals("0")) {
                log.info("PAC update cancel");
                return Optional.empty();
            }
            return Optional.ofNullable(respClient.getBody());
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

    private void pacSuccess(Client client, Client eventClient) throws JsonProcessingException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(cmsHost + "pac/success")
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

    private String getEventType(Client client) throws ORDSException, JsonProcessingException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(cmsintHost + "event-type")
                        .queryParam("clientNumber", client.getClientNumber())
                        .queryParam("eventSeqNum", client.getEventSeqNum());
        try {
            HttpEntity<Map<String, String>> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            new HttpEntity<>(new HttpHeaders()),
                            new ParameterizedTypeReference<>() {});
            log.info(new RequestSuccessLog("Request Success", "getEventType").toString());
            return Objects.requireNonNull(resp.getBody()).get("eventTypeCode");
        } catch (Exception ex) {
            log.error(
                    new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getEventType",
                                    ex.getMessage(),
                                    client)
                            .toString());
            throw new ORDSException();
        }
    }

    private HttpEntity<Client> pacSuccess(Client client) throws JsonProcessingException {
        UriComponentsBuilder builder;
        builder = UriComponentsBuilder.fromHttpUrl(cmsHost + "pac/update");
        HttpEntity<Client> respClient;
        try {
            respClient =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            new HttpEntity<>(client, new HttpHeaders()),
                            new ParameterizedTypeReference<>() {});
            log.info(new RequestSuccessLog("Request Success", "pacUpdate").toString());
            if (respClient.getBody().getStatus().equals("0")) {
                log.info("PAC update cancel");
                return null;
            }
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
        return respClient;
    }
}
