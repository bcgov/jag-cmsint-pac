package ca.bc.gov.open.jag.pac.extractor.config;

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

    @Value("${pac.pac-routing-key}")
    private String pacRoutingkey;
}
