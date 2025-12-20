package tech.mogami.commons.api.facilitator.settle;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import tech.mogami.commons.payment.PaymentPayload;
import tech.mogami.commons.payment.PaymentRequirements;

/**
 * Request to settle a payment.
 *
 * @param paymentPayload      payment payload
 * @param paymentRequirements payment requirements
 */
@Builder
@Jacksonized
@Schema(description = "Request to settle a payment")
@SuppressWarnings("unused")
public record SettleRequest(

        @Valid
        @NotNull(message = "{validation.settleRequest.paymentPayload.required}")
        @Schema(description = "Payload describing the payment")
        PaymentPayload paymentPayload,

        @Valid
        @NotNull(message = "{validation.settleRequest.paymentRequirements.required}")
        @Schema(description = "Payment requirements as provided by the server")
        PaymentRequirements paymentRequirements

) {
}
