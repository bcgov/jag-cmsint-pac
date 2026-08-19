package ca.bc.gov.open.pac.models.eventTypeCode;

import ca.bc.gov.open.pac.models.Client;
import com.health.phis.ws.SynchronizeClient;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CkeySynchronizeClientEntity extends SynchronizeClient {

    public CkeySynchronizeClientEntity(Client client) {
        csNumber = client.getDemographicInfo().getCsNum();
        probableDischargeDate = client.getDemographicInfo().getProbableDischargeDate();
    }

    public CkeySynchronizeClientEntity() {}

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof CkeySynchronizeClientEntity)) return false;
        CkeySynchronizeClientEntity other = (CkeySynchronizeClientEntity) o;
        return other.canEqual(this);
    }

    protected boolean canEqual(Object other) {
        return other instanceof CkeySynchronizeClientEntity;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "CkeySynchronizeClientEntity()";
    }
}
