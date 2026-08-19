package ca.bc.gov.open.jag.pac.extractor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Value("${pac.exchange-name}")
    private String topicExchangeName;

    @Value("${pac.pac-queue}")
    private String pacQueueName;

    @Value("${pac.pac-routing-key}")
    private String pacRoutingkey;

    public String getTopicExchangeName() {
        return topicExchangeName;
    }

    public String getPacQueueName() {
        return pacQueueName;
    }

    public String getPacRoutingkey() {
        return pacRoutingkey;
    }
}
