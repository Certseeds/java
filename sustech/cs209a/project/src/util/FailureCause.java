package util;

/**
 * The enum Failure cause.
 */
public enum FailureCause {
    /**
     * The File not found.
     */
    FILE_NOT_FOUND(1, "file not found"),
    /**
     * The Hash not match.
     */
    HASH_NOT_MATCH(2, "hash not match"),
    /**
     * The Already exist.
     */
    ALREADY_EXIST(3, "already exist");

    /**
     * The Code.
     */
    int code;
    /**
     * The Message.
     */
    String message;

    FailureCause(int code, String message) {
        this.code = code;
        this.message = message;
    }
}