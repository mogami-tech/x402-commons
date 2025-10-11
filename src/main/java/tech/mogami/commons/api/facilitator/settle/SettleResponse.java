package tech.mogami.commons.api.facilitator.settle;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;

/**
 * Settlement response returned by the x402 facilitator for a settle attempt.
 *
 * @param success     true if the payment succeeded
 * @param network     identifier of the blockchain network
 * @param transaction blockchain transaction hash of the settled payment, or null
 * @param errorReason error message from the facilitator, or null
 * @param payer       Address of the payer's wallet , or null
 */
@Builder
@Jacksonized
@Schema(description = "Settlement response returned by the x402 facilitator for a settle attempt")
@SuppressWarnings("unused")
public record SettleResponse(

        @Schema(description = "True if the payment succeeded", example = "true")
        boolean success,

        @Schema(description = "Identifier of the blockchain network", example = "base-sepolia")
        String network,

        @Schema(description = "Blockchain transaction hash of the settled payment, or null if error", example = "0xabc123...", nullable = true)
        @Nullable String transaction,

        @Schema(description = "Error message from the facilitator, or null if success", example = "invalid_payload", nullable = true)
        @Nullable String errorReason,

        @Schema(description = "Address of the payer's wallet, or null if error", example = "0x1234abcd...", nullable = true)
        @Nullable String payer

) {
}
