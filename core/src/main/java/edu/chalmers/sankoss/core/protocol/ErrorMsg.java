package edu.chalmers.sankoss.core.protocol;

/**
 * Holds and object of the error protocol
 * @author Fredrik Thune
 */
public class ErrorMsg {
    private Object errorObject;
    private String errorMessage;

    public ErrorMsg() {
    }

    public ErrorMsg(Object errorObject, String errorMessage) {
        this.errorObject = errorObject;
        this.errorMessage = errorMessage;
    }

    public Object getErrorObject() {
        return errorObject;
    }

    public void setErrorObject(Object errorObject) {
        this.errorObject = errorObject;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
