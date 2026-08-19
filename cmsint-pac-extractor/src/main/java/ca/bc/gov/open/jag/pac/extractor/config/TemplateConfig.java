package ca.bc.gov.open.jag.pac.extractor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TemplateConfig {
    private static final Logger log = LoggerFactory.getLogger(TemplateConfig.class);

    @Value("${ords.cmsIntUsername}")
    private String cmsIntUsername;

    @Value("${ords.cmsIntPassword}")
    private String cmsIntPassword;

    @Value("${ords.cmsUsername}")
    private String cmsUsername;

    @Value("${ords.cmsPassword}")
    private String cmsPassword;

    @Bean(name = "restTemplateCMSInt")
    public RestTemplate restTemplateCMSInt(RestTemplateBuilder restTemplateBuilder) {
        var restTemplate =
                restTemplateBuilder.basicAuthentication(cmsIntUsername, cmsIntPassword).build();
        return restTemplate;
    }

    @Bean(name = "restTemplateCMS")
    public RestTemplate restTemplateCMS(RestTemplateBuilder restTemplateBuilder) {
        var restTemplate =
                restTemplateBuilder.basicAuthentication(cmsUsername, cmsPassword).build();
        return restTemplate;
    }
}
