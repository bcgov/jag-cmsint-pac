package ca.bc.gov.pac.open.jag.pac.transformer.services;

import ca.bc.gov.open.pac.models.Client;
import ca.bc.gov.open.pac.models.ClientDto;
import ca.bc.gov.open.pac.models.dateFormatters.DateFormatEnum;
import ca.bc.gov.open.pac.models.dateFormatters.DateFormatterInterface;
import ca.bc.gov.open.pac.models.eventStatus.PendingEventStatus;
import ca.bc.gov.pac.open.jag.pac.transformer.configurations.OrdsProperties;
import ca.bc.gov.pac.open.jag.pac.transformer.configurations.PacProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class TransformerService {

    private final RestTemplate restTemplate;
    private final OrdsProperties ordsProperties;
    private final PacProperties pacProperties;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TransformerService(
            RestTemplate restTemplate,
            OrdsProperties ordsProperties,
            PacProperties pacProperties,
            RabbitTemplate rabbitTemplate) {
        this.restTemplate = restTemplate;
        this.ordsProperties = ordsProperties;
        this.pacProperties = pacProperties;

        this.rabbitTemplate = rabbitTemplate;
    }

    // PACUpdate BPM
    public void processPAC(final ClientDto clientDto) {
        try {

            Client client = clientDto.toClient();
            if (!(client.getStatus() instanceof PendingEventStatus)) {
                sendToQueue(client);
                return;
            }

            DateFormatterInterface dateFormatter =
                    DateFormatEnum.valueOf(client.getComputerSystemCd().toUpperCase())
                            .getDateFormatter(pacProperties);

            var clientWithUpdatedDates =
                    client.updateBirthDateFormat(dateFormatter)
                            .updateProbableDischargeDateDateFormat(dateFormatter)
                            .updateSysDateFormat(dateFormatter);

            clientWithUpdatedDates
                    .getStatus()
                    .setRestTemplate(restTemplate)
                    .setOrdsProperties(ordsProperties)
                    .updateToInProgress(clientWithUpdatedDates); // cmsords/pac/v1/entries
            sendToQueue(clientWithUpdatedDates);

        } catch (Exception ex) {
            Client client = clientDto.toClient();
            client.getStatus()
                    .setRestTemplate(restTemplate)
                    .setOrdsProperties(ordsProperties)
                    .updateToApplicationError(client);
            log.error("PAC BPM ERROR: " + client + " not processed successfully");
            log.error(ex.getMessage());
        }
    }

    public void sendToQueue(Client client) throws JsonProcessingException {
        this.rabbitTemplate.convertAndSend(
                pacProperties.getExchangeName(), pacProperties.getPacRoutingKey(), client.Dto());
    }
}
