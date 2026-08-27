package ca.bc.gov.open.pac.models.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Locale;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InstantDeserializer extends JsonDeserializer<Instant> {
    @Override
    public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        String longPattern = "dd-MMM-yy hh.mm.ss.SSSSSS a";
        String shortIsoPattern = "yyyy-MMM-dd";
        String shortPattern = "dd-MMM-yy";
        TimeZone timeZone = TimeZone.getTimeZone("GMT-7");
        Locale locale = Locale.US;

        String text = jsonParser.getText();

        try {
            if (text.split("-")[0].length() < 4) {
                return DateParser.parseDateToInstant(longPattern, locale, timeZone, text);
            } else {
                return DateParser.parseDateToInstant(shortIsoPattern, locale, timeZone, text);
            }
        } catch (ParseException e) {
            try {
                return DateParser.parseDateToInstant(shortPattern, locale, timeZone, text);
            } catch (ParseException e2) {
                log.error(e2.getLocalizedMessage());
            }
        }
        return null;
    }
}
