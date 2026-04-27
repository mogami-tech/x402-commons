package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.NonceValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Nonce validator tests")
public class NonceValidatorTest {

    private final NonceValidator validator = new NonceValidator();

    @Test
    @DisplayName("Should return true for null nonce")
    void nullNonce() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for empty nonce")
    void emptyNonce() {
        assertThat(validator.isValid("", null)).isTrue();
    }

    @Test
    @DisplayName("Should return false for nonce without '0x' prefix")
    void invalidPrefixForNonce() {
        assertThat(validator.isValid("1x" + "a".repeat(64), null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for nonce that is too short")
    void nonceTooShort() {
        assertThat(validator.isValid("0x" + "a".repeat(32), null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for nonce that is too long")
    void nonceTooLong() {
        assertThat(validator.isValid("0x" + "a".repeat(65), null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for nonce with invalid hex character (g)")
    void invalidHexCharacterInNonce() {
        assertThat(validator.isValid("0x" + "g".repeat(64), null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for valid lowercase hex nonce")
    void validLowercaseNonce() {
        assertThat(validator.isValid("0x" + "a1b2c3d4".repeat(8), null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for valid uppercase hex nonce")
    void validUppercaseNonce() {
        assertThat(validator.isValid("0x" + "A1B2C3D4".repeat(8), null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for valid mixed-case hex nonce")
    void validMixedCaseNonce() {
        assertThat(validator.isValid("0xdeadbeefCAFEBABE0123456789abcdefDEADBEEFcafebabe0123456789ABCDEF", null)).isTrue();
    }

}
