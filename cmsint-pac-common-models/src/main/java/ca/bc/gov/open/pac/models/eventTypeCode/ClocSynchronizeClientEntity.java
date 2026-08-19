package ca.bc.gov.open.pac.models.eventTypeCode;

import ca.bc.gov.open.pac.models.Client;
import com.health.phis.ws.SynchronizeClient;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClocSynchronizeClientEntity extends SynchronizeClient {

    public ClocSynchronizeClientEntity(Client client) {
        csNumber = client.getDemographicInfo().getCsNum();
        outLocation = client.getDemographicInfo().getPacLocationCd();
        outReason = client.getDemographicInfo().getOutReason();
    }

    public ClocSynchronizeClientEntity() {}

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ClocSynchronizeClientEntity)) return false;
        ClocSynchronizeClientEntity other = (ClocSynchronizeClientEntity) o;
        return other.canEqual(this);
    }

    protected boolean canEqual(Object other) {
        return other instanceof ClocSynchronizeClientEntity;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "ClocSynchronizeClientEntity()";
    }
}
