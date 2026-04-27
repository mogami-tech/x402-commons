package tech.mogami.commons.api.payment.extensions.bazaar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * x402 bazaar extension enabling resource discovery and cataloging.
 *
 * @param info          Discovery information describing how to call the endpoint
 * @param schema        JSON Schema (Draft 2020-12) validating the info object
 * @param routeTemplate Canonical route template for parameterized paths (e.g. {@code /users/:userId}).
 *                      Absent for static routes; facilitators treat an absent value as "use the concrete URL path".
 *                      <p>
 *                      Format rules (facilitators must validate all before use):
 *                      <ul>
 *                        <li>Must start with {@code /}</li>
 *                        <li>Must match {@code ^/[a-zA-Z0-9_/:.\-~%]+$}</li>
 *                        <li>Must not contain {@code ..} (path traversal)</li>
 *                        <li>Must not contain {@code ://} (URL injection)</li>
 *                        <li>Percent-encoded sequences (e.g. {@code %2e%2e}) must be decoded before applying the above checks</li>
 *                      </ul>
 */
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
        JsonNode schema,

        @Nullable
        @Pattern(regexp = "^/(?!.*\\.\\.)(?!.*://)[a-zA-Z0-9_/:.\\-~%]+$", message = "{validation.bazaar.routeTemplate.invalid}")
        @Schema(
                description = "Canonical route template for parameterized paths (e.g. /users/:userId). "
                        + "Absent for static routes. Facilitators must also verify absence of '..' and '://' after percent-decoding.",
                example = "/users/:userId",
                nullable = true
        )
        String routeTemplate

) {
}