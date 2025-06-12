package tech.mogami.commons.api.facilitator.verify;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import tech.mogami.commons.header.payment.PaymentPayload;
import tech.mogami.commons.header.payment.PaymentRequirements;

/**
 * Verify request is a request to the facilitator service.
 *
 * @param x402Version         x402 version
 * @param paymentPayload      paument payload
 * @param paymentRequirements payment requirements
 */
@Builder
@Jacksonized
@SuppressWarnings("unused")
public record VerifyRequest(
        Integer x402Version,

        @Valid
        @NotNull(message = "{validation.verifyRequest.paymentPayload.required}")
        PaymentPayload paymentPayload,

        @Valid
        @NotNull(message = "{validation.verifyRequest.paymentRequirements.required}")
        PaymentRequirements paymentRequirements) {

}
