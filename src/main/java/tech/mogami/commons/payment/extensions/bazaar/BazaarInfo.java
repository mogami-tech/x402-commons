package tech.mogami.commons.payment.extensions.bazaar;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * Specification for a bazaar resource, including both input and optional output details.
 *
 * @param input  Input specification describing how to call the endpoint (required)
 * @param output Optional output specification describing the expected response format
 */
@Builder
@Jacksonized
@Schema(description = "Discovery metadata describing how to call the x402 resource")
@SuppressWarnings("unused")
public record BazaarInfo(

        @Valid
        @JsonProperty(required = true)
        @NotNull(message = "{validation.bazaar.info.input.required}")
        @Schema(description = "Input specification describing how to call the endpoint",
                requiredMode = REQUIRED
        )
        BazaarInput input,

        @Valid
        @Schema(description = "Optional output specification describing the expected response format", nullable = true)
        @Nullable BazaarOutput output

) {
}
