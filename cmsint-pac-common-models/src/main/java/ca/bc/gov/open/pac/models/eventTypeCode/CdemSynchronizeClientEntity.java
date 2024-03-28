package ca.bc.gov.open.pac.models.eventTypeCode;

import ca.bc.gov.open.pac.models.Client;
import com.health.phis.ws.SynchronizeClient;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement
@Data
@NoArgsConstructor
public class CdemSynchronizeClientEntity extends SynchronizeClient {

    public CdemSynchronizeClientEntity(Client client) {
        csNumber = client.getDemographicInfo().getCsNum();
        surname = client.getDemographicInfo().getSurname();
        givenName1 = client.getDemographicInfo().getGivenName1();
        givenName2 = client.getDemographicInfo().getGivenName2();
        birthDate = client.getDemographicInfo().getBirthDate();
        gender = client.getDemographicInfo().getGender();
        photoGuid = client.getDemographicInfo().getPhotoGUID();
        centre = client.getDemographicInfo().getCustodyCenter();
        livingUnit = client.getDemographicInfo().getLivingUnit();
    }
}
