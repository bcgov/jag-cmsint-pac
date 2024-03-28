package ca.bc.gov.open.jag.pac.extractor.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    private final QueueConfig queueConfig;

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Autowired
    public RabbitMqConfig(QueueConfig queueConfig) {
        this.queueConfig = queueConfig;
    }

    @Bean(name = "pac-queue")
    public Queue pacQueue() {
        return new Queue(queueConfig.getPacQueueName(), false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(queueConfig.getTopicExchangeName());
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Declarables binding(@Qualifier("pac-queue") Queue pacQueue, DirectExchange exchange) {
        return new Declarables(
                BindingBuilder.bind(pacQueue).to(exchange).with(queueConfig.getPacRoutingkey()));
    }

    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
