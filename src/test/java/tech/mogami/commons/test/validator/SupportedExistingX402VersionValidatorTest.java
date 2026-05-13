package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.SupportedX402VersionValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("X402 supported version validator tests")
class SupportedExistingX402VersionValidatorTest {

    SupportedX402VersionValidator validator = new SupportedX402VersionValidator();

    @Test
    @DisplayName("Should return false for null version")
    void nullVersion() {
        assertThat(validator.isValid(null, null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for a not supported version")
    void unSupportedVersion() {
        assertThat(validator.isValid(1, null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for supported version")
    void supportedVersion() {
        assertThat(validator.isValid(2, null)).isTrue();
    }

}
