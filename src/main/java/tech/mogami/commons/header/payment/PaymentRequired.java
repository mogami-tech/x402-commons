package tech.mogami.commons.header.payment;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * Reply when an access request is made to a x402 protected URL.
 *
 * @param x402Version Version of the x402 payment protocol
 * @param accepts     List of payment requirements that the resource server accepts.
 *                    A resource server may accept on multiple chains or in multiple currencies.
 * @param error       Message from the resource server to the client to communicate errors in processing payment
 */
@Builder
@Jacksonized
@SuppressWarnings("unused")
public record PaymentRequired(
        int x402Version,
        List<PaymentRequirements> accepts,
        String error
) {
}
