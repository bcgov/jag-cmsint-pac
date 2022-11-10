package ca.bc.gov.open.pac.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void makeSureNewInstanceIsNotTheSameObjectAsTheCurrent() {
        final Client actualClient = new Client("clientNumber"
                , "csNum"
                , "eventSeqNum"
                , "eventTypeCode"
                , "surname"
                , "givenName1"
                , "givenName2"
                , "birthDate"
                , "gender"
                , "photoGUID"
                , "probableDischargeDate"
                , "pacLocationCd"
                , "outReason"
                , "newerSequence"
                , "computerSystemCd"
                , "isActive"
                , "sysDate"
                , "fromCsNum"
                , "userId"
                , "mergeUserId"
                , "icsLocationCd"
                , "isIn"
                , "custodyCenter"
                , "livingUnit"
                , "status");
        Client expectedClient = actualClient.newInstance();
        assertEquals(expectedClient, expectedClient);
        assertNotSame(expectedClient, actualClient);
    }


}