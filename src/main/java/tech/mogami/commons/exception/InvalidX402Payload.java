package tech.mogami.commons.exception;

/**
 * Exception thrown when a x402 payload is invalid.
 */
@SuppressWarnings("unused")
public final class InvalidX402Payload extends X402Exception {

    /**
     * Constructor.
     *
     * @param message the exception message
     */
    public InvalidX402Payload(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message the exception message
     * @param cause   the root cause
     */
    public InvalidX402Payload(final String message, final Throwable cause) {
        super(message, cause);
    }

}
