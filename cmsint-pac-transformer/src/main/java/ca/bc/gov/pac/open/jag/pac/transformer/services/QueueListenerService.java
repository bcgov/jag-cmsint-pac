package ca.bc.gov.pac.open.jag.pac.transformer.services;

import ca.bc.gov.open.pac.models.ClientDto;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class QueueListenerService {
    private static final Logger log = LoggerFactory.getLogger(QueueListenerService.class);

    private final TransformerService transformerService;

    @Autowired
    public QueueListenerService(TransformerService transformerService) {
        this.transformerService = transformerService;
    }

    @RabbitListener(queues = "${pac.pac-queue}")
    public void receivePACMessage(@Payload Message<ClientDto> message) throws IOException {
        ClientDto clientDto = message.getPayload();
        transformerService.processPAC(clientDto);
    }
}
