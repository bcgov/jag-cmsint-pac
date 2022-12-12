package ca.bc.gov.open.pac.models.eventStatus;

import ca.bc.gov.open.pac.models.OrdsPropertiesInterface;
import java.io.Serializable;
import org.springframework.web.client.RestTemplate;

public class CompletedDuplicateEventStatus extends EventStatus implements Serializable {

    public static final String METHOD_NAME = "updateToCompletedDuplicate";

    public CompletedDuplicateEventStatus(
            OrdsPropertiesInterface ordProperties, RestTemplate restTemplate) {
        super(ordProperties, restTemplate);
    }

    @Override
    protected String getMethodName() {
        return METHOD_NAME;
    }
}
