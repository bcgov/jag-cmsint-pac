package ca.bc.gov.open.pac.models;

import ca.bc.gov.open.pac.models.ClientDto.EventState;
import ca.bc.gov.open.pac.models.dateFormatters.DateFormatterInterface;
import ca.bc.gov.open.pac.models.eventStatus.*;
import ca.bc.gov.open.pac.models.ords.DemographicsEntity;
import ca.bc.gov.open.pac.models.ords.EventEntity;
import ca.bc.gov.open.pac.models.ords.ProcessEntity;
import java.io.Serializable;
import java.util.Objects;
import org.springframework.web.client.RestTemplate;

public class Client implements Serializable {
    private String clientNumber;
    private final String eventSeqNum;
    private final String eventTypeCode;
    private final String computerSystemCd;
    private EventStatus status;
    private DemographicInfo demographicInfo;

    public Client(
            String clientNumber,
            String eventSeqNum,
            String eventTypeCode,
            String computerSystemCd,
            EventStatus status,
            DemographicInfo demographicInfo) {
        this.clientNumber = clientNumber;
        this.eventSeqNum = eventSeqNum;
        this.eventTypeCode = eventTypeCode;
        this.computerSystemCd = computerSystemCd;
        this.status = status;
        this.demographicInfo = demographicInfo;
    }

    public Client(ProcessEntity processEntity, EventEntity eventEntity) {
        clientNumber = processEntity.getClientNumber();
        eventSeqNum = processEntity.getEventSeqNum();
        computerSystemCd = processEntity.getComputerSystemCd();
        eventTypeCode = eventEntity.getEventTypeCode();
        status = new NewEventStatus();
        demographicInfo = new DemographicInfo();
    }

    public Client(
            ProcessEntity processEntity,
            EventEntity eventEntity,
            RestTemplate restTemplate,
            OrdsPropertiesInterface ordsProperties) {
        this(processEntity, eventEntity);
        this.status = new NewEventStatus(ordsProperties, restTemplate);
    }

    public Client(Client client, DemographicsEntity demographicsEntity) {
        clientNumber = client.getClientNumber();
        eventSeqNum = client.getEventSeqNum();
        computerSystemCd = client.getComputerSystemCd();
        status = client.getStatus();
        eventTypeCode = client.getEventTypeCode();

        demographicInfo = new DemographicInfo(demographicsEntity);
    }

    public Client(ClientDto clientDto) {
        clientNumber = clientDto.getClientNumber();
        eventSeqNum = clientDto.getEventSeqNum();
        computerSystemCd = clientDto.getComputerSystemCd();
        EventState eventState = clientDto.getEventState();
        switch(eventState) {
            case CompletedDuplicate:
                this.status = new CompletedDuplicateEventStatus(null, null);
                break;
            case ConnectionError:
                this.status = new ConnectionErrorEventStatus(null, null);
                break;
            case InProgress:
                this.status = new InProgressEventStatus(null, null);
                break;
            case New:
                this.status = new NewEventStatus(null, null);
                break;
            case Pending:
                this.status = new PendingEventStatus(null, null);
                break;
            default:
                this.status = new ApplicationErrorEventStatus(null, null);
                break;
        }
        eventTypeCode = clientDto.getEventTypeCode();
        demographicInfo = clientDto.getDemographicInfo();
    }

    public Client(Client client, DemographicInfo demographicInfo) {
        this.clientNumber = client.getClientNumber();
        this.eventSeqNum = client.getEventSeqNum();
        this.computerSystemCd = client.getComputerSystemCd();
        this.eventTypeCode = client.getEventTypeCode();
        this.status = client.getStatus();
        this.demographicInfo = demographicInfo;
    }

    public Client updateBirthDateFormat(DateFormatterInterface dateFormatter) {
        var updatedDemographicInfo = demographicInfo.updateBirthDateFormat(dateFormatter);
        return new Client(this, updatedDemographicInfo);
    }

    public Client updateProbableDischargeDateDateFormat(DateFormatterInterface dateFormatter) {
        var updatedDemographicInfo =
                demographicInfo.updateProbableDischargeDateDateFormat(dateFormatter);
        return new Client(this, updatedDemographicInfo);
    }

    public Client updateSysDateFormat(DateFormatterInterface dateFormatter) {
        var updatedDemographicInfo = demographicInfo.updateSysDateFormat(dateFormatter);
        return new Client(this, updatedDemographicInfo);
    }

    public Client updateNextCourtDtFormat(DateFormatterInterface dateFormatter) {
        var updatedDemographicInfo = demographicInfo.updateNextCourtDtFormat(dateFormatter);
        return new Client(this, updatedDemographicInfo);
    }

    public ClientDto Dto() {
        return new ClientDto(this);
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

    public String getEventTypeCode() {
        return eventTypeCode;
    }

    public String getComputerSystemCd() {
        return computerSystemCd;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
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
        if (!(o instanceof Client)) return false;
        Client other = (Client) o;
        return other.canEqual(this)
                && Objects.equals(this.clientNumber, other.clientNumber)
                && Objects.equals(this.eventSeqNum, other.eventSeqNum)
                && Objects.equals(this.eventTypeCode, other.eventTypeCode)
                && Objects.equals(this.computerSystemCd, other.computerSystemCd)
                && Objects.equals(this.status, other.status)
                && Objects.equals(this.demographicInfo, other.demographicInfo);
    }

    protected boolean canEqual(Object other) {
        return other instanceof Client;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                clientNumber,
                eventSeqNum,
                eventTypeCode,
                computerSystemCd,
                status,
                demographicInfo);
    }

    @Override
    public String toString() {
        return "Client(clientNumber="
                + clientNumber
                + ", eventSeqNum="
                + eventSeqNum
                + ", eventTypeCode="
                + eventTypeCode
                + ", computerSystemCd="
                + computerSystemCd
                + ", status="
                + status
                + ", demographicInfo="
                + demographicInfo
                + ")";
    }
}
