package ca.bc.gov.open.pac.models.serializers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public final class InstantSoapConverterTest {

    private static final String longStringDate = "2022-01-01 02:12:12.000001";
    private static final Long longStringSeconds = 1641028332L;
    private static final int longStringNanos = 1000000;

    private static final String shortStringDate = "01-JAN-22";
    private static final Long shortStringSeconds = 1641020400L;
    private static final int shortStringNanos = 0;

    private static final String isoStringDate = "2022-01-01T09:12:12.000001";
    private static final Long isoStringSeconds = 1641028332L;
    private static final int isoStringNanos = 1000;

    private static Stream<Arguments> parseReturnsInstantWhenValueHasValidFormatFormatValues() {
        return Stream.of(
                arguments("Long Format", longStringDate, longStringSeconds, longStringNanos),
                arguments("Short Format", shortStringDate, shortStringSeconds, shortStringNanos),
                arguments("Iso Format", isoStringDate, isoStringSeconds, isoStringNanos));
    }

    @ParameterizedTest(name = "[{index} - {0}] -> date={1} expectedSeconds={2} expectedNanos={3}")
    @MethodSource("parseReturnsInstantWhenValueHasValidFormatFormatValues")
    void parseReturnsInstantWhenValueHasValidFormatFormat(
            String caseName, String date, Long expectedSeconds, int expectedNanos) {
        Instant instant = InstantSoapConverter.parse(date);
        assertEquals(expectedSeconds, instant.getEpochSecond());
        assertEquals(expectedNanos, instant.getNano());
    }

    @Test
    void parseReturnsNullWhenFormatIsNotRecognizable() {
        assertNull(InstantSoapConverter.parse("mumbo jumbo"));
    }

    @Test
    void parseIsoReturnsIntantWhenValueInIsoFormat() {
        Instant instant = InstantSoapConverter.parseISO(isoStringDate + "Z");
        assertEquals(isoStringSeconds, instant.getEpochSecond());
        assertEquals(isoStringNanos, instant.getNano());
    }


    @Test
    void parseIsoReturnsNullWhenFormatIsNotRecognizable() {
        assertNull(InstantSoapConverter.parseISO("mumbo jumbo"));
    }
}
