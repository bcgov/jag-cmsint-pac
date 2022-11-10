package ca.bc.gov.pac.open.jag.pac.consumer.model;

import lombok.Data;

@Data
public abstract class SynchronizeClient {
    protected String csNumber;
    protected String surname;
    protected String givenName1;
    protected String givenName2;
    protected String birthDate;
    protected String gender;
    protected String photoGuid;
    protected String probableDischargeDate;
    protected String outLocation;
    protected String outReason;
    protected String centre;
    protected String livingUnit;
}
