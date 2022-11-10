package ca.bc.gov.pac.open.jag.pac.consumer.model;

import ca.bc.gov.open.pac.models.Client;
import lombok.Getter;

@Getter
public class ClocSynchronizeClient extends SynchronizeClient {

    public ClocSynchronizeClient(Client client) {
        csNumber = client.getCsNum();
        outLocation = client.getPacLocationCd();
        outReason = client.getOutReason();
    }
}
