package ca.bc.gov.open.pac.models.eventTypeCode;

import ca.bc.gov.open.pac.models.Client;
import com.health.phis.ws.SynchronizeClient;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CimgSynchronizeClientEntity extends SynchronizeClient {
    public CimgSynchronizeClientEntity(Client client) {
        csNumber = client.getDemographicInfo().getCsNum();
        photoGuid = client.getDemographicInfo().getPhotoGUID();
    }

    public CimgSynchronizeClientEntity() {}

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof CimgSynchronizeClientEntity)) return false;
        CimgSynchronizeClientEntity other = (CimgSynchronizeClientEntity) o;
        return other.canEqual(this);
    }

    protected boolean canEqual(Object other) {
        return other instanceof CimgSynchronizeClientEntity;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "CimgSynchronizeClientEntity()";
    }
}
