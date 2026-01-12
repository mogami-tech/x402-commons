package tech.mogami.commons.exception;

/**
 * Base exception for X-402 related errors.
 */
public abstract class X402Exception extends RuntimeException {

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
