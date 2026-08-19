package ca.bc.gov.open.pac.models;

import ca.bc.gov.open.pac.models.eventStatus.*;

import java.io.Serializable;
import java.util.Objects;

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

    public ClientDto() {}

    public ClientDto(
            String clientNumber,
            String eventSeqNum,
            String eventTypeCode,
            String computerSystemCd,
            EventState eventState,
            DemographicInfo demographicInfo) {
        this.clientNumber = clientNumber;
        this.eventSeqNum = eventSeqNum;
        this.eventTypeCode = eventTypeCode;
        this.computerSystemCd = computerSystemCd;
        this.eventState = eventState;
        this.demographicInfo = demographicInfo;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getEventSeqNum() {
        return eventSeqNum;
    }

    public void setEventSeqNum(String eventSeqNum) {
        this.eventSeqNum = eventSeqNum;
    }

    public String getEventTypeCode() {
        return eventTypeCode;
    }

    public void setEventTypeCode(String eventTypeCode) {
        this.eventTypeCode = eventTypeCode;
    }

    public String getComputerSystemCd() {
        return computerSystemCd;
    }

    public void setComputerSystemCd(String computerSystemCd) {
        this.computerSystemCd = computerSystemCd;
    }

    public EventState getEventState() {
        return eventState;
    }

    public void setEventState(EventState eventState) {
        this.eventState = eventState;
    }

    public DemographicInfo getDemographicInfo() {
        return demographicInfo;
    }

    public void setDemographicInfo(DemographicInfo demographicInfo) {
        this.demographicInfo = demographicInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ClientDto)) return false;
        ClientDto other = (ClientDto) o;
        return other.canEqual(this)
                && Objects.equals(this.clientNumber, other.clientNumber)
                && Objects.equals(this.eventSeqNum, other.eventSeqNum)
                && Objects.equals(this.eventTypeCode, other.eventTypeCode)
                && Objects.equals(this.computerSystemCd, other.computerSystemCd)
                && Objects.equals(this.eventState, other.eventState)
                && Objects.equals(this.demographicInfo, other.demographicInfo);
    }

    protected boolean canEqual(Object other) {
        return other instanceof ClientDto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                clientNumber,
                eventSeqNum,
                eventTypeCode,
                computerSystemCd,
                eventState,
                demographicInfo);
    }

    @Override
    public String toString() {
        return "ClientDto(clientNumber="
                + clientNumber
                + ", eventSeqNum="
                + eventSeqNum
                + ", eventTypeCode="
                + eventTypeCode
                + ", computerSystemCd="
                + computerSystemCd
                + ", eventState="
                + eventState
                + ", demographicInfo="
                + demographicInfo
                + ")";
    }
}
