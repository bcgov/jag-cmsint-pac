package ca.bc.gov.pac.open.jag.pac.consumer.model;

import ca.bc.gov.open.pac.models.Client;

public class ClunSynchronizeClient extends SynchronizeClient {
    public ClunSynchronizeClient(Client client) {
        csNumber = client.getCsNum();
        surname = client.getSurname();
        givenName1 = client.getGivenName1();
        givenName2 = client.getGivenName2();
        birthDate = client.getBirthDate();
        gender = client.getGender();
        photoGuid = client.getPhotoGUID();
        centre = client.getCustodyCenter();
        livingUnit = client.getLivingUnit();
    }
}
