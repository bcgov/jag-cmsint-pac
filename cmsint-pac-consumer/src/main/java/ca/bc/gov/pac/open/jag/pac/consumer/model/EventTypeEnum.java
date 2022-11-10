package ca.bc.gov.pac.open.jag.pac.consumer.model;

import ca.bc.gov.open.pac.models.Client;
import java.util.function.Function;

public enum EventTypeEnum {
    CADM(CadmSynchronizeClient::new),
    CDEM(CdemSynchronizeClient::new),
    CREL(CrelSynchronizeClient::new),
    CLUN(ClunSynchronizeClient::new),
    CKEY(CkeySynchronizeClient::new),
    CLOC(ClocSynchronizeClient::new),
    CIMG(CimgSynchronizeClient::new);

    private final Function<Client, SynchronizeClient> synchronizeClientFunction;

    private EventTypeEnum(Function<Client, SynchronizeClient> synchronizeClientFunction) {
        this.synchronizeClientFunction = synchronizeClientFunction;
    }

    public SynchronizeClient getSynchronizeClient(Client client) {
        return synchronizeClientFunction.apply(client);
    }
}
