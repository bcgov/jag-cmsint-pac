package ca.bc.gov.open.pac.models;

import ca.bc.gov.open.pac.models.ords.DemographicsEntity;
import ca.bc.gov.open.pac.models.ords.EventEntity;
import ca.bc.gov.open.pac.models.ords.ProcessEntity;

public class TestClientInitializer {
    private static final String clientNumber = "clientNumber";
    private static final String eventSeqNumber = "eventSeqNumber";
    private static final String computerSystemCd = "computerSystemCd";
    private static final String eventTypeCode = "eventTypeCode";
    private static final String csNum = "csNum";
    private static final String surname = "surname";
    private static final String givenName1 = "givenName1";
    private static final String givenName2 = "givenName2";
    private static final String birthDate = "birthDate";
    private static final String gender = "gender";
    private static final String photoGUID = "photoGUID";
    private static final String probableDischargeDate = "probableDischargeDate";
    private static final String pacLocationCd = "pacLocationCd";
    private static final String outReason = "outReason";
    private static final String isActive = "isActive";
    private static final String sysDate = "sysDate";
    private static final String fromCsNum = "fromCsNum";
    private static final String userId = "userId";
    private static final String mergeUserId = "mergeUserId";
    private static final String icsLocationCd = "icsLocationCd";
    private static final String isIn = "isIn";
    private static final String custodyCenter = "custodyCenter";
    private static final String livingUnit = "livingUnit";
    private static final String nextCourtDt = "nextCourtDt";

    public static Client getClientInstance() {
        return new Client(getProcessInstance(), getEventEntity());
    }

    public static Client getClientInstance(ProcessEntity processEntity, EventEntity eventEntity) {
        return new Client(processEntity, eventEntity);
    }

    public static ProcessEntity getProcessInstance() {
        return new ProcessEntity(clientNumber, eventSeqNumber, computerSystemCd);
    }

    public static EventEntity getEventEntity() {
        return new EventEntity(clientNumber, eventSeqNumber, eventTypeCode);
    }

    public static DemographicsEntity getDemographicsEntity() {
        return new DemographicsEntity(
                clientNumber,
                eventTypeCode,
                csNum,
                surname,
                givenName1,
                givenName2,
                birthDate,
                gender,
                photoGUID,
                probableDischargeDate,
                outReason,
                isActive,
                fromCsNum,
                mergeUserId,
                livingUnit,
                icsLocationCd,
                isIn,
                sysDate,
                pacLocationCd,
                userId,
                custodyCenter,
                nextCourtDt);
    }
}
