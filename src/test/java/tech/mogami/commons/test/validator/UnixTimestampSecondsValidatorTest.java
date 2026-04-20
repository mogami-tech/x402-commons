package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.UnixTimestampSecondsValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Unix timestamp in seconds validator tests")
class UnixTimestampSecondsValidatorTest {

    private final UnixTimestampSecondsValidator validator = new UnixTimestampSecondsValidator();

    @Test
    @DisplayName("null should be valid")
    void nullTimestamp() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    @DisplayName("blank and whitespace should be valid")
    void blankAndWhitespaceTimestamp() {
        assertTrue(validator.isValid("", null));
        assertTrue(validator.isValid("   ", null));
    }

    @Test
    @DisplayName("positive numbers should be valid")
    void positiveNumbersTimestamp() {
        assertTrue(validator.isValid("1", null));
        assertTrue(validator.isValid(Long.toString(Long.MAX_VALUE), null));
    }

    @Test
    @DisplayName("zero and negative numbers should not be valid")
    void zeroAndNegativeTimestamp() {
        assertFalse(validator.isValid("0", null));
        assertFalse(validator.isValid("-1", null));
    }

    @Test
    @DisplayName("non-numeric strings should not be valid")
    void nonNumericTimestamp() {
        assertFalse(validator.isValid("invalid", null));
    }

    @Test
    @DisplayName("number with surrounding spaces should not be valid")
    void numberWithSurroundingSpacesTimestamp() {
        // Long.parseLong does not accept leading/trailing spaces -> should be invalid
        assertFalse(validator.isValid(" 123 ", null));
    }

}
