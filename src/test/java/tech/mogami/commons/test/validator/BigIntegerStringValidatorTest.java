package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.BigIntegerStringValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("BigIntegerString validator tests")
class BigIntegerStringValidatorTest {

    private final BigIntegerStringValidator validator = new BigIntegerStringValidator();

    @Test
    @DisplayName("null should be valid")
    void nullValue() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    @DisplayName("blank and whitespace should be valid")
    void blankValue() {
        assertTrue(validator.isValid("", null));
        assertTrue(validator.isValid("   ", null));
    }

    @Test
    @DisplayName("zero should be valid")
    void zeroValue() {
        assertTrue(validator.isValid("0", null));
    }

    @Test
    @DisplayName("positive integers should be valid")
    void positiveValues() {
        assertTrue(validator.isValid("1", null));
        assertTrue(validator.isValid("10000", null));
        assertTrue(validator.isValid("999999999999999999999999999999", null));
    }

    @Test
    @DisplayName("negative integers should not be valid")
    void negativeValues() {
        assertFalse(validator.isValid("-1", null));
        assertFalse(validator.isValid("-10000", null));
    }

    @Test
    @DisplayName("non-numeric strings should not be valid")
    void nonNumericValues() {
        assertFalse(validator.isValid("abc", null));
        assertFalse(validator.isValid("1.5", null));
        assertFalse(validator.isValid("0x1A", null));
    }

}
