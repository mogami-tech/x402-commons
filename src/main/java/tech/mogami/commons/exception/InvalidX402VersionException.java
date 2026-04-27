package tech.mogami.commons.exception;

/**
 * Exception thrown when a x402 version is invalid.
 */
@SuppressWarnings("unused")
public final class InvalidX402VersionException extends X402Exception {

    /** Serial version UID. */
    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param message the exception message
     */
    public InvalidX402VersionException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message the exception message
     * @param cause   the root cause
     */
    public InvalidX402VersionException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
