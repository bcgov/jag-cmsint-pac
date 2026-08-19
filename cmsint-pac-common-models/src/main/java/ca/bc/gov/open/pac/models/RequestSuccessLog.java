package ca.bc.gov.open.pac.models;

import com.fasterxml.jackson.core.JsonProcessingException;

public class RequestSuccessLog implements WriteAsString {
    private String type;
    private String endpoint;

    public RequestSuccessLog(String type, String endpoint) {
        this.type = type;
        this.endpoint = endpoint;
    }

    public String getType() {
        return type;
    }

    public String getEndpoint() {
        return endpoint;
    }

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
