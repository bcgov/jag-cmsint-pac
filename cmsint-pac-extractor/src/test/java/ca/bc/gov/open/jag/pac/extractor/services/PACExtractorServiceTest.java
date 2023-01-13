package ca.bc.gov.open.jag.pac.extractor.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import ca.bc.gov.open.jag.pac.extractor.config.OrdsProperties;
import ca.bc.gov.open.pac.models.Client;
import ca.bc.gov.open.pac.models.DemographicInfo;
import ca.bc.gov.open.pac.models.eventStatus.PendingEventStatus;
import ca.bc.gov.open.pac.models.exceptions.ORDSException;
import ca.bc.gov.open.pac.models.ords.DemographicsEntity;
import ca.bc.gov.open.pac.models.ords.EventEntity;
import ca.bc.gov.open.pac.models.ords.NewerEventEntity;
import ca.bc.gov.open.pac.models.ords.ProcessEntity;
import ca.bc.gov.open.pac.models.ords.UpdateEntryEntity;
import java.net.URI;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

// @ExtendWith(MockitoExtension.class)
@ExtendWith(OutputCaptureExtension.class)
@ActiveProfiles(value = "test")
public class PACExtractorServiceTest {

    private static final String testString = "test";
    private static final String testUrl = "http://example.com";
    private static final String testEndpoint = "/endpoint";
    @Mock private RestTemplate mockRestTemplate;
    @Mock private RabbitTemplate mockRabbitTemplate;
    @Mock private AmqpAdmin mockedAmqpAdmin;

    @Mock private OrdsProperties mockOrdsProperties;
    @Mock private PACExtractorService mockPacExtractorService;

    private static PACExtractorService pacExtractorService;
    private ProcessEntity genericProcessEntity;

    private final String clientNumber = "clientNumber";
    private final String eventSeqNumber = "eventSeqNumber";
    private final String computerSystemCd = "computerSystemCd";
    private final String eventTypeCode = "eventTypeCode";
    private final String csNum = "csNum";
    private final String surname = "surname";
    private final String givenName1 = "givenName1";
    private final String givenName2 = "givenName2";
    private final String birthDate = "birthDate";
    private final String gender = "gender";
    private final String photoGUID = "photoGUID";
    private final String probableDischargeDate = "probableDischargeDate";
    private final String pacLocationCd = "pacLocationCd";
    private final String outReason = "outReason";
    private final String computerSystemCd1 = "computerSystemCd";
    private final String isActive = "isActive";
    private final String sysDate = "sysDate";
    private final String fromCsNum = "fromCsNum";
    private final String userId = "userId";
    private final String mergeUserId = "mergeUserId";
    private final String icsLocationCd = "icsLocationCd";
    private final String isIn = "isIn";
    private final String custodyCenter = "custodyCenter";
    private final String livingUnit = "livingUnit";
    private final Client mockedClient =
            new Client(
                    new ProcessEntity(clientNumber, eventSeqNumber, computerSystemCd),
                    new EventEntity(clientNumber, eventSeqNumber, eventTypeCode),
                    mockRestTemplate,
                    mockOrdsProperties);

    private final DemographicsEntity actualDemographics =
            new DemographicsEntity(
                    clientNumber,
                    eventTypeCode,
                    csNum,
                    surname,
                    givenName1,
                    givenName2,
                    birthDate,
                    gender,
                    photoGUID,
                    probableDischargeDate,
                    outReason,
                    isActive,
                    fromCsNum,
                    mergeUserId,
                    livingUnit,
                    icsLocationCd,
                    isIn,
                    sysDate,
                    "avLocaCd",
                    "avClientLocaUserId",
                    "vLocaCdAlternate");

    @BeforeEach
    void beforeEachSetup() {
        MockitoAnnotations.openMocks(this);

        pacExtractorService =
                new PACExtractorService(
                        mockOrdsProperties,
                        null,
                        mockRestTemplate,
                        mockRabbitTemplate,
                        mockedAmqpAdmin,
                        null);
        when(mockOrdsProperties.getUsername()).thenReturn(testString);
        when(mockOrdsProperties.getPassword()).thenReturn(testString);
        when(mockOrdsProperties.getCmsIntBaseUrl()).thenReturn(testUrl);
        when(mockOrdsProperties.getCmsBaseUrl()).thenReturn(testUrl);
        when(mockOrdsProperties.getEventsEndpoint()).thenReturn(testEndpoint);
        when(mockOrdsProperties.getProcessesEndpoint()).thenReturn(testEndpoint);
        when(mockOrdsProperties.getEventsTypeEndpoint()).thenReturn(testEndpoint);
        when(mockOrdsProperties.getSuccessEndpoint()).thenReturn(testEndpoint);
        when(mockOrdsProperties.getSuccessEndpoint()).thenReturn(testEndpoint);
        when(mockOrdsProperties.getDemographicsEndpoint()).thenReturn(testEndpoint);
        when(mockOrdsProperties.getEntriesEndpoint()).thenReturn(testEndpoint);

        genericProcessEntity = new ProcessEntity("1", "1", "1");

        when(mockedAmqpAdmin.declareQueue(any())).thenReturn(null);

        doNothing().when(mockRestTemplate).put(any(URI.class), any(UpdateEntryEntity.class));
    }

