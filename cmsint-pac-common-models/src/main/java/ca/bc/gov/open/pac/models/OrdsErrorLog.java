package ca.bc.gov.open.pac.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrdsErrorLog implements WriteAsString {

    private String message;
    private String method;
    private String exception;
    private Object request;

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
