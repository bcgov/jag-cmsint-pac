package ca.bc.gov.open.pac.loader;

import ca.bc.gov.open.pac.models.Client;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NewEventLoader implements EventLoader {

    @Override
    public void process(Client client) {
        log.info("A 'New' event should not be processed " + client);
    }
}
