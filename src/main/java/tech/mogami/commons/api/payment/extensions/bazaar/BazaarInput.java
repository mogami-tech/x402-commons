package tech.mogami.commons.api.payment.extensions.bazaar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;
import tech.mogami.commons.constant.util.BodyType;
import tech.mogami.commons.constant.util.HttpMethod;
import tech.mogami.commons.validator.BodyTypeString;

import java.util.Map;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * HTTP input specification for a bazaar resource.
 *
 * @param type        Transport type (always 'http')
 * @param method      Http method
 * @param bodyType    One of 'json', 'form-data', 'text'
 * @param body        Example body payload
 * @param queryParams Example query parameters
 * @param headers     Example HTTP headers
 */
@Builder
@Jacksonized
@Schema(description = "HTTP input specification for a bazaar resource")
@SuppressWarnings("unused")
public record BazaarInput(

        @JsonProperty(required = true)
        @NotNull(message = "{validation.bazaar.input.type.required}")
        @Pattern(regexp = "http", message = "{validation.bazaar.input.type.invalid}")
        @Schema(description = "Transport type (always 'http')", example = "http", requiredMode = REQUIRED)
        String type,

        @JsonProperty(required = true)
        @NotNull(message = "{validation.bazaar.input.method.required}")
        @Schema(description = "Http method", example = "GET", requiredMode = REQUIRED)
        HttpMethod method,

        @BodyTypeString(message = "{validation.bazaar.input.bodyType.invalid}")
        @Schema(description = "One of 'json', 'form-data', 'text'", example = "json", nullable = true)
        @Nullable BodyType bodyType,

        @Schema(description = "Example body payload", nullable = true)
        @Nullable JsonNode body,

        @Schema(description = "Example query parameters", nullable = true)
        @Nullable Map<String, Object> queryParams,

        @Schema(description = "Example HTTP headers", nullable = true)
        @Nullable Map<String, String> headers

) {
}
