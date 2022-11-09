package ca.bc.gov.pac.open.jagpacnconsumer.services;

import ca.bc.gov.open.pac.models.Client;
import ca.bc.gov.open.pac.models.PingModel;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class ConsumerService {
    private final ObjectMapper objectMapper;

    private final PACService pacService;

    @Autowired
    public ConsumerService(ObjectMapper objectMapper, PACService pacService) {
        this.objectMapper = objectMapper;
        this.pacService = pacService;
    }

    // Disable PAC Queue until PAC is ready to go
    @RabbitListener(queues = "${icon.pac-queue}")
    public void receivePACMessage(@Payload Message<Client> message) throws IOException {
        try {
            pacService.processPAC(message.getPayload());
        } catch (Exception ignored) {
            log.error("PAC BPM ERROR: " + message + " not processed successfully");
        }
        System.out.println(new ObjectMapper().writeValueAsString(message.getPayload()));
    }

    @RabbitListener(queues = "${pac.ping-queue}")
    public void receivePingMessage(@Payload Message<PingModel> message)
            throws JsonProcessingException {
        System.out.println(new ObjectMapper().writeValueAsString(message.getPayload()));
    }
}
