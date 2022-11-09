package ca.bc.gov.pac.open.jag.pac.consumer.model;

import lombok.Data;

@Data
public class SynchronizeClient {
    private String csNumber;
    private String surname;
    private String givenName1;
    private String givenName2;
    private String birthDate;
    private String gender;
    private String photoGuid;
    private String probableDischargeDate;
    private String outLocation;
    private String outReason;
    private String centre;
    private String livingUnit;
}
