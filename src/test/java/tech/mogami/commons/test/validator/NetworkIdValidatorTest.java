package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.NetworkIdValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Network id validator tests")
public class NetworkIdValidatorTest {

    NetworkIdValidator validator = new NetworkIdValidator();

    @Test
    @DisplayName("Should return true for null network id")
    void nullNetworkId() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for empty network id")
    void emptyNetworkId() {
        assertThat(validator.isValid("", null)).isTrue();
    }

    @Test
    @DisplayName("Should return false for unknown network id")
    void unknownNetworkId() {
        assertThat(validator.isValid("INVALID_NETWORK", null)).isFalse();
        assertThat(validator.isValid("EIP155", null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for known network id")
    void validNetworkId() {
        assertThat(validator.isValid("eip155:84532", null)).isTrue();
        assertThat(validator.isValid("EIP155:84532", null)).isTrue();
    }

}
