package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.SchemeValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Scheme validator tests")
class SchemeValidatorTest {

    SchemeValidator validator = new SchemeValidator();

    @Test
    @DisplayName("Should return true for null scheme")
    void nullScheme() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for empty scheme")
    void emptyScheme() {
        assertThat(validator.isValid("", null)).isTrue();
    }

    @Test
    @DisplayName("Should return false for unknown scheme")
    void unknownScheme() {
        assertThat(validator.isValid("INVALID_SCHEME", null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for known scheme")
    void validScheme() {
        assertThat(validator.isValid("exact", null)).isTrue();
        assertThat(validator.isValid("exAct", null)).isTrue();
    }

}
