package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.SchemeValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Scheme validator tests")
public class SchemeValidatorTest {

    private SchemeValidator validator;

    @BeforeEach
    void setUp() {
        validator = new SchemeValidator();
    }

    @Test
    @DisplayName("Should return true for null scheme")
    void shouldReturnTrueForNullScheme() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for empty scheme")
    void shouldReturnTrueForEmptyScheme() {
        assertThat(validator.isValid("", null)).isTrue();
    }

    @Test
    @DisplayName("Should return false for unknown scheme")
    void shouldReturnFalseForUnknownScheme() {
        assertThat(validator.isValid("INVALID_SCHEME", null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for known scheme")
    void shouldReturnTrueForKnownScheme() {
        assertThat(validator.isValid("exact", null)).isTrue();
        assertThat(validator.isValid("exAct", null)).isTrue();
    }

}
