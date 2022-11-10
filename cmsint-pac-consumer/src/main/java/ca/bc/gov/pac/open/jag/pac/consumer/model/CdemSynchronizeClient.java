package ca.bc.gov.pac.open.jag.pac.consumer.model;

import ca.bc.gov.open.pac.models.Client;

public class CdemSynchronizeClient extends SynchronizeClient {

    public CdemSynchronizeClient(Client client) {
        csNumber = client.getCsNum();
    }
}
