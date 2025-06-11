package tech.mogami.commons.header.payment;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import tech.mogami.commons.header.payment.schemes.exact.ExactSchemePayload;
import tech.mogami.commons.validator.Network;
import tech.mogami.commons.validator.Scheme;

import static tech.mogami.commons.header.payment.PaymentConstants.SCHEME_PARAMETER;

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
@SuppressWarnings("unused")
public record PaymentPayload(
        Integer x402Version,

        @NotBlank(message = "{validation.paymentPayload.scheme.required}")
        @Scheme(message = "{validation.paymentPayload.scheme.invalid}")
        String scheme,

        @NotBlank(message = "{validation.paymentPayload.network.required}")
        @Network(message = "{validation.paymentPayload.network.invalid}")
        String network,

        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = SCHEME_PARAMETER)
        @JsonSubTypes({
                @JsonSubTypes.Type(value = ExactSchemePayload.class, name = "exact")
        })
        Object payload
) {
}
