package app.exceptions;

public class NotAuthorizedException extends Exception {
    private int statuscode;
    public NotAuthorizedException(String message) {
        super(message);
    }

    public NotAuthorizedException(int statuscode, String message) {
        super(message);
        this.statuscode = statuscode;
    }

    public NotAuthorizedException(int statuscode, String message, Throwable cause) {
        super(message, cause);
        this.statuscode = statuscode;
    }

    public int getStatuscode() {
        return statuscode;
    }
}
