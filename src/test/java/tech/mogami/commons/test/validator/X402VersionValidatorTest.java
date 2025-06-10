package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.X402VersionValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("X402 version validator tests")
public class X402VersionValidatorTest {

    private X402VersionValidator validator;

    @BeforeEach
    void setUp() {
        validator = new X402VersionValidator();
    }

    @Test
    @DisplayName("Should return true for null version")
    void shouldReturnTrueForNullVersion() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for invalid version")
    void shouldReturnFalseForUnknownVersion() {
        assertThat(validator.isValid(9999, null)).isFalse();
    }

    @Test
    @DisplayName("Should return true for known version")
    void shouldReturnTrueForKnownVersion() {
        assertThat(validator.isValid(1, null)).isTrue();
    }

}
