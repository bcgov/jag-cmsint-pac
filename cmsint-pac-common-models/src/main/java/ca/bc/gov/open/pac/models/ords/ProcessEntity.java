package ca.bc.gov.open.pac.models.ords;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class ProcessEntity extends BaseEntity {
    @JsonProperty("clientNumber")
    private String clientNumber;

    @JsonProperty("eventSeqNum")
    private String eventSeqNum;

    @JsonProperty("computerSystemCd")
    private String computerSystemCd;

    public ProcessEntity() {}

    public ProcessEntity(String clientNumber, String eventSeqNum, String computerSystemCd) {
        this.clientNumber = clientNumber;
        this.eventSeqNum = eventSeqNum;
        this.computerSystemCd = computerSystemCd;
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

    public String getComputerSystemCd() {
        return computerSystemCd;
    }

    public void setComputerSystemCd(String computerSystemCd) {
        this.computerSystemCd = computerSystemCd;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ProcessEntity)) return false;
        ProcessEntity other = (ProcessEntity) o;
        return other.canEqual(this)
                && super.equals(o)
                && Objects.equals(this.clientNumber, other.clientNumber)
                && Objects.equals(this.eventSeqNum, other.eventSeqNum)
                && Objects.equals(this.computerSystemCd, other.computerSystemCd);
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof ProcessEntity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), clientNumber, eventSeqNum, computerSystemCd);
    }

    @Override
    public String toString() {
        return "ProcessEntity(clientNumber="
                + clientNumber
                + ", eventSeqNum="
                + eventSeqNum
                + ", computerSystemCd="
                + computerSystemCd
                + ")";
    }
}
