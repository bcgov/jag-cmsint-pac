package ca.bc.gov.open.pac.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestSuccessLog implements WriteAsString {
    private String type;
    private String endpoint;

    @Override
    public String toString() {
        String s = "";
        try {
            s = writeAsString();
        } catch (JsonProcessingException ignored) {
        }
        return s;
    }
}
