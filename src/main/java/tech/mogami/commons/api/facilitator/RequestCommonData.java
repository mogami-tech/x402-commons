package tech.mogami.commons.api.facilitator;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Request common data interface for retrieving common payment data.
 */
public interface RequestCommonData {

    /**
     * Get the nonce from the payload.
     *
     * @return the nonce if present
     */
    Optional<String> getNonce();

    /**
     * Get the from address from the payload.
     *
     * @return the from address if present
     */
    Optional<String> getFromAddress();

    /**
     * Get the to address from the payload.
     *
     * @return the to address if present
     */
    Optional<String> getToAddress();

    /**
     * Get the amount from the payload.
     *
     * @return the amount if present
     */
    Optional<BigInteger> getAmount();

    /**
     * Get the asset contract from the payload.
     *
     * @return the asset contract if present
     */
    Optional<String> getAssetContract();

}
