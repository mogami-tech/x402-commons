package tech.mogami.commons.header.payment.schemes.exact;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

/**
 * Exact scheme payload.
 *
 * @param signature     the signature of the EIP-3009 transferWithAuthorization operation.
 * @param authorization parameters required to reconstruct the messaged signed for the transferWithAuthorization operation.
 */
@Jacksonized
@Builder(toBuilder = true)
@SuppressWarnings("unused")
public record ExactSchemePayload(
        String signature,
        Authorization authorization
) {

    /**
     * Authorization parameters required to reconstruct the messaged signed for the transferWithAuthorization operation.
     *
     * @param from        the address of the sender
     * @param to          the address of the recipient
     * @param value       the number of tokens to be transferred
     * @param validAfter  the timestamp after which the authorization is isValid
     * @param validBefore the timestamp before which the authorization is isValid
     * @param nonce       a unique identifier for the authorization
     */
    @Jacksonized
    @Builder
    @SuppressWarnings("unused")
    public record Authorization(
            String from,
            String to,
            String value,
            String validAfter,
            String validBefore,
            String nonce
    ) {
    }

}
