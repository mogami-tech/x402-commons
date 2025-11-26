package tech.mogami.commons.api.facilitator.verify;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import tech.mogami.commons.api.facilitator.RequestCommonData;
import tech.mogami.commons.constant.network.Network;
import tech.mogami.commons.constant.network.Networks;
import tech.mogami.commons.payment.PaymentPayload;
import tech.mogami.commons.payment.PaymentRequirements;
import tech.mogami.commons.validator.X402Version;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Request to verify a payment.
 *
 * @param x402Version         x402 version
 * @param paymentPayload      payment payload
 * @param paymentRequirements payment requirements
 */
@Builder
@Jacksonized
@Schema(description = "Request to verify a payment")
@SuppressWarnings("unused")
public record VerifyRequest(

        @NotNull(message = "{validation.verifyRequest.x402Version.required}")
        @X402Version(message = "{validation.verifyRequest.x402Version.invalid}")
        @Schema(description = "x402 protocol version", example = "1")
        Integer x402Version,

        @Valid
        @NotNull(message = "{validation.verifyRequest.paymentPayload.required}")
        @Schema(description = "Payload describing the payment")
        PaymentPayload paymentPayload,

        @Valid
        @NotNull(message = "{validation.verifyRequest.paymentRequirements.required}")
        @Schema(description = "Payment requirements as provided by the server")
        PaymentRequirements paymentRequirements

) implements RequestCommonData {

    @Override
    @JsonIgnore
    public Optional<String> getNonce() {
        return Optional.ofNullable(paymentPayload)
                .flatMap(PaymentPayload::getNonce);
    }

    @Override
    @JsonIgnore
    public Optional<String> getFromAddress() {
        return Optional.ofNullable(paymentPayload)
                .flatMap(PaymentPayload::getFromAddress);
    }

    @Override
    @JsonIgnore
    public Optional<String> getToAddress() {
        return Optional.ofNullable(paymentPayload)
                .flatMap(PaymentPayload::getToAddress);
    }

    @Override
    @JsonIgnore
    public Optional<BigInteger> getAssetAmount() {
        return Optional.ofNullable(paymentPayload)
                .flatMap(PaymentPayload::getAmount);
    }

    @Override
    @JsonIgnore
    public Optional<String> getAssetContract() {
        return Optional.ofNullable(paymentRequirements)
                .stream()
                // TODO What if there are multiple asset contracts?
                .findFirst()
                .map(PaymentRequirements::asset);
    }

    @Override
    @JsonIgnore
    public Optional<Network> getNetwork() {
        return Optional.ofNullable(paymentPayload)
                .flatMap(PaymentPayload::getNetworkName)
                .flatMap(Networks::findByName);
    }

}
