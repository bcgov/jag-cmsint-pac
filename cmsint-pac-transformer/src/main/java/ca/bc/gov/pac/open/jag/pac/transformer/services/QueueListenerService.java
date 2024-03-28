package ca.bc.gov.pac.open.jag.pac.transformer.services;

import ca.bc.gov.open.pac.models.ClientDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QueueListenerService {
    private final ObjectMapper objectMapper;

    private final TransformerService transformerService;

    @Autowired
    public QueueListenerService(ObjectMapper objectMapper, TransformerService transformerService) {
        this.objectMapper = objectMapper;
        this.transformerService = transformerService;
    }

    @RabbitListener(queues = "${pac.pac-queue}")
    public void receivePACMessage(@Payload Message<ClientDto> message) throws IOException {
        ClientDto clientDto = message.getPayload();
        transformerService.processPAC(clientDto);
    }
}
