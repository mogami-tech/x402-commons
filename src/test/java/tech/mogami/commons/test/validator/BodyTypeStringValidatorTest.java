package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.BodyTypeStringValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Body type string validator tests")
public class BodyTypeStringValidatorTest {

    BodyTypeStringValidator validator;

    @BeforeEach
    void setUp() {
        validator = new BodyTypeStringValidator();
    }

    @Test
    @DisplayName("Should return false for null value")
    void shouldReturnFalseForNullValue() {
        assertThat(validator.isValid(null, null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for empty value")
    void shouldReturnFalseForEmptyValue() {
        assertThat(validator.isValid("", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for blank value")
    void shouldReturnFalseForBlankValue() {
        assertThat(validator.isValid("   ", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for invalid body type")
    void shouldReturnFalseForInvalidBodyType() {
        assertThat(validator.isValid("INVALID", null)).isFalse();
        assertThat(validator.isValid("xml", null)).isFalse();
        assertThat(validator.isValid("binary", null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for valid body types")
    void shouldReturnTrueForValidBodyTypes() {
        assertThat(validator.isValid("json", null)).isTrue();
        assertThat(validator.isValid("form-data", null)).isTrue();
        assertThat(validator.isValid("text", null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for valid body types in uppercase")
    void shouldReturnTrueForValidBodyTypesInUppercase() {
        assertThat(validator.isValid("JSON", null)).isTrue();
        assertThat(validator.isValid("FORM-DATA", null)).isTrue();
        assertThat(validator.isValid("TEXT", null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for valid body types in mixed case")
    void shouldReturnTrueForValidBodyTypesInMixedCase() {
        assertThat(validator.isValid("Json", null)).isTrue();
        assertThat(validator.isValid("Form-Data", null)).isTrue();
        assertThat(validator.isValid("TeXt", null)).isTrue();
    }

}
