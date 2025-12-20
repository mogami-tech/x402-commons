package tech.mogami.commons.payment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;
import tech.mogami.commons.deserializer.ForceStringDeserializer;
import tech.mogami.commons.validator.X402Version;

import java.util.List;
import java.util.Map;

/**
 * Reply when an access request is made to a x402 protected URL.
 *
 * @param x402Version version of the x402 payment protocol
 * @param error       Message from the resource server to the client to communicate errors in processing payment
 * @param resource    Resource requiring payment
 * @param accepts     List of payment requirements that the resource server accepts.
 *                    A resource server may accept on multiple chains or in multiple currencies.
 * @param extensions  Protocol extensions data
 */
@Builder
@Jacksonized
@Schema(description = "Payment requirement returned to the client when accessing a protected resource")
@SuppressWarnings("unused")
public record PaymentRequired(

        @NotNull(message = "{validation.paymentPayload.x402Version.required}")
        @X402Version(message = "{validation.paymentPayload.x402Version.invalid}")
        @Schema(description = "Version of the x402 payment protocol", example = "2")
        Integer x402Version,

        @JsonDeserialize(using = ForceStringDeserializer.class)
        @Schema(description = "Human-readable error message explaining why payment is required", example = "Payment required to access this resource", nullable = true)
        @Nullable String error,

        @Valid
        @NotNull(message = "{validation.paymentRequired.resource.required}")
        @Schema(description = "Resource requiring payment")
        PaymentResource resource,

        @NotNull(message = "{validation.paymentRequired.accepts.required}")
        @Schema(description = "List of acceptable payment methods (e.g., different schemes/networks/assets)")
        List<PaymentRequirements> accepts,

        @Schema(description = "Protocol extensions data", nullable = true)
        @Nullable Map<String, Object> extensions

) {
}
