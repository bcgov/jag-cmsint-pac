package ca.bc.gov.open.pac.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrdsErrorLog {

    private String message;
    private String method;
    private String exception;
    private Object request;
}