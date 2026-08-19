package ca.bc.gov.open.pac.models.ords;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DemographicsEntity extends BaseEntity {

    @JsonProperty("clientNumber")
    private String clientNumber;

    @JsonProperty("eventTypeCode")
    private String eventTypeCode;

    @JsonProperty("csNum")
    private String csNum;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("givenName1")
    private String givenName1;

    @JsonProperty("givenName2")
    private String givenName2;

    @JsonProperty("birthDate")
    private String birthDate;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("photoGUID")
    private String photoGUID;

    @JsonProperty("probableDischargeDate")
    private String probableDischargeDate;

    @JsonProperty("outReason")
    private String outReason;

    @JsonProperty("isActive")
    private String isActive;

    @JsonProperty("fromCsNum")
    private String fromCsNum;

    @JsonProperty("mergeUserId")
    private String mergeUserId;

    @JsonProperty("livingUnit")
    private String livingUnit;

    @JsonProperty("icsLocationCd")
    private String icsLocationCd;

    @JsonProperty("isIn")
    private String isIn;

    @JsonProperty("sysDate")
    private String sysDate;

    @JsonProperty("pacLocationCd")
    private String pacLocationCd;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("custodyCenter")
    private String custodyCenter;

    @JsonProperty("nextCourtDt")
    private String nextCourtDt;

    public DemographicsEntity() {}

    public DemographicsEntity(
            String clientNumber,
            String eventTypeCode,
            String csNum,
            String surname,
            String givenName1,
            String givenName2,
            String birthDate,
            String gender,
            String photoGUID,
            String probableDischargeDate,
            String outReason,
            String isActive,
            String fromCsNum,
            String mergeUserId,
            String livingUnit,
            String icsLocationCd,
            String isIn,
            String sysDate,
            String pacLocationCd,
            String userId,
            String custodyCenter,
            String nextCourtDt) {
        this.clientNumber = clientNumber;
        this.eventTypeCode = eventTypeCode;
        this.csNum = csNum;
        this.surname = surname;
        this.givenName1 = givenName1;
        this.givenName2 = givenName2;
        this.birthDate = birthDate;
        this.gender = gender;
        this.photoGUID = photoGUID;
        this.probableDischargeDate = probableDischargeDate;
        this.outReason = outReason;
        this.isActive = isActive;
        this.fromCsNum = fromCsNum;
        this.mergeUserId = mergeUserId;
        this.livingUnit = livingUnit;
        this.icsLocationCd = icsLocationCd;
        this.isIn = isIn;
        this.sysDate = sysDate;
        this.pacLocationCd = pacLocationCd;
        this.userId = userId;
        this.custodyCenter = custodyCenter;
        this.nextCourtDt = nextCourtDt;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getEventTypeCode() {
        return eventTypeCode;
    }

    public void setEventTypeCode(String eventTypeCode) {
        this.eventTypeCode = eventTypeCode;
    }

    public String getCsNum() {
        return csNum;
    }

    public void setCsNum(String csNum) {
        this.csNum = csNum;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGivenName1() {
        return givenName1;
    }

    public void setGivenName1(String givenName1) {
        this.givenName1 = givenName1;
    }

    public String getGivenName2() {
        return givenName2;
    }

    public void setGivenName2(String givenName2) {
        this.givenName2 = givenName2;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhotoGUID() {
        return photoGUID;
    }

    public void setPhotoGUID(String photoGUID) {
        this.photoGUID = photoGUID;
    }

    public String getProbableDischargeDate() {
        return probableDischargeDate;
    }

    public void setProbableDischargeDate(String probableDischargeDate) {
        this.probableDischargeDate = probableDischargeDate;
    }

    public String getOutReason() {
        return outReason;
    }

    public void setOutReason(String outReason) {
        this.outReason = outReason;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getFromCsNum() {
        return fromCsNum;
    }

    public void setFromCsNum(String fromCsNum) {
        this.fromCsNum = fromCsNum;
    }

    public String getMergeUserId() {
        return mergeUserId;
    }

    public void setMergeUserId(String mergeUserId) {
        this.mergeUserId = mergeUserId;
    }

    public String getLivingUnit() {
        return livingUnit;
    }

    public void setLivingUnit(String livingUnit) {
        this.livingUnit = livingUnit;
    }

    public String getIcsLocationCd() {
        return icsLocationCd;
    }

    public void setIcsLocationCd(String icsLocationCd) {
        this.icsLocationCd = icsLocationCd;
    }

    public String getIsIn() {
        return isIn;
    }

    public void setIsIn(String isIn) {
        this.isIn = isIn;
    }

    public String getSysDate() {
        return sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    public String getPacLocationCd() {
        return pacLocationCd;
    }

    public void setPacLocationCd(String pacLocationCd) {
        this.pacLocationCd = pacLocationCd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustodyCenter() {
        return custodyCenter;
    }

    public void setCustodyCenter(String custodyCenter) {
        this.custodyCenter = custodyCenter;
    }

    public String getNextCourtDt() {
        return nextCourtDt;
    }

    public void setNextCourtDt(String nextCourtDt) {
        this.nextCourtDt = nextCourtDt;
    }
}
