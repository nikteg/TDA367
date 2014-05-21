package edu.chalmers.sankoss.core.protocol;

/**
 * Holds and object of the error protocol
 * @author Fredrik Thune
 */
public class ErrorMsg {
    private Object errorObject;
    private String errorMessage;

    public ErrorMsg(Object errorObject, String errorMessage) {
        this.errorObject = errorObject;
        this.errorMessage = errorMessage;
    }
}
