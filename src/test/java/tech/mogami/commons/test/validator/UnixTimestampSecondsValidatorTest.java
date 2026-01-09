package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.UnixTimestampSecondsValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("UnixTimestampSecondsValidator tests")
class UnixTimestampSecondsValidatorTest {

    private final UnixTimestampSecondsValidator validator = new UnixTimestampSecondsValidator();

    @Test
    @DisplayName("null is valid")
    void nullIsValid() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    @DisplayName("blank and whitespace are valid")
    void blankAndWhitespaceAreValid() {
        assertTrue(validator.isValid("", null));
        assertTrue(validator.isValid("   ", null));
    }

    @Test
    @DisplayName("positive numbers are valid")
    void positiveNumbersAreValid() {
        assertTrue(validator.isValid("1", null));
        assertTrue(validator.isValid(Long.toString(Long.MAX_VALUE), null));
    }

    @Test
    @DisplayName("zero and negative numbers are invalid")
    void zeroAndNegativeAreInvalid() {
        assertFalse(validator.isValid("0", null));
        assertFalse(validator.isValid("-1", null));
    }

    @Test
    @DisplayName("non-numeric strings are invalid")
    void nonNumericIsInvalid() {
        assertFalse(validator.isValid("invalid", null));
    }

    @Test
    @DisplayName("number with surrounding spaces is invalid")
    void numberWithSurroundingSpacesIsInvalid() {
        // Long.parseLong does not accept leading/trailing spaces -> should be invalid
        assertFalse(validator.isValid(" 123 ", null));
    }

}

