package tech.mogami.commons.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;
import tech.mogami.commons.payment.schemes.exact.ExactSchemePayload;
import tech.mogami.commons.validator.X402Version;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static tech.mogami.commons.payment.schemes.Schemes.SCHEME_PARAMETER;

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

        @NotNull(message = "{validation.paymentPayload.x402Version.required}")
        @X402Version(message = "{validation.paymentPayload.x402Version.invalid}")
        @Schema(description = "Version of the x402 payment protocol", example = "2")
        Integer x402Version,

        @Valid
        @Schema(description = "Resource requiring payment", nullable = true)
        @Nullable PaymentResource resource,

        @Valid
        @NotNull(message = "{validation.paymentRequired.accepts.required}")
        @Schema(description = "PaymentRequirements object indicating the payment method chosen")
        PaymentRequirements accepted,

        @Valid
        @NotNull(message = "{validation.paymentPayload.payload.required}")
        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = SCHEME_PARAMETER)
        @JsonSubTypes({
                @JsonSubTypes.Type(value = ExactSchemePayload.class, name = "exact")
        })
        @Schema(description = "Scheme-dependent payload (structure depends on selected scheme)", oneOf = {ExactSchemePayload.class})
        Object payload,

        @Schema(description = "Protocol extensions data", nullable = true)
        @Nullable Map<String, Object> extensions

) {

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
     *
     * @param extractor extractor function
     * @param <T>       type of the extracted value
     * @return the extracted value if available
     */
    private <T> Optional<T> extract(final Function<ExactSchemePayload, Optional<T>> extractor) {
        return Optional.ofNullable(payload)
                .flatMap(p -> switch (p) {
                    case ExactSchemePayload exactPayload -> extractor.apply(exactPayload);
                    default -> Optional.empty();
                });
    }

}
