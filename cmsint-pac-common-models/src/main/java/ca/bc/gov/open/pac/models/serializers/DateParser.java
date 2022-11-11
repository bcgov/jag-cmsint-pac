package ca.bc.gov.open.pac.models.serializers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateParser {
    public static Date parseDate(String pattern, Locale locale, TimeZone timezone, String source)
            throws ParseException {
        var sdf = new SimpleDateFormat(pattern, locale);
        sdf.setTimeZone(timezone);
        return sdf.parse(source);
    }

    public static Instant parseDateToInstant(
            String pattern, Locale locale, TimeZone timezone, String source) throws ParseException {
        return parseDate(pattern, locale, timezone, source).toInstant();
    }
}
