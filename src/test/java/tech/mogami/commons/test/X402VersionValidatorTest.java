package tech.mogami.commons.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.X402VersionValidator;

import static org.assertj.core.api.Assertions.assertThat;

public class X402VersionValidatorTest {

    private X402VersionValidator validator;

    @BeforeEach
    void setUp() {
        validator = new X402VersionValidator();
    }

    @Test
    void shouldReturnTrueForNullVersion() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    void shouldReturnFalseForUnknownVersion() {
        assertThat(validator.isValid(9999, null)).isFalse();
    }

    @Test
    void shouldReturnTrueForKnownVersion() {
        assertThat(validator.isValid(1, null)).isTrue();
    }

}
