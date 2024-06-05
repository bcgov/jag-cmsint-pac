package ca.bc.gov.open.pac.models.eventTypeCode;

import ca.bc.gov.open.pac.models.Client;
import com.health.phis.ws.SynchronizeClient;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement
@Data
@NoArgsConstructor
public class CimgSynchronizeClientEntity extends SynchronizeClient {
    public CimgSynchronizeClientEntity(Client client) {
        csNumber = client.getDemographicInfo().getCsNum();
        photoGuid = client.getDemographicInfo().getPhotoGUID();
    }
}
