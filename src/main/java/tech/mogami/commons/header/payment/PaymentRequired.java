package tech.mogami.commons.header.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;
import tech.mogami.commons.validator.X402Version;

import java.util.List;

/**
 * Reply when an access request is made to a x402 protected URL.
 *
 * @param x402Version Version of the x402 payment protocol
 * @param accepts     List of payment requirements that the resource server accepts.
 *                    A resource server may accept on multiple chains or in multiple currencies.
 * @param error       Message from the resource server to the client to communicate errors in processing payment
 */
@Builder
@Jacksonized
@Schema(description = "Payment requirement returned to the client when accessing a protected resource")
@SuppressWarnings("unused")
public record PaymentRequired(

        @NotBlank(message = "{validation.paymentRequired.x402Version.required}")
        @X402Version(message = "{validation.paymentRequired.x402Version.invalid}")
        @Schema(description = "x402 protocol version used for the payment requirement", example = "1")
        Integer x402Version,

        @NotNull(message = "{validation.paymentRequired.accepts.required}")
        @Schema(description = "List of acceptable payment methods (e.g., different schemes/networks/assets)")
        List<PaymentRequirements> accepts,

        @Schema(description = "Optional error message indicating why payment is required")
        @Nullable String error) {
}
