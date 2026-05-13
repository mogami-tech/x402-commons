package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.ExistingX402VersionValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("X402 existing version validator tests")
class ExistingExistingX402VersionValidatorTest {

    ExistingX402VersionValidator validator = new ExistingX402VersionValidator();

    @Test
    @DisplayName("Should return true for null version")
    void nullVersion() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for unknown version")
    void nonExistingVersion() {
        assertThat(validator.isValid(9999, null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for known version")
    void existingVersion() {
        assertThat(validator.isValid(1, null)).isTrue();
        assertThat(validator.isValid(2, null)).isTrue();
    }

}
