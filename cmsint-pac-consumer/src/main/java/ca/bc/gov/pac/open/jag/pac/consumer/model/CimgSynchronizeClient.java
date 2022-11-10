package ca.bc.gov.pac.open.jag.pac.consumer.model;

import ca.bc.gov.open.pac.models.Client;

public class CimgSynchronizeClient extends SynchronizeClient {
    public CimgSynchronizeClient(Client client) {
        csNumber = client.getCsNum();
        photoGuid = client.getPhotoGUID();
    }
}
