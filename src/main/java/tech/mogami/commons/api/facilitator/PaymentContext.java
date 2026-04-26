package tech.mogami.commons.api.facilitator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import tech.mogami.commons.api.payment.PaymentPayload;
import tech.mogami.commons.api.payment.PaymentRequirements;
import tech.mogami.commons.constant.network.Network;
import tech.mogami.commons.constant.network.Networks;
import tech.mogami.commons.constant.x402.X402Version;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Payment context marker interface.
 */
public interface PaymentContext {

    /**
     * Gets the payment payload.
     *
     * @return payment payload
     */
    PaymentPayload paymentPayload();

    /**
     * Gets the payment requirements.
     *
     * @return payment requirements
     */
    PaymentRequirements paymentRequirements();

    /**
     * Get the X402 version from the payload.
     *
     * @return the X402 version if present
     */
    @JsonIgnore
    default Optional<X402Version> getVersion() {
        return Optional.ofNullable(paymentPayload())
                .flatMap(PaymentPayload::getX402Version);
    }

    /**
     * Get the payment ID (nonce usually) from the payload.
     *
     * @return the payment ID if present
     */
    @JsonIgnore
    default Optional<String> getPaymentId() {
        return Optional.ofNullable(paymentPayload())
                .flatMap(PaymentPayload::getNonce);
    }

    /**
     * Get the from address from the payload.
     *
     * @return the from address if present
     */
    @JsonIgnore
    default Optional<String> getFrom() {
        return Optional.ofNullable(paymentPayload())
                .flatMap(PaymentPayload::getFromAddress);
    }

    /**
     * Get the to address from the payload.
     *
     * @return the to address if present
     */
    @JsonIgnore
    default Optional<String> getTo() {
        return Optional.ofNullable(paymentPayload())
                .flatMap(PaymentPayload::getToAddress);
    }

    /**
     * Get the amount from the payload.
     *
     * @return the amount if present
     */
    @JsonIgnore
    default Optional<BigInteger> getAssetAmount() {
        return Optional.ofNullable(paymentPayload())
                .flatMap(PaymentPayload::getAmount);
    }

    /**
     * Get the asset contract from the payment requirements.
     *
     * @return the asset contract if present
     */
    @JsonIgnore
    default Optional<String> getAssetContract() {
        return Optional.ofNullable(paymentRequirements())
                .map(PaymentRequirements::asset)
                .map(StringUtils::trimToNull);
    }

    /**
     * Get the network from the payment requirements.
     *
     * @return the network if present
     */
    @JsonIgnore
    default Optional<Network> getNetwork() {
        return Optional.ofNullable(paymentRequirements())
                .map(PaymentRequirements::network)
                .flatMap(Networks::findByNetworkId);
    }

}
