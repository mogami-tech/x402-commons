package tech.mogami.commons.api.facilitator.settle;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import tech.mogami.commons.payment.PaymentPayload;
import tech.mogami.commons.payment.PaymentRequirements;
import tech.mogami.commons.validator.X402Version;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Request to settle a payment.
 *
 * @param x402Version         x402 version
 * @param paymentPayload      payment payload
 * @param paymentRequirements payment requirements
 */
@Builder
@Jacksonized
@Schema(description = "Request to settle a payment")
@SuppressWarnings("unused")
public record SettleRequest(

        @NotNull(message = "{validation.settleRequest.x402Version.required}")
        @X402Version(message = "{validation.settleRequest.x402Version.invalid}")
        @Schema(description = "x402 protocol version", example = "1")
        Integer x402Version,

        @Valid
        @NotNull(message = "{validation.settleRequest.paymentPayload.required}")
        @Schema(description = "Payload describing the payment")
        PaymentPayload paymentPayload,

        @Valid
        @NotNull(message = "{validation.settleRequest.paymentRequirements.required}")
        @Schema(description = "Payment requirements as provided by the server")
        PaymentRequirements paymentRequirements

) {

    /**
     * Get the nonce from the payload.
     *
     * @return the nonce if present
     */
    @JsonIgnore
    public Optional<String> getNonce() {
        return Optional.ofNullable(paymentPayload)
                .flatMap(PaymentPayload::getNonce);
    }

    /**
     * Get the from address from the payload.
     *
     * @return the from address if present
     */
    @JsonIgnore
    public Optional<String> getFromAddress() {
        return Optional.ofNullable(paymentPayload)
                .flatMap(PaymentPayload::getFromAddress);
    }

    /**
     * Get the to address from the payload.
     *
     * @return the to address if present
     */
    @JsonIgnore
    public Optional<String> getToAddress() {
        return Optional.ofNullable(paymentPayload)
                .flatMap(PaymentPayload::getToAddress);
    }

    /**
     * Get the amount from the payload.
     *
     * @return the amount if present
     */
    @JsonIgnore
    public Optional<BigInteger> getAmount() {
        return Optional.ofNullable(paymentPayload)
                .flatMap(PaymentPayload::getAmount);
    }

}
