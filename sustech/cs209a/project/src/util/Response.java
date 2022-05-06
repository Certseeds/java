package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Response.
 */
public class Response {
    /**
     * The Object mapper.
     */
    static ObjectMapper objectMapper = new ObjectMapper();
    /**
     * The Code.
     */
    int code;
    /**
     * The Message.
     */
    String message;
    /**
     * The Result.
     */
    ObjectNode result;

    /**
     * Instantiates a new Response.
     *
     * @param code    the code
     * @param message the message
     */
    public Response(int code, String message) {
        this.code = code;
        this.message = message;
        this.result = objectMapper.createObjectNode();
    }

    /**
     * Instantiates a new Response.
     *
     * @param code    the code
     * @param message the message
     * @param result  the result
     */
    public Response(int code, String message, ObjectNode result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    /**
     * Sets object mapper.
     *
     * @param objectMapper the object mapper
     */
    public static void setObjectMapper(ObjectMapper objectMapper) {
        Response.objectMapper = objectMapper;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     * @return the code
     */
    public Response setCode(int code) {
        this.code = code;
        return this;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     * @return the message
     */
    public Response setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public ObjectNode getResult() {
        return result;
    }

    /**
     * Sets result.
     *
     * @param result the result
     * @return the result
     */
    public Response setResult(ObjectNode result) {
        this.result = result;
        return this;
    }
}
