package ca.bc.gov.open.jag.pac.loader.service;

import ca.bc.gov.open.pac.models.Client;
import ca.bc.gov.open.pac.models.ClientDto;
import ca.bc.gov.open.pac.models.exceptions.ORDSException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QueueListenerService {

    private final LoaderService loaderService;

    public QueueListenerService(LoaderService loaderService) {
        this.loaderService = loaderService;
    }

    @RabbitListener(queues = "${pac.pac-queue}")
    public void receivePACMessage(@Payload Message<ClientDto> message) throws IOException {
        ClientDto clientDto = message.getPayload();
        try {
            loaderService.processPAC(clientDto);
        } catch (ORDSException ex) {
            Client client = clientDto.toClient();
            log.error("PAC BPM ERROR: " + message + " not processed successfully");
            loaderService.updateToConnectionError(client);
        } catch (Exception ignored) {
            Client client = clientDto.toClient();
            log.error("PAC BPM ERROR: " + message + " not processed successfully");
            loaderService.updateToConnectionError(client);
        }
    }
}
