package ca.bc.gov.open.pac.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ClientTest {
    final Client actualClient =
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
    void makeSureNewInstanceIsNotTheSameObjectAsTheCurrent() {
        Client expectedClient = actualClient.getCopy();
        assertEquals(expectedClient, expectedClient);
        assertNotSame(expectedClient, actualClient);
    }

    @Test
    void makeSureNewInstanceIsNotTheSameObjectAsTheCurrentButItHasNewEventTypeCode() {
        String newEventTypeCode = "newEventType";
        Client expectedClient = actualClient.getCopyWithNewEventTypeCode(newEventTypeCode);

        assertNotSame(expectedClient, actualClient);
        assertEquals(expectedClient.getEventTypeCode(), newEventTypeCode);
    }
}
