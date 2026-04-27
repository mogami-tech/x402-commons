package tech.mogami.commons.exception;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

import java.util.Set;

/**
 * Exception thrown when a x402 header is invalid.
 */
@SuppressWarnings("unused")
public final class InvalidX402HeaderException extends X402Exception {

    /** Serial version UID. */
    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /** The set of constraint violations, may be empty when the error is not validation-related. */
    @Getter
    private final Set<? extends ConstraintViolation<?>> violations;

    /**
     * Constructor.
     *
     * @param message the exception message
     */
    public InvalidX402HeaderException(final String message) {
        super(message);
        this.violations = Set.of();
    }

    /**
     * Constructor.
     *
     * @param message    the exception message
     * @param cause      the root cause
     */
    public InvalidX402HeaderException(final String message, final Throwable cause) {
        super(message, cause);
        this.violations = Set.of();
    }

    /**
     * Constructor used when the header fails bean-validation.
     *
     * @param message    the exception message
     * @param violations the set of constraint violations
     */
    public InvalidX402HeaderException(final String message, final Set<? extends ConstraintViolation<?>> violations) {
        super(message);
        this.violations = violations;
    }

}
