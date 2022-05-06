package util;

/**
 * The enum Operation.
 */
public enum Operation {

    /**
     * Upload operation.
     */
    UPLOAD,
    /**
     * Download operation.
     */
    DOWNLOAD,
    /**
     * Compare operation.
     */
    COMPARE,
    /**
     * Exists operation.
     */
    EXISTS,
    /**
     * List operation.
     */
    LIST,
    /**
     * Break operation.
     */
    BREAK,
    /**
     * Not any one operation.
     */
    NOT_ANY_ONE;

    /**
     * Parse operation operation.
     *
     * @param op the op String,
     * @return the operation
     */
    public static Operation parseOperation(String op) {
        return switch (op.toLowerCase()) {
            case "upload" -> Operation.UPLOAD;
            case "download" -> Operation.DOWNLOAD;
            case "compare" -> Operation.COMPARE;
            case "exists" -> Operation.EXISTS;
            case "list" -> Operation.LIST;
            case "break" -> Operation.BREAK;
            default -> Operation.NOT_ANY_ONE;
        };
    }
}
