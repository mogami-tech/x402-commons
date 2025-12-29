package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.SupportedX402VersionValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("X402 version supported validator tests")
public class SupportedExistingX402VersionValidatorTest {

    SupportedX402VersionValidator validator;

    @BeforeEach
    void setUp() {
        validator = new SupportedX402VersionValidator();
    }

    @Test
    @DisplayName("Should return false for null version")
    void shouldReturnTrueForNullVersion() {
        assertThat(validator.isValid(null, null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for a not supported version")
    void shouldReturnFalseForNotSupportedVersion() {
        assertThat(validator.isValid(1, null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for known version")
    void shouldReturnTrueForKnownVersion() {
        assertThat(validator.isValid(2, null)).isTrue();
    }

}
