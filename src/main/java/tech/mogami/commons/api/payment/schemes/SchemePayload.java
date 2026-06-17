package tech.mogami.commons.api.payment.schemes;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Marker interface for scheme-specific payment payloads.
 * All scheme payload types must implement this interface.
 */
@SuppressWarnings({"unused", "checkstyle:InterfaceIsType"})
public interface SchemePayload {

    /**
     * Extract payment nonce when available for this scheme.
     *
     * @return payment nonce
     */
    default Optional<String> getNonce() {
        return Optional.empty();
    }

    /**
     * Extract payer address when available for this scheme.
     *
     * @return payer address
     */
    default Optional<String> getFromAddress() {
        return Optional.empty();
    }

    /**
     * Extract payee address when available for this scheme.
     *
     * @return payee address
     */
    default Optional<String> getToAddress() {
        return Optional.empty();
    }

    /**
     * Extract payment amount when available for this scheme.
     *
     * @return payment amount
     */
    default Optional<BigInteger> getAmount() {
        return Optional.empty();
    }

}
