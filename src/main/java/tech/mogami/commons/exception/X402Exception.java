package tech.mogami.commons.exception;

/**
 * Base exception for x402 related errors.
 */
public abstract class X402Exception extends RuntimeException {

    /** Serial version UID. */
    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param message the exception message
     */
    protected X402Exception(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message the exception message
     * @param cause   the root cause
     */
    protected X402Exception(final String message, final Throwable cause) {
        super(message, cause);
    }

}
