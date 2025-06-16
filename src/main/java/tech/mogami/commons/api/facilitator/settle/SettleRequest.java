package tech.mogami.commons.api.facilitator.settle;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import tech.mogami.commons.header.payment.PaymentPayload;
import tech.mogami.commons.header.payment.PaymentRequirements;

/**
 * Request to settle a payment.
 *
 * @param x402Version         x402 version
 * @param paymentPayload      paument payload
 * @param paymentRequirements payment requirements
 */
@Builder
@Jacksonized
@Schema(description = "Request to settle a payment")
@SuppressWarnings("unused")
public record SettleRequest(

        @Schema(description = "x402 protocol version", example = "1")
        int x402Version,

        @Schema(description = "Payload describing the payment")
        PaymentPayload paymentPayload,

        @Schema(description = "Payment requirements as provided by the server")
        PaymentRequirements paymentRequirements) {
}
