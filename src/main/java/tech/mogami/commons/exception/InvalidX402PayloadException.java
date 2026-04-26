package tech.mogami.commons.exception;

/**
 * Exception thrown when a x402 payload is invalid.
 */
@SuppressWarnings("unused")
public final class InvalidX402PayloadException extends X402Exception {

    /** Serial version UID. */
    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param message the exception message
     */
    public InvalidX402PayloadException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message the exception message
     * @param cause   the root cause
     */
    public InvalidX402PayloadException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
