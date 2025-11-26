package tech.mogami.commons.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import tech.mogami.commons.payment.schemes.exact.ExactSchemePayload;
import tech.mogami.commons.validator.Network;
import tech.mogami.commons.validator.Scheme;
import tech.mogami.commons.validator.X402Version;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Function;

import static tech.mogami.commons.payment.PaymentConstants.SCHEME_PARAMETER;

/**
 * Payment payload (included as the X-PAYMENT header in base64 encoded JSON).
 *
 * @param x402Version version of the x402 payment protocol
 * @param scheme      scheme is the scheme value of the accepted `paymentRequirements` the client is using to pay
 * @param network     network is the network id of the accepted `paymentRequirements` the client is using to pay
 * @param payload     payload is scheme dependent
 */
@Builder(toBuilder = true)
@Jacksonized
@Schema(description = "Payment payload sent by the client")
@SuppressWarnings("unused")
public record PaymentPayload(

        @NotNull(message = "{validation.paymentPayload.x402Version.required}")
        @X402Version(message = "{validation.paymentPayload.x402Version.invalid}")
        @Schema(description = "Version of the x402 payment protocol", example = "1")
        Integer x402Version,

        @NotBlank(message = "{validation.paymentPayload.scheme.required}")
        @Scheme(message = "{validation.paymentPayload.scheme.invalid}")
        @Schema(description = "Scheme used to pay", example = "exact")
        String scheme,

        @NotBlank(message = "{validation.paymentPayload.network.required}")
        @Network(message = "{validation.paymentPayload.network.invalid}")
        @Schema(description = "Network used to pay", example = "base-sepolia")
        String network,

        @Valid
        @NotNull(message = "{validation.paymentPayload.payload.required}")
        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = SCHEME_PARAMETER)
        @JsonSubTypes({
                @JsonSubTypes.Type(value = ExactSchemePayload.class, name = "exact")
        })
        @Schema(description = "Scheme-dependent payload (structure depends on selected scheme)", oneOf = {ExactSchemePayload.class})
        Object payload

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
     * Get the asset contract from the payload.
     *
     * @return the asset contract if available
     */
    @JsonIgnore
    public Optional<String> getNetworkName() {
        return Optional.ofNullable(network);
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
