package ca.bc.gov.open.pac.models;

import ca.bc.gov.open.pac.models.dateFormatters.DateFormatterInterface;
import ca.bc.gov.open.pac.models.ords.DemographicsEntity;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class DemographicInfo implements Serializable {
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
    private final String nextCourtDt;

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
        nextCourtDt = demographicsEntity.getNextCourtDt();
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
        nextCourtDt = null;
    }

    public DemographicInfo updateBirthDateFormat(DateFormatterInterface dateFormatter) {
        var updateBirthDate = dateFormatter.format(birthDate);
        return new DemographicInfo(
                csNum,
                surname,
                givenName1,
                givenName2,
                gender,
                photoGUID,
                pacLocationCd,
                outReason,
                isActive,
                fromCsNum,
                userId,
                mergeUserId,
                icsLocationCd,
                isIn,
                custodyCenter,
                livingUnit,
                updateBirthDate,
                probableDischargeDate,
                sysDate,
                nextCourtDt);
    }

    public DemographicInfo updateProbableDischargeDateDateFormat(
            DateFormatterInterface dateFormatter) {
        var updateProbableDischargeDate = dateFormatter.format(probableDischargeDate);
        return new DemographicInfo(
                csNum,
                surname,
                givenName1,
                givenName2,
                gender,
                photoGUID,
                pacLocationCd,
                outReason,
                isActive,
                fromCsNum,
                userId,
                mergeUserId,
                icsLocationCd,
                isIn,
                custodyCenter,
                livingUnit,
                birthDate,
                updateProbableDischargeDate,
                sysDate,
                nextCourtDt);
    }

    public DemographicInfo updateSysDateFormat(DateFormatterInterface dateFormatter) {
        var updateSysDate = dateFormatter.format(sysDate);
        return new DemographicInfo(
                csNum,
                surname,
                givenName1,
                givenName2,
                gender,
                photoGUID,
                pacLocationCd,
                outReason,
                isActive,
                fromCsNum,
                userId,
                mergeUserId,
                icsLocationCd,
                isIn,
                custodyCenter,
                livingUnit,
                birthDate,
                probableDischargeDate,
                updateSysDate,
                nextCourtDt);
    }

    public DemographicInfo updateNextCourtDtFormat(DateFormatterInterface dateFormatter) {
        var updateNextCourtDt = dateFormatter.format(nextCourtDt);
        return new DemographicInfo(
                csNum,
                surname,
                givenName1,
                givenName2,
                gender,
                photoGUID,
                pacLocationCd,
                outReason,
                isActive,
                fromCsNum,
                userId,
                mergeUserId,
                icsLocationCd,
                isIn,
                custodyCenter,
                livingUnit,
                birthDate,
                probableDischargeDate,
                sysDate,
                updateNextCourtDt);
    }
}
