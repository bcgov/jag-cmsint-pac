package ca.bc.gov.pac.open.jag.pac.consumer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.bc.gov.open.pac.models.Client;
import org.junit.jupiter.api.Test;

class EventTypeEnumTest {
    final Client client =
            new Client(
                    "clientNumber",
                    "csNum",
                    "eventSeqNum",
                    "eventTypeCode",
                    "surname",
                    "givenName1",
                    "givenName2",
                    "birthDate",
                    "gender",
                    "photoGUID",
                    "probableDischargeDate",
                    "pacLocationCd",
                    "outReason",
                    "newerSequence",
                    "computerSystemCd",
                    "isActive",
                    "sysDate",
                    "fromCsNum",
                    "userId",
                    "mergeUserId",
                    "icsLocationCd",
                    "isIn",
                    "custodyCenter",
                    "livingUnit",
                    "status");

    @Test
    void cadmEventCodeProducesCadmSynchronizeClient() {

        CadmSynchronizeClient expectedClient = new CadmSynchronizeClient(client);
        SynchronizeClient actualClient = EventTypeEnum.CADM.getSynchronizeClient(client);

        assertEquals(expectedClient, actualClient);
    }

    @Test
    void cdemEventCodeProducesCdemSynchronizeClient() {

        CdemSynchronizeClient expectedClient = new CdemSynchronizeClient(client);
        SynchronizeClient actualClient = EventTypeEnum.CDEM.getSynchronizeClient(client);

        assertEquals(expectedClient, actualClient);
    }

    @Test
    void cimgEventCodeProducesCimgSynchronizeClient() {

        CimgSynchronizeClient expectedClient = new CimgSynchronizeClient(client);
        SynchronizeClient actualClient = EventTypeEnum.CIMG.getSynchronizeClient(client);

        assertEquals(expectedClient, actualClient);
    }

    @Test
    void ckeyEventCodeProducesCkeySynchronizeClient() {

        CkeySynchronizeClient expectedClient = new CkeySynchronizeClient(client);
        SynchronizeClient actualClient = EventTypeEnum.CKEY.getSynchronizeClient(client);

        assertEquals(expectedClient, actualClient);
    }

    @Test
    void clocEventCodeProducesClocSynchronizeClient() {

        ClocSynchronizeClient expectedClient = new ClocSynchronizeClient(client);
        SynchronizeClient actualClient = EventTypeEnum.CLOC.getSynchronizeClient(client);

        assertEquals(expectedClient, actualClient);
    }

    @Test
    void clunEventCodeProducesClunSynchronizeClient() {

        ClunSynchronizeClient expectedClient = new ClunSynchronizeClient(client);
        SynchronizeClient actualClient = EventTypeEnum.CLUN.getSynchronizeClient(client);

        assertEquals(expectedClient, actualClient);
    }

    @Test
    void crelEventCodeProducesClunSynchronizeClient() {

        CrelSynchronizeClient expectedClient = new CrelSynchronizeClient(client);
        SynchronizeClient actualClient = EventTypeEnum.CREL.getSynchronizeClient(client);

        assertEquals(expectedClient, actualClient);
    }
}
