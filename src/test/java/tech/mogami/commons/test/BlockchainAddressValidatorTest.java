package tech.mogami.commons.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.BlockchainAddressValidator;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockchainAddressValidatorTest {

    private BlockchainAddressValidator validator;

    @BeforeEach
    void setUp() {
        validator = new BlockchainAddressValidator();
    }

    @Test
    void shouldReturnTrueForNullAddress() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    void shouldReturnTrueForEmptyAddress() {
        assertThat(validator.isValid("", null)).isTrue();
    }

    @Test
    void shouldReturnFalseForInvalidPrefix() {
        assertThat(validator.isValid("1x1234567890abcdef1234567890abcdef12345678", null)).isFalse();
    }

    @Test
    void shouldReturnFalseForInvalidLength() {
        assertThat(validator.isValid("0x1234", null)).isFalse();
    }

    @Test
    void shouldReturnFalseForNonHexCharacters() {
        assertThat(validator.isValid("0x1234567890abcdef1234567890abcdef1234567g", null)).isFalse();
    }

    @Test
    void shouldReturnTrueForValidAddress() {
        String valid = "0x1234567890abcdef1234567890abcdef12345678";
        assertThat(validator.isValid(valid, null)).isTrue();
    }

}
