package tech.mogami.commons.api.payment.schemes;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Contract for scheme-specific payment payloads.
 * All scheme payload types must implement this interface.
 * Default implementations return {@link Optional#empty()} so that new schemes only override what they support.
 */
@SuppressWarnings("unused")
public interface SchemePayload {

    /**
     * Get the nonce from the payload.
     * Returns empty by default; override for schemes that carry a nonce.
     *
     * @return the nonce if available
     */
    default Optional<String> getNonce() {
        return Optional.empty();
    }

    /**
     * Get the from address from the payload.
     * Returns empty by default; override for schemes that carry a sender address.
     *
     * @return the from address if available
     */
    default Optional<String> getFromAddress() {
        return Optional.empty();
    }

    /**
     * Get the to address from the payload.
     * Returns empty by default; override for schemes that carry a recipient address.
     *
     * @return the to address if available
     */
    default Optional<String> getToAddress() {
        return Optional.empty();
    }

    /**
     * Get the amount from the payload.
     * Returns empty by default; override for schemes that carry an amount.
     *
     * @return the amount if available
     */
    default Optional<BigInteger> getAmount() {
        return Optional.empty();
    }

}
