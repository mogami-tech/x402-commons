package tech.mogami.commons.exception;

/**
 * Exception thrown when a x402 scheme is invalid.
 */
@SuppressWarnings("unused")
public final class InvalidX402SchemeException extends X402Exception {

    /** Serial version UID. */
    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param message the exception message
     */
    public InvalidX402SchemeException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message the exception message
     * @param cause   the root cause
     */
    public InvalidX402SchemeException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
