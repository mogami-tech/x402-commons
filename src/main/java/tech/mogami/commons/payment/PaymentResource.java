package tech.mogami.commons.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;

/**
 * ResourceInfo object describing the protected resource.
 *
 * @param url         URL of the protected resource
 * @param description Human-readable description of the resource
 * @param mimeType    MIME type of the expected response
 */
@Builder
@Jacksonized
@Schema(description = "ResourceInfo object describing the protected resource")
public record PaymentResource(

        @NotBlank
        @Schema(description = "URL of the protected resource", example = "https://api.example.com/premium-data")
        String url,

        @Schema(description = "Human-readable description of the resource", example = "Access to premium market data", nullable = true)
        @Nullable String description,

        @Schema(description = "MIME type of the expected response", example = "application/json", nullable = true)
        @Nullable String mimeType

) {
}
