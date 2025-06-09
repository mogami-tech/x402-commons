package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.NetworkValidator;

import static org.assertj.core.api.Assertions.assertThat;

public class NetworkValidatorTest {

    private NetworkValidator validator;

    @BeforeEach
    void setUp() {
        validator = new NetworkValidator();
    }

    @Test
    void shouldReturnTrueForNullNetwork() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    void shouldReturnTrueForEmptyNetwork() {
        assertThat(validator.isValid("", null)).isTrue();
    }

    @Test
    void shouldReturnFalseForUnknownNetwork() {
        assertThat(validator.isValid("INVALID_NETWORK", null)).isFalse();
    }

    @Test
    void shouldReturnTrueForKnownNetwork() {
        assertThat(validator.isValid("base-sepolia", null)).isTrue();
    }

}
