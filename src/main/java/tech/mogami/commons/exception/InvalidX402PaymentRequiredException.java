package tech.mogami.commons.exception;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

import java.util.Set;

/**
 * Exception thrown when a x402 payment is invalid.
 */
public final class InvalidX402PaymentRequiredException extends X402Exception {

    /** Serial version UID. */
    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /** The set of constraint violations. */
    @Getter
    private final Set<? extends ConstraintViolation<?>> violations;

    /**
     * Constructor.
     *
     * @param newViolations the set of constraint violations
     */
    public InvalidX402PaymentRequiredException(final Set<? extends ConstraintViolation<?>> newViolations) {
        super("Invalid x402 payment requirements");
        this.violations = newViolations;
    }

}
