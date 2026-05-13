package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.PayToValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Pay-to validator tests")
class PayToValidatorTest {

    PayToValidator validator = new PayToValidator();

    // =========================================================================
    // Null / blank — always valid (presence enforced by @NotBlank)
    // =========================================================================

    @Test
    @DisplayName("Should return true for null value")
    void nullValue() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for empty string")
    void emptyString() {
        assertThat(validator.isValid("", null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for blank string")
    void blankString() {
        assertThat(validator.isValid("   ", null)).isTrue();
    }

    // =========================================================================
    // EVM addresses
    // =========================================================================

    @Test
    @DisplayName("Should return true for a valid EVM address")
    void validEvmAddress() {
        assertThat(validator.isValid("0x209693Bc6afc0C5328bA36FaF03C514EF312287C", null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for a lowercase EVM address")
    void lowercaseEvmAddress() {
        assertThat(validator.isValid("0x209693bc6afc0c5328ba36faf03c514ef312287c", null)).isTrue();
    }

    @Test
    @DisplayName("Should return false for 0x prefix with too few hex characters")
    void evmAddressTooShort() {
        assertThat(validator.isValid("0x1234567890abcdef", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for 0x prefix with too many hex characters")
    void evmAddressTooLong() {
        assertThat(validator.isValid("0x1234567890abcdef1234567890abcdef123456789", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for 0x prefix with invalid hex character")
    void evmAddressInvalidHexChar() {
        assertThat(validator.isValid("0x1234567890abcdef1234567890abcdef1234567g", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for 0x prefix with a space inside")
    void evmAddressWithSpace() {
        assertThat(validator.isValid("0x1234567890abcdef1234567890 bcdef12345678", null)).isFalse();
    }

    // =========================================================================
    // Solana / non-EVM wallet addresses
    // =========================================================================

    @Test
    @DisplayName("Should return true for a valid Solana base58 address")
    void validSolanaAddress() {
        assertThat(validator.isValid("CKPKJWNdJEqa81x7CkZ14BVPiY6y16Sxs7owznqtWYp5", null)).isTrue();
    }

    // =========================================================================
    // Role constants
    // =========================================================================

    @Test
    @DisplayName("Should return true for 'merchant' role constant")
    void merchantRoleConstant() {
        assertThat(validator.isValid("merchant", null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for role constant with underscore")
    void roleConstantWithUnderscore() {
        assertThat(validator.isValid("api_merchant", null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for role constant with hyphen")
    void roleConstantWithHyphen() {
        assertThat(validator.isValid("api-merchant", null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for alphanumeric role constant")
    void alphanumericRoleConstant() {
        assertThat(validator.isValid("merchant42", null)).isTrue();
    }

    // =========================================================================
    // Invalid identifiers
    // =========================================================================

    @Test
    @DisplayName("Should return false for identifier containing a space")
    void identifierWithSpace() {
        assertThat(validator.isValid("my merchant", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for identifier containing a special character")
    void identifierWithSpecialChar() {
        assertThat(validator.isValid("merchant@role", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for identifier containing a dot")
    void identifierWithDot() {
        assertThat(validator.isValid("merchant.role", null)).isFalse();
    }

}
