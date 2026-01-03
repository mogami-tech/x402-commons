package tech.mogami.commons.exception;

/**
 * Exception thrown when a x402 scheme is invalid.
 */
@SuppressWarnings("unused")
public final class InvalidX402Scheme extends X402Exception {

    /**
     * Constructor.
     *
     * @param message the exception message
     */
    public InvalidX402Scheme(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message the exception message
     * @param cause   the root cause
     */
    public InvalidX402Scheme(final String message, final Throwable cause) {
        super(message, cause);
    }

}
