package ca.bc.gov.open.pac.loader;

import ca.bc.gov.open.pac.models.Client;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompletedDuplicateEventLoader implements EventLoader {
    @Override
    public void process(Client client) {
        log.info("The event's process for the client " + client + "is finished");
    }
}
