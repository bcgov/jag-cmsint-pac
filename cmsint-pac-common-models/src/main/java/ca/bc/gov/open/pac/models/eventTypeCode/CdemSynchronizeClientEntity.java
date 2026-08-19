package ca.bc.gov.open.pac.models.eventTypeCode;

import ca.bc.gov.open.pac.models.Client;
import com.health.phis.ws.SynchronizeClient;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
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
        nextCourtDt = client.getDemographicInfo().getNextCourtDt();
    }

    public CdemSynchronizeClientEntity() {}

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof CdemSynchronizeClientEntity)) return false;
        CdemSynchronizeClientEntity other = (CdemSynchronizeClientEntity) o;
        return other.canEqual(this);
    }

    protected boolean canEqual(Object other) {
        return other instanceof CdemSynchronizeClientEntity;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "CdemSynchronizeClientEntity()";
    }
}
