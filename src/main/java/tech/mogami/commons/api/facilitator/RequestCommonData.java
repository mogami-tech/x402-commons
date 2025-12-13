package tech.mogami.commons.api.facilitator;

import tech.mogami.commons.constant.network.Network;
import tech.mogami.commons.constant.version.X402Version;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Request common data interface for retrieving common payment data.
 */
public interface RequestCommonData {

    /**
     * Get the X402 version from the payload.
     *
     * @return the X402 version if present
     */
    Optional<X402Version> getX402Version();

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
     * Get the asset amount from the payload.
     *
     * @return the asset amount if present
     */
    Optional<BigInteger> getAssetAmount();

    /**
     * Get the asset contract from the payload.
     *
     * @return the asset contract if present
     */
    Optional<String> getAssetContract();

    /**
     * Get the network from the payload.
     *
     * @return the network if present
     */
    Optional<Network> getNetwork();

}
