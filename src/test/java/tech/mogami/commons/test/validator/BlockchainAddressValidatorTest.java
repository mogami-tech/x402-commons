package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.BlockchainAddressValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Blockchain address validator tests")
class BlockchainAddressValidatorTest {

    BlockchainAddressValidator validator = new BlockchainAddressValidator();

    @Test
    @DisplayName("Should return true for null address")
    void nullAddress() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for empty address")
    void emptyAddress() {
        assertThat(validator.isValid("", null)).isTrue();
    }

    @Test
    @DisplayName("Should return false for address without '0x' prefix")
    void invalidPrefixForAddress() {
        assertThat(validator.isValid("1x1234567890abcdef1234567890abcdef12345678", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for address with invalid length")
    void invalidLengthForAddress() {
        assertThat(validator.isValid("0x1234", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for address with invalid hex character (g)")
    void invalidHexCharacterInAddress() {
        assertThat(validator.isValid("0x1234567890abcdef1234567890abcdef1234567g", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for address that is too short")
    void addressTooShort() {
        assertThat(validator.isValid("0x1234567890abcdef", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for address that is too long")
    void addressTooLong() {
        assertThat(validator.isValid("0x1234567890abcdef1234567890abcdef123456789", null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for valid address")
    void validAddress() {
        assertThat(validator.isValid("0x1234567890abcdef1234567890abcdef12345678", null)).isTrue();
    }

}
