package ca.bc.gov.pac.open.jag.pac.consumer.model;

import ca.bc.gov.open.pac.models.Client;
import lombok.Getter;

@Getter
public class CkeySynchronizeClient extends SynchronizeClient {

    public CkeySynchronizeClient(Client client) {
        csNumber = client.getCsNum();
        probableDischargeDate = client.getProbableDischargeDate();
    }
}
