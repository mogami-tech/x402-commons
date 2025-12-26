package tech.mogami.commons.api.facilitator.verify;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;

/**
 * Response returned after verification processing.
 *
 * @param isValid       Indicates whether the payment authorization is valid
 * @param invalidReason Reason for invalidity (omitted if valid)
 * @param payer         Address of the payer's wallet
 */
@Builder
@Jacksonized
@Schema(description = "Response returned after verification processing")
@SuppressWarnings("unused")
public record VerificationResponse(

        @Schema(description = "Indicates whether the payment authorization is valid", example = "true")
        boolean isValid,

        @Schema(description = "Reason for invalidity (omitted if valid)", example = "invalid_payload", nullable = true)
        @Nullable String invalidReason,

        @Schema(description = "Address of the payer's wallet", example = "0x1234...", nullable = true)
        @Nullable String payer

) {
}
