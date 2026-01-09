package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.NetworkIdValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Network id validator tests")
public class NetworkIdValidatorTest {

    NetworkIdValidator validator;

    @BeforeEach
    void setUp() {
        validator = new NetworkIdValidator();
    }

    @Test
    @DisplayName("Should return true for null network")
    void shouldReturnTrueForNullNetwork() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for empty network")
    void shouldReturnTrueForEmptyNetwork() {
        assertThat(validator.isValid("", null)).isTrue();
    }

    @Test
    @DisplayName("Should return false for unknown network")
    void shouldReturnFalseForUnknownNetwork() {
        assertThat(validator.isValid("INVALID_NETWORK", null)).isFalse();
        assertThat(validator.isValid("EIP155", null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for known network")
    void shouldReturnTrueForKnownNetwork() {
        assertThat(validator.isValid("eip155:84532", null)).isTrue();
        assertThat(validator.isValid("EIP155:84532", null)).isTrue();
    }

}
