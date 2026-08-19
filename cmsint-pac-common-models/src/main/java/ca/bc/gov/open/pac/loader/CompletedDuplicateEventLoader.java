package ca.bc.gov.open.pac.loader;

import ca.bc.gov.open.pac.models.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompletedDuplicateEventLoader implements EventLoader {
    private static final Logger log = LoggerFactory.getLogger(CompletedDuplicateEventLoader.class);

    @Override
    public void process(Client client) {
        log.info("The event's process for the client " + client + "is finished");
    }
}
