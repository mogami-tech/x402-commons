package tech.mogami.commons.exception;

/**
 * Exception thrown when the x402 header is invalid.
 */
@SuppressWarnings("unused")
public final class InvalidX402HeaderException extends X402Exception {

    /**
     * Constructor.
     *
     * @param message the exception message
     */
    public InvalidX402HeaderException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message the exception message
     * @param cause   the root cause
     */
    public InvalidX402HeaderException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
