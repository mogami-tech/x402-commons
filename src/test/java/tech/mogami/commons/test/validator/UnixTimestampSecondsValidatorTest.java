package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.UnixTimestampSecondsValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Unix timestamp in seconds validator tests")
class UnixTimestampSecondsValidatorTest {

    private final UnixTimestampSecondsValidator validator = new UnixTimestampSecondsValidator();

    @Test
    @DisplayName("null should be valid")
    void nullTimestamp() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    @DisplayName("blank and whitespace should be valid")
    void blankAndWhitespaceTimestamp() {
        assertThat(validator.isValid("", null)).isTrue();
        assertThat(validator.isValid("   ", null)).isTrue();
    }

    @Test
    @DisplayName("positive numbers should be valid")
    void positiveNumbersTimestamp() {
        assertThat(validator.isValid("1", null)).isTrue();
        assertThat(validator.isValid(Long.toString(Long.MAX_VALUE), null)).isTrue();
    }

    @Test
    @DisplayName("zero and negative numbers should not be valid")
    void zeroAndNegativeTimestamp() {
        assertThat(validator.isValid("0", null)).isFalse();
        assertThat(validator.isValid("-1", null)).isFalse();
    }

    @Test
    @DisplayName("non-numeric strings should not be valid")
    void nonNumericTimestamp() {
        assertThat(validator.isValid("invalid", null)).isFalse();
    }

    @Test
    @DisplayName("number with surrounding spaces should be valid (whitespace is trimmed)")
    void numberWithSurroundingSpacesTimestamp() {
        assertThat(validator.isValid(" 123 ", null)).isTrue();
    }

}
