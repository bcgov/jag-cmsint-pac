package ca.bc.gov.open.pac.models.eventTypeCode;

import ca.bc.gov.open.pac.models.Client;
import com.health.phis.ws.SynchronizeClient;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement
@Data
@NoArgsConstructor
public class ClocSynchronizeClientEntity extends SynchronizeClient {

    public ClocSynchronizeClientEntity(Client client) {
        csNumber = client.getDemographicInfo().getCsNum();
        outLocation = client.getDemographicInfo().getPacLocationCd();
        outReason = client.getDemographicInfo().getOutReason();
    }
}
