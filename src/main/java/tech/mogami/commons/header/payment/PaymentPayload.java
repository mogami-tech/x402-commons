package tech.mogami.commons.header.payment;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import tech.mogami.commons.header.payment.schemes.ExactSchemePayload;

import static tech.mogami.commons.header.payment.PaymentConstants.SCHEME_PARAMETER;
import static tech.mogami.commons.header.payment.schemes.ExactSchemeConstants.EXACT_SCHEME_NAME;

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
        int x402Version,
        String scheme,
        String network,
        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = SCHEME_PARAMETER)
        @JsonSubTypes({
                @JsonSubTypes.Type(value = ExactSchemePayload.class, name = EXACT_SCHEME_NAME)
        })
        Object payload
) {
}
