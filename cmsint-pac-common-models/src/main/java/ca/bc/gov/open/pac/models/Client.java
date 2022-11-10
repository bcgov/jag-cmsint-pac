package ca.bc.gov.open.pac.models;

import java.io.Serializable;
import java.lang.reflect.Field;
import lombok.*;
import org.springframework.util.SerializationUtils;

@Data
@AllArgsConstructor
public class Client implements Serializable {
    private final String clientNumber;
    private final String csNum;
    private final String eventSeqNum;
    private final String eventTypeCode;
    private final String surname;
    private final String givenName1;
    private final String givenName2;
    private final String birthDate;
    private final String gender;
    private final String photoGUID;
    private final String probableDischargeDate;
    private final String pacLocationCd;
    private final String outReason;
    private final String newerSequence;
    private final String computerSystemCd;
    private final String isActive;
    private final String sysDate;
    private final String fromCsNum;
    private final String userId;
    private final String mergeUserId;
    private final String icsLocationCd;
    private final String isIn;
    private final String custodyCenter;
    private final String livingUnit;
    // to accept the status if update process cancels
    private final String status;

    public Client getCopy() {
        byte[] serializedClient = SerializationUtils.serialize(this);
        return (Client) SerializationUtils.deserialize(serializedClient);
    }

    public Client getCopyWithNewEventTypeCode(String eventTypeCode) {
        Client deserializedClient = getCopy();

        try {
            Field modifiersField = Client.class.getDeclaredField("eventTypeCode");
            modifiersField.setAccessible(true);
            modifiersField.set(deserializedClient, eventTypeCode);
            modifiersField.setAccessible(false);
            return deserializedClient;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
