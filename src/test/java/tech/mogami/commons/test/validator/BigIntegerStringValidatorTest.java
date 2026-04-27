package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.BigIntegerStringValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BigIntegerString validator tests")
class BigIntegerStringValidatorTest {

    private final BigIntegerStringValidator validator = new BigIntegerStringValidator();

    @Test
    @DisplayName("null should be valid")
    void nullValue() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    @DisplayName("blank and whitespace should be valid")
    void blankValue() {
        assertThat(validator.isValid("", null)).isTrue();
        assertThat(validator.isValid("   ", null)).isTrue();
    }

    @Test
    @DisplayName("zero should be valid")
    void zeroValue() {
        assertThat(validator.isValid("0", null)).isTrue();
    }

    @Test
    @DisplayName("positive integers should be valid")
    void positiveValues() {
        assertThat(validator.isValid("1", null)).isTrue();
        assertThat(validator.isValid("10000", null)).isTrue();
        assertThat(validator.isValid("999999999999999999999999999999", null)).isTrue();
    }

    @Test
    @DisplayName("negative integers should not be valid")
    void negativeValues() {
        assertThat(validator.isValid("-1", null)).isFalse();
        assertThat(validator.isValid("-10000", null)).isFalse();
    }

    @Test
    @DisplayName("non-numeric strings should not be valid")
    void nonNumericValues() {
        assertThat(validator.isValid("abc", null)).isFalse();
        assertThat(validator.isValid("1.5", null)).isFalse();
        assertThat(validator.isValid("0x1A", null)).isFalse();
    }

}