    @Test
    void getNewProcessesHasNoNewProcess() {
        when(mockRestTemplate.getForObject(any(URI.class), eq(ProcessEntity[].class)))
                .thenReturn(new ProcessEntity[0]);

        List<ProcessEntity> newProcesses = pacExtractorService.getNewProcesses();
        assertEquals(0, newProcesses.size());
    }

    @Test
    void getNewProcessesHasNewProcess() {
        ProcessEntity processEntity =
                new ProcessEntity(clientNumber, eventSeqNumber, computerSystemCd);
        ProcessEntity[] processEntitiesArray = {processEntity};

        when(mockRestTemplate.getForObject(any(URI.class), eq(ProcessEntity[].class)))
                .thenReturn(processEntitiesArray);

        List<ProcessEntity> newProcesses = pacExtractorService.getNewProcesses();
        assertEquals(1, newProcesses.size());
        assertEquals(processEntity, newProcesses.get(0));
    }

    @Test
    void getNewProcessesThrowsExceptionWhenExecuting() {
        when(mockRestTemplate.getForObject(any(URI.class), eq(ProcessEntity[].class)))
                .thenThrow(new RuntimeException("test exception"));

        assertThrows(ORDSException.class, pacExtractorService::getNewProcesses);
    }

    @Test
    void pollOrdsForNewRecordsBringsZeroNewRecords(CapturedOutput output) {
        ResponseEntity<ProcessEntity[]> responseEntity =
                new ResponseEntity<>(new ProcessEntity[0], HttpStatus.OK);

        when(mockRestTemplate.getForObject(any(URI.class), eq(ProcessEntity[].class)))
                .thenReturn(new ProcessEntity[0]);

        pacExtractorService.pollOrdsForNewRecords();
    }

    @Test
    void pollOrdsForNewRecordsFailsDuringRequest(CapturedOutput output) {
        when(mockRestTemplate.getForObject(any(URI.class), eq(ProcessEntity[].class)))
                .thenThrow(new RuntimeException("testing exception"));

        pacExtractorService.pollOrdsForNewRecords();
        assertThat(output).contains("Failed to pull new records from the db");
    }

    @Test
    void gettingEventTypeReturnsClientObject() {
        EventEntity eventEntity =
                new EventEntity("client id", "event sequence number", "event type code");

        Client expectedClient = new Client(genericProcessEntity, eventEntity);

        when(mockRestTemplate.getForObject(any(URI.class), eq(EventEntity.class)))
                .thenReturn(eventEntity);

        Client actualClient = pacExtractorService.getEventForProcess(genericProcessEntity);
        assertEquals(expectedClient, actualClient);
    }

    @Test
    void problematicGettingEventTypeRaisesOrdsException() {
        when(mockRestTemplate.getForObject(any(URI.class), eq(EventEntity.class)))
                .thenThrow(ORDSException.class);

        assertThrows(
                ORDSException.class,
                () -> pacExtractorService.getEventForProcess(genericProcessEntity));
    }

    @SneakyThrows
    @ParameterizedTest
    @CsvSource(
            value = {
                "true,ca.bc.gov.open.pac.models.eventStatus.CompletedDuplicateEventStatus",
                "false,ca.bc.gov.open.pac.models.eventStatus.PendingEventStatus"
            })
    void clientOnCorrectStateDependingOnIfItHasOrNotANewerEvent(
            boolean hasNewerEvent, String eventStatusClassString) {

        Class<?> eventStatusClass = Class.forName(eventStatusClassString);
        mockedClient.setStatus(new PendingEventStatus(mockOrdsProperties, mockRestTemplate));

        NewerEventEntity newerEventEntity = new NewerEventEntity();
        newerEventEntity.setHasNewerEvent(hasNewerEvent);

        ResponseEntity<NewerEventEntity> responseEntity =
                new ResponseEntity<>(newerEventEntity, HttpStatus.OK);

        when(mockRestTemplate.getForObject(any(URI.class), eq(NewerEventEntity.class)))
                .thenReturn(newerEventEntity);

        Client actualClient = pacExtractorService.getClientNewerSequence(mockedClient);
        assertEquals(eventStatusClass, actualClient.getStatus().getClass());
    }

    @Test
    void gettingDemographicsInfoFurtherPopulatesTheClientObject() {
        when(mockRestTemplate.getForObject(any(URI.class), eq(DemographicsEntity.class)))
                .thenReturn(actualDemographics);

        Client actualClient = pacExtractorService.getDemographicsInfo(mockedClient);
        assertNotEquals(mockedClient, actualClient);
        assertEquals(new DemographicInfo(actualDemographics), actualClient.getDemographicInfo());
    }

    @Test
    void gettingNullResponseFromOrdsWhenDemographicsInfoThrowsOrdsException() {
        when(mockRestTemplate.getForObject(any(URI.class), eq(DemographicsEntity.class)))
                .thenReturn(null);

        assertThrows(
                ORDSException.class, () -> pacExtractorService.getDemographicsInfo(mockedClient));
    }
}
