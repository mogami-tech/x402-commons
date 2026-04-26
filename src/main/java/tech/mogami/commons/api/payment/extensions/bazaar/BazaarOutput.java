package tech.mogami.commons.api.payment.extensions.bazaar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * HTTP output specification for a bazaar resource.
 *
 * @param type    Response content type (e.g., JSON, text)
 * @param format  Additional format information (optional)
 * @param example Example response payload
 */
@Builder
@Jacksonized
@Schema(description = "HTTP output specification for a bazaar resource")
@SuppressWarnings("unused")
public record BazaarOutput(

        @JsonProperty(required = true)
        @NotNull(message = "{validation.bazaar.output.type.required}")
        @Schema(description = "Response content type (e.g., json, text)", example = "json", requiredMode = REQUIRED)
        String type,

        @Schema(description = "Additional format information (optional)", example = "application/json", nullable = true)
        @Nullable String format,

        @Schema(description = "Example response payload", nullable = true)
        @Nullable JsonNode example

) {
}
