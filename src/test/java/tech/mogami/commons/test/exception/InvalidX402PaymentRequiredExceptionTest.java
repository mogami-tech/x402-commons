package tech.mogami.commons.test.exception;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.exception.InvalidX402PaymentRequiredException;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("InvalidX402PaymentRequiredException tests")
public class InvalidX402PaymentRequiredExceptionTest {

    @Test
    @DisplayName("Constructor with violations should use default message")
    void constructorWithViolations() {
        Set<ConstraintViolation<?>> violations = Set.of();

        InvalidX402PaymentRequiredException exception = new InvalidX402PaymentRequiredException(violations);

        assertThat(exception.getMessage()).isEqualTo("Invalid x402 payment requirements");
        assertThat(exception.getViolations()).isSameAs(violations);
        assertThat(exception.getCause()).isNull();
    }

    @Test
    @DisplayName("Constructor with message and violations should use provided message")
    void constructorWithMessageAndViolations() {
        Set<ConstraintViolation<?>> violations = Set.of();

        InvalidX402PaymentRequiredException exception =
                new InvalidX402PaymentRequiredException("Custom validation error", violations);

        assertThat(exception.getMessage()).isEqualTo("Custom validation error");
        assertThat(exception.getViolations()).isSameAs(violations);
        assertThat(exception.getCause()).isNull();
    }

    @Test
    @DisplayName("Exception should be a RuntimeException")
    void isRuntimeException() {
        InvalidX402PaymentRequiredException exception = new InvalidX402PaymentRequiredException(Set.of());

        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Violations set should be preserved as passed")
    void violationsPreserved() {
        Set<ConstraintViolation<?>> violations = Set.of();

        InvalidX402PaymentRequiredException exception = new InvalidX402PaymentRequiredException(violations);

        assertThat(exception.getViolations()).isSameAs(violations);
    }

}
