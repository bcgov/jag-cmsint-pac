package ca.bc.gov.pac.open.jag.pac.consumer.model;

import ca.bc.gov.open.pac.models.Client;

public class CrelSynchronizeClient extends SynchronizeClient {

    public CrelSynchronizeClient(Client client) {
        csNumber = client.getCsNum();
    }
}
