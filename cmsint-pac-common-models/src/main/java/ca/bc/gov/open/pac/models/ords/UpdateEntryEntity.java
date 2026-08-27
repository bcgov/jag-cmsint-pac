package ca.bc.gov.open.pac.models.ords;

import ca.bc.gov.open.pac.models.Client;
import ca.bc.gov.open.pac.models.eventStatus.EventStatusCode;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class UpdateEntryEntity implements Serializable {
    private final String clientNumber;
    private final String eventSeqNum;
    private final String computerSystemCd;
    private final String eventTypeCode;

    public UpdateEntryEntity(Client client, EventStatusCode eventStatusCode) {
        clientNumber = client.getClientNumber();
        eventSeqNum = client.getEventSeqNum();
        computerSystemCd = client.getComputerSystemCd();
        eventTypeCode = eventStatusCode.getCode();
    }
}
