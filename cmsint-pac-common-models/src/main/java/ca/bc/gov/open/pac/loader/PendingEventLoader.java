package ca.bc.gov.open.pac.loader;

import ca.bc.gov.open.pac.models.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PendingEventLoader implements EventLoader {
    private static final Logger log = LoggerFactory.getLogger(PendingEventLoader.class);

    public void process(Client client) {
        log.info("A 'Pending' event should not be processed " + client);
    }
}
