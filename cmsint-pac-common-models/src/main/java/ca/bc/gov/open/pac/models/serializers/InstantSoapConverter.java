package ca.bc.gov.open.pac.models.serializers;

import static ca.bc.gov.open.pac.models.serializers.DateParser.parseDateToInstant;

import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class InstantSoapConverter {

    private static final String timezoneString = "GMT-7";
    private static final TimeZone timezone = TimeZone.getTimeZone(timezoneString);
    private static final String longDatePattern = "yyyy-MM-dd HH:mm:ss.SSSSSS";
    private static final String shortDatePattern = "dd-MMM-yy";
    private static final String printDatePattern = "yyyy-MM-dd HH:mm:ss.0";
    private static final Locale locale = Locale.US;

    private InstantSoapConverter() {}

    public static String print(Instant value) {
        return DateTimeFormatter.ofPattern(printDatePattern)
                .withZone(ZoneId.of(timezoneString))
                .withLocale(locale)
                .format(value);
    }

    public static Instant parse(String value) {
        try {
            return parseDateToInstant(longDatePattern, locale, timezone, value);
        } catch (ParseException ignored) {
        }

        try {
            return parseDateToInstant(shortDatePattern, locale, timezone, value);
        } catch (ParseException ignored) {
        }

        try {
            return Instant.parse(value + "Z");
        } catch (DateTimeParseException ignored) {
            log.warn("Bad date received from soap request - invalid date format: " + value);
            return null;
        }
    }

    public static Instant parseISO(String value) {
        try {
            return Instant.parse(value);
        } catch (Exception ex) {
            log.warn("Bad date received from soap request - invalid date format: " + value);
            return null;
        }
    }

    public static String printISO(Instant value) {
        return value.toString();
    }
}
