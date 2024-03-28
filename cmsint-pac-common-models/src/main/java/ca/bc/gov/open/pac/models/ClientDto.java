package ca.bc.gov.open.pac.models;

import ca.bc.gov.open.pac.models.eventStatus.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto implements Serializable {
    public String clientNumber;
    public String eventSeqNum;
    public String eventTypeCode;
    public String computerSystemCd;
    public enum EventState {
        ApplicationError,
        CompletedDuplicate,
        ConnectionError,
        InProgress,
        New,
        Pending
    };
    public EventState eventState;
    public DemographicInfo demographicInfo;

    public ClientDto(Client client)
    {
        this.clientNumber = client.getClientNumber();
        this.eventSeqNum = client.getEventSeqNum();
        this.computerSystemCd = client.getComputerSystemCd();
        this.eventTypeCode = client.getEventTypeCode();
        if(client.getStatus() instanceof CompletedDuplicateEventStatus) {
            this.eventState = EventState.CompletedDuplicate;
        } else if (client.getStatus() instanceof ConnectionErrorEventStatus) {
            this.eventState = EventState.ConnectionError;
        } else if (client.getStatus() instanceof InProgressEventStatus) {
            this.eventState = EventState.InProgress;
        } else if (client.getStatus() instanceof NewEventStatus) {
            this.eventState = EventState.New;
        } else if (client.getStatus() instanceof PendingEventStatus) {
            this.eventState = EventState.Pending;
        } else if (client.getStatus() instanceof ApplicationErrorEventStatus) {
            this.eventState = EventState.ApplicationError;
        }

        this.demographicInfo = client.getDemographicInfo();
    }

    public Client toClient() {
        return new Client(this);
    }

}
