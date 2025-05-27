package tech.mogami.commons.api.facilitator.verify;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

/**
 * VerifyResponse is a response from the facilitator service.
 *
 * @param isValid       verification status
 * @param invalidReason reason why the verification failed
 * @param payer         payer address
 */
@Builder
@Jacksonized
@SuppressWarnings("unused")
public record VerifyResponse(
        boolean isValid,
        String invalidReason,
        String payer) {
}
