package ca.bc.gov.open.pac.models.serializers;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Slf4j
public final class InstantSoapConverter {

    private InstantSoapConverter() {
    }

    public static String print(Instant value) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0")
                .withZone(ZoneId.of("GMT-7"))
                .withLocale(Locale.US)
                .format(value);
    }

    private static Date parseDate(String pattern, Locale locale, TimeZone tz, String source)
            throws ParseException {
        var sdf = new SimpleDateFormat(pattern, locale);
        sdf.setTimeZone(tz);
        return sdf.parse(source);
    }

    public static Instant parse(String value) {
        var tz = TimeZone.getTimeZone("GMT-7");
        var longDatePattern = "yyyy-MM-dd HH:mm:ss.SSSSSS";
        var shortDatePattern = "dd-MMM-yy";

        try {
            return parseDate(longDatePattern, Locale.US, tz, value).toInstant();
        } catch (ParseException ignored) {
        }

        try {
            return parseDate(shortDatePattern, Locale.US, tz, value).toInstant();
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
