package ca.bc.gov.open.pac.models.ords;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class EventEntity extends BaseEntity {
    @JsonProperty("clientNumber")
    private String clientNumber;

    @JsonProperty("eventSeqNum")
    private String eventSeqNum;

    @JsonProperty("eventTypeCode")
    private String eventTypeCode;

    public EventEntity() {}

    public EventEntity(String clientNumber, String eventSeqNum, String eventTypeCode) {
        this.clientNumber = clientNumber;
        this.eventSeqNum = eventSeqNum;
        this.eventTypeCode = eventTypeCode;
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

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof EventEntity)) return false;
        EventEntity other = (EventEntity) o;
        return other.canEqual(this)
                && super.equals(o)
                && Objects.equals(this.clientNumber, other.clientNumber)
                && Objects.equals(this.eventSeqNum, other.eventSeqNum)
                && Objects.equals(this.eventTypeCode, other.eventTypeCode);
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof EventEntity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), clientNumber, eventSeqNum, eventTypeCode);
    }

    @Override
    public String toString() {
        return "EventEntity(clientNumber="
                + clientNumber
                + ", eventSeqNum="
                + eventSeqNum
                + ", eventTypeCode="
                + eventTypeCode
                + ")";
    }
}
