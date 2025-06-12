package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.NetworkValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Network validator tests")
public class NetworkValidatorTest {

    private NetworkValidator validator;

    @BeforeEach
    void setUp() {
        validator = new NetworkValidator();
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
    }

    @Test
    @DisplayName("Should return true for known network")
    void shouldReturnTrueForKnownNetwork() {
        assertThat(validator.isValid("basE-Sepolia", null)).isTrue();
        assertThat(validator.isValid("base-sepolia", null)).isTrue();
    }

}
