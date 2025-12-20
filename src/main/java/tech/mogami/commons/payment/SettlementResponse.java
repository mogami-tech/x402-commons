package tech.mogami.commons.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jspecify.annotations.Nullable;

/**
 * Response returned after settlement processing.
 */
@SuppressWarnings("unused")
public record SettlementResponse(

        @Schema(description = "Indicates whether the payment settlement was successful", example = "true")
        boolean success,

        @Schema(description = "Error reason if settlement failed (omitted if successful)", example = "invalid_payload", nullable = true)
        @Nullable String errorReason,

        @Schema(description = "Address of the payer's wallet", example = "0x1234...", nullable = true)
        @Nullable String payer,

        @Schema(description = "Blockchain transaction hash of the settled payment, or null if error", example = "0x123...", nullable = true)
        @Nullable String transaction,

        @Schema(description = "Blockchain network identifier in CAIP-2 format", example = "eip155:84532")
        String network

) {
}
