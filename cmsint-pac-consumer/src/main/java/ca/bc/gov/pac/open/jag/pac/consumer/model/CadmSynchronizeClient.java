package ca.bc.gov.pac.open.jag.pac.consumer.model;

import ca.bc.gov.open.pac.models.Client;
import lombok.Getter;

@Getter
public class CadmSynchronizeClient extends SynchronizeClient {

    public CadmSynchronizeClient(Client client) {
        csNumber = client.getCsNum();
        surname = client.getSurname();
        givenName1 = client.getGivenName1();
        givenName2 = client.getGivenName2();
        birthDate = client.getBirthDate();
        gender = client.getGender();
        photoGuid = client.getPhotoGUID();
        probableDischargeDate = client.getProbableDischargeDate();
        centre = client.getCustodyCenter();
        livingUnit = client.getLivingUnit();
    }
}
