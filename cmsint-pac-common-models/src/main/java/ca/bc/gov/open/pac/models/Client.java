package ca.bc.gov.open.pac.models;

import ca.bc.gov.open.pac.models.eventStatus.EventStatus;
import ca.bc.gov.open.pac.models.eventStatus.NewEventStatus;
import ca.bc.gov.open.pac.models.ords.DemographicsEntity;
import ca.bc.gov.open.pac.models.ords.EventEntity;
import ca.bc.gov.open.pac.models.ords.ProcessEntity;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.client.RestTemplate;

@Data
@AllArgsConstructor
public class Client implements Serializable {
    private String clientNumber;
    private final String eventSeqNum;
    private final String eventTypeCode;
    private final String computerSystemCd;
    private EventStatus status;
    private DemographicInfo demographicInfo;

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
            OrdsPropertiesInterface ordProperties) {
        this(processEntity, eventEntity);
        this.status = new NewEventStatus(ordProperties, restTemplate);
    }

    public Client(Client client, DemographicsEntity demographicsEntity) {
        clientNumber = client.getClientNumber();
        eventSeqNum = client.getEventSeqNum();
        computerSystemCd = client.getComputerSystemCd();
        status = client.getStatus();
        eventTypeCode = client.getEventTypeCode();

        demographicInfo = new DemographicInfo(demographicsEntity);
    }
}
