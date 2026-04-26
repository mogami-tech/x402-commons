package tech.mogami.commons.api.payment.extensions.bazaar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
@Jacksonized
@Schema(description = "x402 bazaar extension enabling resource discovery and cataloging")
@SuppressWarnings("unused")
public record BazaarExtension(

        @Valid
        @JsonProperty(required = true)
        @NotNull(message = "{validation.bazaar.info.required}")
        @Schema(description = "Discovery information describing how to call the endpoint", requiredMode = REQUIRED)
        BazaarInfo info,

        @JsonProperty(required = true)
        @NotNull(message = "{validation.bazaar.schema.required}")
        @Schema(description = "JSON Schema (Draft 2020-12) validating the info object", requiredMode = REQUIRED)
        JsonNode schema

) {
}