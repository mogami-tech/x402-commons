package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.SchemeValidator;

import static org.assertj.core.api.Assertions.assertThat;

public class SchemeValidatorTest {

    private SchemeValidator validator;

    @BeforeEach
    void setUp() {
        validator = new SchemeValidator();
    }

    @Test
    void shouldReturnTrueForNullScheme() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    void shouldReturnTrueForEmptyScheme() {
        assertThat(validator.isValid("", null)).isTrue();
    }

    @Test
    void shouldReturnFalseForUnknownScheme() {
        assertThat(validator.isValid("INVALID_SCHEME", null)).isFalse();
    }

    @Test
    void shouldReturnTrueForKnownScheme() {
        assertThat(validator.isValid("exact", null)).isTrue();
    }

}
