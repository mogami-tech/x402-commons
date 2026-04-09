package tech.mogami.commons.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeration representing the type of body content in an HTTP request.
 */
public enum BodyType {

    /** Represents a JSON body type. */
    JSON("json"),

    /** Represents a form-data body type. */
    FORM_DATA("form-data"),

    /** Represents a text body type. */
    TEXT("text");

    /** The string value associated with the enum constant. */
    private final String value;

    BodyType(final String newValue) {
        this.value = newValue;
    }

    @JsonValue
    public String value() {
        return value;
    }

}
