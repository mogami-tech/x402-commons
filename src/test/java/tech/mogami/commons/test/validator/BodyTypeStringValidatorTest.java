package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.BodyTypeStringValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Body type string validator tests")
public class BodyTypeStringValidatorTest {

    BodyTypeStringValidator validator = new BodyTypeStringValidator();

    @Test
    @DisplayName("Should return false for null value")
    void nullValue() {
        assertThat(validator.isValid(null, null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for empty value")
    void emptyValue() {
        assertThat(validator.isValid("", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for blank value")
    void BlankValue() {
        assertThat(validator.isValid("   ", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for invalid body types")
    void invalidBodyTypes() {
        assertThat(validator.isValid("INVALID", null)).isFalse();
        assertThat(validator.isValid("xml", null)).isFalse();
        assertThat(validator.isValid("binary", null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for valid body types")
    void validBodyTypes() {
        assertThat(validator.isValid("json", null)).isTrue();
        assertThat(validator.isValid("form-data", null)).isTrue();
        assertThat(validator.isValid("text", null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for valid body types in mixed case")
    void mixedCase() {
        assertThat(validator.isValid("Json", null)).isTrue();
        assertThat(validator.isValid("JSON", null)).isTrue();

        assertThat(validator.isValid("FORM-DATA", null)).isTrue();
        assertThat(validator.isValid("Form-Data", null)).isTrue();

        assertThat(validator.isValid("TeXt", null)).isTrue();
        assertThat(validator.isValid("TEXT", null)).isTrue();
    }

}
