package ca.bc.gov.open.pac.models;

import ca.bc.gov.open.pac.models.dateFormatters.DateFormatterInterface;
import ca.bc.gov.open.pac.models.ords.DemographicsEntity;
import java.io.Serializable;
import java.util.Objects;

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

    public DemographicInfo(
            String csNum,
            String surname,
            String givenName1,
            String givenName2,
            String gender,
            String photoGUID,
            String pacLocationCd,
            String outReason,
            String isActive,
            String fromCsNum,
            String userId,
            String mergeUserId,
            String icsLocationCd,
            String isIn,
            String custodyCenter,
            String livingUnit,
            String birthDate,
            String probableDischargeDate,
            String sysDate,
            String nextCourtDt) {
        this.csNum = csNum;
        this.surname = surname;
        this.givenName1 = givenName1;
        this.givenName2 = givenName2;
        this.gender = gender;
        this.photoGUID = photoGUID;
        this.pacLocationCd = pacLocationCd;
        this.outReason = outReason;
        this.isActive = isActive;
        this.fromCsNum = fromCsNum;
        this.userId = userId;
        this.mergeUserId = mergeUserId;
        this.icsLocationCd = icsLocationCd;
        this.isIn = isIn;
        this.custodyCenter = custodyCenter;
        this.livingUnit = livingUnit;
        this.birthDate = birthDate;
        this.probableDischargeDate = probableDischargeDate;
        this.sysDate = sysDate;
        this.nextCourtDt = nextCourtDt;
    }

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

    public String getCsNum() {
        return csNum;
    }

    public String getSurname() {
        return surname;
    }

    public String getGivenName1() {
        return givenName1;
    }

    public String getGivenName2() {
        return givenName2;
    }

    public String getGender() {
        return gender;
    }

    public String getPhotoGUID() {
        return photoGUID;
    }

    public String getPacLocationCd() {
        return pacLocationCd;
    }

    public String getOutReason() {
        return outReason;
    }

    public String getIsActive() {
        return isActive;
    }

    public String getFromCsNum() {
        return fromCsNum;
    }

    public String getUserId() {
        return userId;
    }

    public String getMergeUserId() {
        return mergeUserId;
    }

    public String getIcsLocationCd() {
        return icsLocationCd;
    }

    public String getIsIn() {
        return isIn;
    }

    public String getCustodyCenter() {
        return custodyCenter;
    }

    public String getLivingUnit() {
        return livingUnit;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getProbableDischargeDate() {
        return probableDischargeDate;
    }

    public String getSysDate() {
        return sysDate;
    }

    public String getNextCourtDt() {
        return nextCourtDt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DemographicInfo)) return false;
        DemographicInfo other = (DemographicInfo) o;
        return other.canEqual(this)
                && Objects.equals(this.csNum, other.csNum)
                && Objects.equals(this.surname, other.surname)
                && Objects.equals(this.givenName1, other.givenName1)
                && Objects.equals(this.givenName2, other.givenName2)
                && Objects.equals(this.gender, other.gender)
                && Objects.equals(this.photoGUID, other.photoGUID)
                && Objects.equals(this.pacLocationCd, other.pacLocationCd)
                && Objects.equals(this.outReason, other.outReason)
                && Objects.equals(this.isActive, other.isActive)
                && Objects.equals(this.fromCsNum, other.fromCsNum)
                && Objects.equals(this.userId, other.userId)
                && Objects.equals(this.mergeUserId, other.mergeUserId)
                && Objects.equals(this.icsLocationCd, other.icsLocationCd)
                && Objects.equals(this.isIn, other.isIn)
                && Objects.equals(this.custodyCenter, other.custodyCenter)
                && Objects.equals(this.livingUnit, other.livingUnit)
                && Objects.equals(this.birthDate, other.birthDate)
                && Objects.equals(this.probableDischargeDate, other.probableDischargeDate)
                && Objects.equals(this.sysDate, other.sysDate)
                && Objects.equals(this.nextCourtDt, other.nextCourtDt);
    }

    protected boolean canEqual(Object other) {
        return other instanceof DemographicInfo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
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
                nextCourtDt);
    }

    @Override
    public String toString() {
        return "DemographicInfo("
                + "csNum=" + csNum
                + ", surname=" + surname
                + ", givenName1=" + givenName1
                + ", givenName2=" + givenName2
                + ", gender=" + gender
                + ", photoGUID=" + photoGUID
                + ", pacLocationCd=" + pacLocationCd
                + ", outReason=" + outReason
                + ", isActive=" + isActive
                + ", fromCsNum=" + fromCsNum
                + ", userId=" + userId
                + ", mergeUserId=" + mergeUserId
                + ", icsLocationCd=" + icsLocationCd
                + ", isIn=" + isIn
                + ", custodyCenter=" + custodyCenter
                + ", livingUnit=" + livingUnit
                + ", birthDate=" + birthDate
                + ", probableDischargeDate=" + probableDischargeDate
                + ", sysDate=" + sysDate
                + ", nextCourtDt=" + nextCourtDt
                + ")";
    }
}
