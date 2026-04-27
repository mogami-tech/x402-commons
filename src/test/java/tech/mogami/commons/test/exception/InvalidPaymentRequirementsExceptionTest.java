package tech.mogami.commons.test.exception;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.exception.InvalidPaymentRequirementsException;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("InvalidPaymentRequirementsException tests")
public class InvalidPaymentRequirementsExceptionTest {

    @Test
    @DisplayName("Constructor with violations should use default message")
    void constructorWithViolations() {
        Set<ConstraintViolation<?>> violations = Set.of();

        InvalidPaymentRequirementsException exception = new InvalidPaymentRequirementsException(violations);

        assertThat(exception.getMessage()).isEqualTo("Invalid x402 payment requirements");
        assertThat(exception.getViolations()).isSameAs(violations);
        assertThat(exception.getCause()).isNull();
    }

    @Test
    @DisplayName("Constructor with message and violations should use provided message")
    void constructorWithMessageAndViolations() {
        Set<ConstraintViolation<?>> violations = Set.of();

        InvalidPaymentRequirementsException exception = new InvalidPaymentRequirementsException("Custom validation error", violations);

        assertThat(exception.getMessage()).isEqualTo("Custom validation error");
        assertThat(exception.getViolations()).isSameAs(violations);
        assertThat(exception.getCause()).isNull();
    }

    @Test
    @DisplayName("Exception should be a RuntimeException")
    void isRuntimeException() {
        InvalidPaymentRequirementsException exception = new InvalidPaymentRequirementsException(Set.of());

        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Violations set should be preserved as passed")
    void violationsPreserved() {
        Set<ConstraintViolation<?>> violations = Set.of();

        InvalidPaymentRequirementsException exception = new InvalidPaymentRequirementsException(violations);

        assertThat(exception.getViolations()).isSameAs(violations);
    }

}
