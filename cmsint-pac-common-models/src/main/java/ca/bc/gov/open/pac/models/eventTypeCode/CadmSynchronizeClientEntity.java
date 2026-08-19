package ca.bc.gov.open.pac.models.eventTypeCode;

import ca.bc.gov.open.pac.models.Client;
import com.health.phis.ws.SynchronizeClient;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CadmSynchronizeClientEntity extends SynchronizeClient {

    public CadmSynchronizeClientEntity(Client client) {
        csNumber = client.getDemographicInfo().getCsNum();
        surname = client.getDemographicInfo().getSurname();
        givenName1 = client.getDemographicInfo().getGivenName1();
        givenName2 = client.getDemographicInfo().getGivenName2();
        birthDate = client.getDemographicInfo().getBirthDate();
        gender = client.getDemographicInfo().getGender();
        photoGuid = client.getDemographicInfo().getPhotoGUID();
        probableDischargeDate = client.getDemographicInfo().getProbableDischargeDate();
        centre = client.getDemographicInfo().getCustodyCenter();
        livingUnit = client.getDemographicInfo().getLivingUnit();
    }

    public CadmSynchronizeClientEntity() {}

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof CadmSynchronizeClientEntity)) return false;
        CadmSynchronizeClientEntity other = (CadmSynchronizeClientEntity) o;
        return other.canEqual(this);
    }

    protected boolean canEqual(Object other) {
        return other instanceof CadmSynchronizeClientEntity;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "CadmSynchronizeClientEntity()";
    }
}
