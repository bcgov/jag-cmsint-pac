package ca.bc.gov.open.jag.pac.extractor.config;

import ca.bc.gov.open.pac.models.OrdsPropertiesInterface;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ords")
@Data
public class OrdsProperties implements OrdsPropertiesInterface {
    private String username;
    private String password;
    private String cmsIntOrdsUrl = "http://test.com/cmsint/";
    private String cmsOrdsUrl = "http://test.com/cms/";
    private String eventsEndpoint;
    private String processesEndpoint;
    private String eventsTypeEndpoint;
    private String successEndpoint;
    private String entriesEndpoint;
    private String demographicsEndpoint;
    private String modulePath = "module/";

    public String getCmsIntBaseUrl() {
        return cmsIntOrdsUrl + modulePath;
    }

    public String getCmsBaseUrl() {
        return cmsOrdsUrl + modulePath;
    }
}
