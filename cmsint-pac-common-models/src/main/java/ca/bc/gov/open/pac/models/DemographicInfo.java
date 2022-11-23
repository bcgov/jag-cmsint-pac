package ca.bc.gov.open.pac.models;

import ca.bc.gov.open.pac.models.ords.DemographicsEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class DemographicInfo {
    private final String csNum;
    private final String surname;
    private final String givenName1;
    private final String givenName2;
    private final String gender;
    private final String photoGUID;
    private final String pacLocationCd;
    private final String outReason;
    private final String isActive;
    private final String fromCsNum;
    private final String userId;
    private final String mergeUserId;
    private final String icsLocationCd;
    private final String isIn;
    private final String custodyCenter;
    private final String livingUnit;
    private final String birthDate;
    private final String probableDischargeDate;
    private final String sysDate;

    public DemographicInfo(DemographicsEntity demographicsEntity) {
        csNum = demographicsEntity.getCsNum();
        surname = demographicsEntity.getSurname();
        givenName1 = demographicsEntity.getGivenName1();
        givenName2 = demographicsEntity.getGivenName2();
        birthDate = demographicsEntity.getBirthDate();
        gender = demographicsEntity.getGender();
        photoGUID = demographicsEntity.getPhotoGUID();
        probableDischargeDate = demographicsEntity.getProbableDischargeDate();
        outReason = demographicsEntity.getOutReason();
        isActive = demographicsEntity.getIsActive();
        fromCsNum = demographicsEntity.getFromCsNum();
        mergeUserId = demographicsEntity.getMergeUserId();
        livingUnit = demographicsEntity.getLivingUnit();
        icsLocationCd = demographicsEntity.getIcsLocationCd();
        isIn = demographicsEntity.getIsIn();
        sysDate = demographicsEntity.getSysDate();
        userId = demographicsEntity.getUserId();
        pacLocationCd = demographicsEntity.getPacLocationCd();
        custodyCenter = demographicsEntity.getCustodyCenter();
    }

    public DemographicInfo() {
        csNum = null;
        surname = null;
        givenName1 = null;
        givenName2 = null;
        birthDate = null;
        gender = null;
        photoGUID = null;
        probableDischargeDate = null;
        outReason = null;
        isActive = null;
        fromCsNum = null;
        mergeUserId = null;
        livingUnit = null;
        icsLocationCd = null;
        isIn = null;
        sysDate = null;
        pacLocationCd = null;
        userId = null;
        custodyCenter = null;
    }
}
