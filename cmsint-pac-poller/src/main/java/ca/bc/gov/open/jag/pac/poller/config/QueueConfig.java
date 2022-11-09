package ca.bc.gov.open.jag.pac.poller.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class QueueConfig {

    @Value("${pac.exchange-name}")
    private String topicExchangeName;

    @Value("${pac.pac-queue}")
    private String pacQueueName;

    @Value("${pac.ping-queue}")
    private String pingQueueName;

    @Value("${pac.pac-routing-key}")
    private String pacRoutingkey;

    @Value("${pac.ping-routing-key}")
    private String pingRoutingKey;
}
