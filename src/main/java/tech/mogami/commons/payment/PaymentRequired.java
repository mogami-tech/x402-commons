package tech.mogami.commons.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;
import tech.mogami.commons.constant.version.X402Version;
import tech.mogami.commons.constant.version.X402Versions;
import tech.mogami.commons.deserializer.ForceStringDeserializer;
import tech.mogami.commons.validator.ExistingX402Version;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;
import static tech.mogami.commons.constant.version.X402Versions.X402_SUPPORTED_VERSIONS;

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

        @JsonProperty(required = true)
        @NotNull(message = "{validation.paymentPayload.x402Version.required}")
        @ExistingX402Version(message = "{validation.paymentPayload.x402Version.invalid}")
        @Schema(description = "Version of the x402 payment protocol", example = "2", requiredMode = REQUIRED)
        Integer x402Version,

        @JsonDeserialize(using = ForceStringDeserializer.class)
        @Schema(description = "Human-readable error message explaining why payment is required", example = "Payment required to access this resource", nullable = true)
        @Nullable String error,

        @Valid
        @JsonProperty(required = true)
        @NotNull(message = "{validation.paymentRequired.resource.required}")
        @Schema(description = "Resource requiring payment", requiredMode = REQUIRED)
        PaymentResource resource,

        @JsonProperty(required = true)
        @NotNull(message = "{validation.paymentRequired.accepts.required}")
        @Schema(description = "List of acceptable payment methods (e.g., different schemes/networks/assets)", requiredMode = REQUIRED)
        List<PaymentRequirements> accepts,

        @Schema(description = "Protocol extensions data")
        @Nullable Map<String, Object> extensions

) {

    /**
     * Retrieves the X402 version as an Optional enum.
     *
     * @return An Optional containing the X402Version if found, otherwise an empty Optional.
     */
    @JsonIgnore
    public Optional<X402Version> getVersion() {
        return X402Versions.findByVersion(x402Version);
    }

    /**
     * Checks if the x402 version is supported.
     *
     * @return true if the version is supported, false otherwise.
     */
    @JsonIgnore
    public boolean isSupportedVersion() {
        return getVersion()
                .map(X402_SUPPORTED_VERSIONS::contains)
                .orElse(false);
    }

}
