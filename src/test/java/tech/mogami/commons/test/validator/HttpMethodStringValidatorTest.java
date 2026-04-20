package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.HttpMethodStringValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HTTP method string validator tests")
public class HttpMethodStringValidatorTest {

    HttpMethodStringValidator validator = new HttpMethodStringValidator();

    @Test
    @DisplayName("Should return false for null value")
    void nullHttpMethod() {
        assertThat(validator.isValid(null, null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for empty value")
    void emptyHttpMethod() {
        assertThat(validator.isValid("", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for blank value")
    void blankHttpMethod() {
        assertThat(validator.isValid("   ", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for invalid HTTP method")
    void invalidHttpMethod() {
        assertThat(validator.isValid("INVALID", null)).isFalse();
        assertThat(validator.isValid("OPTIONS", null)).isFalse();
        assertThat(validator.isValid("TRACE", null)).isFalse();
        assertThat(validator.isValid("CONNECT", null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for valid HTTP methods")
    void validHttpMethod() {
        assertThat(validator.isValid("GET", null)).isTrue();
        assertThat(validator.isValid("get", null)).isTrue();
        assertThat(validator.isValid("GeT", null)).isTrue();

        assertThat(validator.isValid("POST", null)).isTrue();
        assertThat(validator.isValid("post", null)).isTrue();
        assertThat(validator.isValid("pOst", null)).isTrue();

        assertThat(validator.isValid("PUT", null)).isTrue();
        assertThat(validator.isValid("put", null)).isTrue();

        assertThat(validator.isValid("DELETE", null)).isTrue();
        assertThat(validator.isValid("delete", null)).isTrue();

        assertThat(validator.isValid("PATCH", null)).isTrue();
        assertThat(validator.isValid("patch", null)).isTrue();

        assertThat(validator.isValid("HEAD", null)).isTrue();
        assertThat(validator.isValid("head", null)).isTrue();
    }

}
