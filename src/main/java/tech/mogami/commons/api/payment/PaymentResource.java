package tech.mogami.commons.api.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

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
@SuppressWarnings("unused")
public record PaymentResource(

        @JsonProperty(required = true)
        @NotBlank(message = "{validation.paymentPayload.resource.url.required}")
        @Schema(description = "URL of the protected resource", example = "https://api.example.com/premium-data", requiredMode = REQUIRED)
        String url,

        @Schema(description = "Human-readable description of the resource", example = "Access to premium market data", nullable = true)
        @Nullable String description,

        @Schema(description = "MIME type of the expected response", example = "application/json", nullable = true)
        @Nullable String mimeType

) {
}
