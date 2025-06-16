package tech.mogami.commons.api.facilitator.verify;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

/**
 * Verify response returned by the x402 facilitator for a verification attempt.
 *
 * @param isValid       verification status
 * @param invalidReason reason why the verification failed
 * @param payer         payer address
 */
@Builder
@Jacksonized
@Schema(description = "Verify response returned by the x402 facilitator for a verification attempt")
@SuppressWarnings("unused")
public record VerifyResponse(

        @Schema(description = "Request is valid", example = "true")
        boolean isValid,

        @Schema(description = "Reason why the verification failed, or null if valid", example = "invalid_payload", nullable = true)
        String invalidReason,

        @Schema(description = "Payer address, or null if not applicable", example = "0x1234abcd...", nullable = true)
        String payer) {
}
