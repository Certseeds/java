package util;

/**
 * The type Failure response.
 */
public class FailureResponse extends Response {


    /**
     * Instantiates a new Failure response.
     *
     * @param code    the code
     * @param message the message
     */
    public FailureResponse(int code, String message) {
        super(code, message);
    }

    /**
     * Instantiates a new Failure response.
     *
     * @param failureCause the failure cause
     */
    public FailureResponse(FailureCause failureCause) {
        super(failureCause.code, failureCause.message);
    }

}

