package ca.bc.gov.open.pac.models;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.bc.gov.open.pac.models.ords.DemographicsEntity;
import ca.bc.gov.open.pac.models.ords.EventEntity;
import ca.bc.gov.open.pac.models.ords.ProcessEntity;
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;

class ClientTest {
    private final ProcessEntity processEntity = TestClientInitializer.getProcessInstance();
    private final EventEntity eventEntity = TestClientInitializer.getEventEntity();
    private final Client actualClient = new Client(processEntity, eventEntity);
    private final DemographicsEntity actualDemographics =
            TestClientInitializer.getDemographicsEntity();

    @Test
    void constructorWithNewEventAndEventTypeCodeProperlyMapsTheValuesToTheFields() {
        EventEntity eventEntity =
                new EventEntity("client id", "event sequence number", "event type code");
        ProcessEntity processEntity = new ProcessEntity("1", "1", "1");
        Client expectedClient = new Client(processEntity, eventEntity);

        assertEquals(expectedClient.getClientNumber(), processEntity.getClientNumber());
        assertEquals(expectedClient.getEventSeqNum(), processEntity.getEventSeqNum());
        assertEquals(expectedClient.getComputerSystemCd(), processEntity.getComputerSystemCd());
        assertEquals(expectedClient.getEventTypeCode(), eventEntity.getEventTypeCode());
    }

    @Test
    void constructorWithClientAndDemographicsEntityGetTheRightValuesFromBoth() {
        Client expectedClient = new Client(actualClient, actualDemographics);

        clientRelatedAssertions(expectedClient);

        demographicInfoRelatedAssertions(expectedClient);
    }

    private void demographicInfoRelatedAssertions(Client expectedClient) {
        assertEquals(expectedClient.getDemographicInfo().getCsNum(), actualDemographics.getCsNum());
        assertEquals(
                expectedClient.getDemographicInfo().getSurname(), actualDemographics.getSurname());
        assertEquals(
                expectedClient.getDemographicInfo().getGivenName1(),
                actualDemographics.getGivenName1());
        assertEquals(
                expectedClient.getDemographicInfo().getGivenName2(),
                actualDemographics.getGivenName2());
        assertEquals(
                expectedClient.getDemographicInfo().getBirthDate(),
                actualDemographics.getBirthDate());
        assertEquals(
                expectedClient.getDemographicInfo().getGender(), actualDemographics.getGender());
        assertEquals(
                expectedClient.getDemographicInfo().getPhotoGUID(),
                actualDemographics.getPhotoGUID());
        assertEquals(
                expectedClient.getDemographicInfo().getProbableDischargeDate(),
                actualDemographics.getProbableDischargeDate());
        assertEquals(
                expectedClient.getDemographicInfo().getOutReason(),
                actualDemographics.getOutReason());
        assertEquals(
                expectedClient.getDemographicInfo().getIsActive(),
                actualDemographics.getIsActive());
        assertEquals(
                expectedClient.getDemographicInfo().getFromCsNum(),
                actualDemographics.getFromCsNum());
        assertEquals(
                expectedClient.getDemographicInfo().getMergeUserId(),
                actualDemographics.getMergeUserId());
        assertEquals(
                expectedClient.getDemographicInfo().getLivingUnit(),
                actualDemographics.getLivingUnit());
        assertEquals(
                expectedClient.getDemographicInfo().getIcsLocationCd(),
                actualDemographics.getIcsLocationCd());
        assertEquals(expectedClient.getDemographicInfo().getIsIn(), actualDemographics.getIsIn());
        assertEquals(
                expectedClient.getDemographicInfo().getSysDate(), actualDemographics.getSysDate());
        assertEquals(
                expectedClient.getDemographicInfo().getUserId(), actualDemographics.getUserId());
        assertEquals(
                expectedClient.getDemographicInfo().getCustodyCenter(),
                actualDemographics.getCustodyCenter());
        assertEquals(
                expectedClient.getDemographicInfo().getPacLocationCd(),
                actualDemographics.getPacLocationCd());
        assertEquals(
                expectedClient.getDemographicInfo().getNextCourtDt(),
                actualDemographics.getNextCourtDt());
    }

    private void clientRelatedAssertions(Client expectedClient) {
        assertEquals(expectedClient.getClientNumber(), actualClient.getClientNumber());
        assertEquals(expectedClient.getEventSeqNum(), actualClient.getEventSeqNum());
        assertEquals(expectedClient.getComputerSystemCd(), actualClient.getComputerSystemCd());
        assertEquals(expectedClient.getStatus(), actualClient.getStatus());
        assertEquals(expectedClient.getEventTypeCode(), actualClient.getEventTypeCode());
    }

    @Test
    void clientObjectCanBeSerialized() {
        assertDoesNotThrow(() -> SerializationUtils.serialize(actualClient));
    }
}
