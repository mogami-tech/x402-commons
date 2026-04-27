package tech.mogami.commons.api.payment.extensions.bazaar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;
import tech.mogami.commons.constant.util.BodyType;
import tech.mogami.commons.constant.util.HttpMethod;

import java.util.Map;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * HTTP input specification for a bazaar resource (type = "http").
 *
 * @param method      HTTP method (GET, POST, etc.)
 * @param bodyType    Body type for body methods (POST, PUT, PATCH): one of 'json', 'form-data', 'text'
 * @param body        Example request body
 * @param queryParams Example query parameters
 * @param headers     Example HTTP headers
 * @param pathParams  Concrete path parameter values for this specific request (e.g. {@code {"userId": "123"}})
 */
@Builder
@Jacksonized
@Schema(description = "HTTP input specification for a bazaar resource (type = \"http\")")
@SuppressWarnings("unused")
public record BazaarHttpInput(

        @JsonProperty(required = true)
        @NotNull(message = "{validation.bazaar.input.method.required}")
        @Schema(description = "HTTP method", example = "GET", requiredMode = REQUIRED)
        HttpMethod method,

        @Schema(description = "Body type for body methods: one of 'json', 'form-data', 'text'", example = "json", nullable = true)
        @Nullable BodyType bodyType,

        @Schema(description = "Example request body", nullable = true)
        @Nullable JsonNode body,

        @Schema(description = "Example query parameters", nullable = true)
        @Nullable Map<String, Object> queryParams,

        @Schema(description = "Example HTTP headers", nullable = true)
        @Nullable Map<String, String> headers,

        @Schema(description = "Concrete path parameter values for this specific request", nullable = true)
        @Nullable Map<String, String> pathParams

) implements BazaarInput {
}
