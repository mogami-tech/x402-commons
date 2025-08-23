package tech.mogami.commons.api.facilitator.settle;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import tech.mogami.commons.header.payment.PaymentPayload;
import tech.mogami.commons.header.payment.PaymentRequirements;
import tech.mogami.commons.validator.X402Version;

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
        PaymentRequirements paymentRequirements) {
}
