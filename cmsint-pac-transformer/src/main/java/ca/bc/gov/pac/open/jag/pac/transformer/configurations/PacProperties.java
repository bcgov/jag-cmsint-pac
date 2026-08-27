package ca.bc.gov.pac.open.jag.pac.transformer.configurations;

import ca.bc.gov.open.pac.models.PacPropertiesInterface;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pac")
@Data
public class PacProperties implements PacPropertiesInterface {
    private String pacQueue;
    private String pacRoutingKey;
    private String exchangeName;
    private String serviceUrl;
    private String pacDatePattern;
    private String icsDatePattern;
    private String cmsDatePattern;
}
