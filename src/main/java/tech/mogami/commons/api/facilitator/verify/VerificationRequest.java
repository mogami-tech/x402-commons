package tech.mogami.commons.api.facilitator.verify;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import tech.mogami.commons.api.facilitator.PaymentContext;
import tech.mogami.commons.api.payment.PaymentPayload;
import tech.mogami.commons.api.payment.PaymentRequirements;
import tech.mogami.commons.validator.ExistingX402Version;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * Request to verify a payment.
 *
 * @param x402Version         version of the x402 payment protocol
 * @param paymentPayload      payment payload
 * @param paymentRequirements payment requirements
 */
@Builder
@Jacksonized
@Schema(description = "Request to verify a payment")
@SuppressWarnings("unused")
public record VerificationRequest(

        @JsonProperty(required = true)
        @NotNull(message = "{validation.verifyRequest.x402Version.required}")
        @ExistingX402Version(message = "{validation.verifyRequest.x402Version.invalid}")
        @Schema(description = "Version of the x402 payment protocol", example = "2", requiredMode = REQUIRED)
        Integer x402Version,

        @Valid
        @NotNull(message = "{validation.verifyRequest.paymentPayload.required}")
        @Schema(description = "Payload describing the payment")
        PaymentPayload paymentPayload,

        @Valid
        @NotNull(message = "{validation.verifyRequest.paymentRequirements.required}")
        @Schema(description = "Payment requirements as provided by the server")
        PaymentRequirements paymentRequirements

) implements PaymentContext {
}