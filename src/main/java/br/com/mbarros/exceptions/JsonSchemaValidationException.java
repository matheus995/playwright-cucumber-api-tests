package br.com.mbarros.exceptions;

public class JsonSchemaValidationException extends Exception {

    public JsonSchemaValidationException() {
        super();
    }

    public JsonSchemaValidationException(String message) {
        super(message);
    }

    public JsonSchemaValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonSchemaValidationException(Throwable cause) {
        super(cause);
    }
}