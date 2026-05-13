package tech.mogami.commons.api.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;
import tech.mogami.commons.api.payment.extensions.Extensions;
import tech.mogami.commons.api.payment.schemes.Scheme;
import tech.mogami.commons.api.payment.schemes.SchemePayload;
import tech.mogami.commons.api.payment.schemes.Schemes;
import tech.mogami.commons.api.payment.schemes.exact.ExactSchemePayload;
import tech.mogami.commons.constant.x402.X402Version;
import tech.mogami.commons.constant.x402.X402Versions;
import tech.mogami.commons.exception.InvalidX402SchemeException;
import tech.mogami.commons.exception.InvalidX402VersionException;
import tech.mogami.commons.util.JsonUtil;
import tech.mogami.commons.validator.ExistingX402Version;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Function;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * Payment payload.
 *
 * @param x402Version version of the x402 payment protocol
 * @param resource    Resource requiring payment
 * @param accepted    PaymentRequirements object indicating the payment method chosen
 * @param payload     payload is scheme dependent
 * @param extensions  Protocol extensions data
 */
@Builder(toBuilder = true)
@Jacksonized
@Schema(description = "Payment payload sent by the client")
@SuppressWarnings("unused")
public record PaymentPayload(

        @JsonProperty(required = true)
        @NotNull(message = "{validation.paymentPayload.x402Version.required}")
        @ExistingX402Version(message = "{validation.paymentPayload.x402Version.invalid}")
        @Schema(description = "Version of the x402 payment protocol", example = "2", requiredMode = REQUIRED)
        Integer x402Version,

        @Nullable
        @Valid
        @JsonProperty("resource")
        @Schema(description = "Resource requiring payment", nullable = true)
        PaymentResource resource,

        @JsonProperty(required = true)
        @Valid
        @NotNull(message = "{validation.paymentPayload.accepted.required}")
        @Schema(description = "PaymentRequirements object indicating the payment method chosen", requiredMode = REQUIRED)
        PaymentRequirements accepted,

        @JsonProperty(required = true)
        @Valid
        @NotNull(message = "{validation.paymentPayload.payload.required}")
        @Schema(description = "Scheme-dependent payload (structure depends on selected scheme)", requiredMode = REQUIRED, oneOf = {ExactSchemePayload.class})
        Object payload,

        @Valid
        @Schema(description = "Protocol extensions data", nullable = true)
        @Nullable Extensions extensions

) {

    /**
     * Get the X402Version enum corresponding to the x402Version field.
     *
     * @return the X402 version
     */
    @JsonIgnore
    public X402Version getX402Version() {
        return X402Versions.findByVersion(x402Version())
                .orElseThrow(() -> new InvalidX402VersionException("Unknown x402 version: " + x402Version()));
    }

    /**
     * Get the scheme from the accepted payment requirements.
     *
     * @return the scheme
     * @throws InvalidX402SchemeException if the scheme is unsupported
     */
    @JsonIgnore
    public Scheme getScheme() {
        if (accepted == null) {
            throw new InvalidX402SchemeException("Missing accepted payment requirements");
        }
        return Schemes.findByName(accepted.scheme())
                .orElseThrow(() -> new InvalidX402SchemeException("Unsupported scheme: " + accepted.scheme()));
    }

    /**
     * Get the payload cast to its specific type based on the accepted scheme.
     *
     * @return the payload cast to its specific type
     * @throws InvalidX402SchemeException if the scheme is unsupported
     */
    @JsonIgnore
    public SchemePayload getTypedPayload() {
        return JsonUtil.convertValue(payload, getScheme().payloadClass());
    }

    /**
     * Get the nonce from the payload.
     *
     * @return the nonce if available
     */
    @JsonIgnore
    public Optional<String> getNonce() {
        return extract(ExactSchemePayload::getNonce);
    }

    /**
     * Get the from address from the payload.
     *
     * @return the from address if available
     */
    @JsonIgnore
    public Optional<String> getFromAddress() {
        return extract(ExactSchemePayload::getFromAddress);
    }

    /**
     * Get the to address from the payload.
     *
     * @return the to address if available
     */
    @JsonIgnore
    public Optional<String> getToAddress() {
        return extract(ExactSchemePayload::getToAddress);
    }

    /**
     * Get the amount from the payload.
     *
     * @return the amount if available
     */
    @JsonIgnore
    public Optional<BigInteger> getAmount() {
        return extract(ExactSchemePayload::getAmount);
    }

    /**
     * Generic extractor for ExactSchemePayload fields.
     * Note: only supports the "exact" scheme — returns empty for any other scheme.
     *
     * @param extractor extractor function
     * @param <T>       type of the extracted value
     * @return the extracted value if available
     */
    private <T> Optional<T> extract(final Function<ExactSchemePayload, Optional<T>> extractor) {
        return Optional.ofNullable(payload)
                .map(p -> JsonUtil.convertValue(p, ExactSchemePayload.class))
                .flatMap(extractor);
    }

}
