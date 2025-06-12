package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.BlockchainAddressValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Blockchain address validator tests")
public class BlockchainAddressValidatorTest {

    private BlockchainAddressValidator validator;

    @BeforeEach
    void setUp() {
        validator = new BlockchainAddressValidator();
    }

    @Test
    @DisplayName("Should return true for null address")
    void shouldReturnTrueForNullAddress() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for empty address")
    void shouldReturnTrueForEmptyAddress() {
        assertThat(validator.isValid("", null)).isTrue();
    }

    @Test
    @DisplayName("Should return false for address without '0x' prefix")
    void shouldReturnFalseForInvalidPrefix() {
        assertThat(validator.isValid("1x1234567890abcdef1234567890abcdef12345678", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for address with invalid length")
    void shouldReturnFalseForInvalidLength() {
        assertThat(validator.isValid("0x1234", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for address with too many characters")
    void shouldReturnFalseForNonHexCharacters() {
        assertThat(validator.isValid("0x1234567890abcdef1234567890abcdef1234567g", null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for valid address")
    void shouldReturnTrueForValidAddress() {
        assertThat(validator.isValid("0x1234567890abcdef1234567890abcdef12345678", null)).isTrue();
    }

}
