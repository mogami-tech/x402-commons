package tech.mogami.commons.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * Response returned after settlement processing.
 *
 * @param success      Indicates whether the payment settlement was successful
 * @param errorReason  Error reason if settlement failed (omitted if successful)
 * @param payer        Address of the payer's wallet
 * @param transaction  Blockchain transaction hash of the settled payment, or null if error
 * @param network      Blockchain network identifier in CAIP-2 format
 * @param requirements Payment requirements used for the settlement (TODO Not in the specs! Suppress this.)
 */
@Builder
@Jacksonized
@Schema(description = "Response returned after settlement processing")
@SuppressWarnings("unused")
public record SettlementResponse(

        @JsonProperty(required = true)
        @Schema(description = "Indicates whether the payment settlement was successful", example = "true", requiredMode = REQUIRED)
        boolean success,

        @Schema(description = "Error reason if settlement failed (omitted if successful)", example = "invalid_payload", nullable = true)
        @Nullable String errorReason,

        @Schema(description = "Address of the payer's wallet", example = "0x1234...", nullable = true)
        @Nullable String payer,

        @JsonProperty(required = true)
        @Schema(description = "Blockchain transaction hash of the settled payment, or null if error", example = "0x123...", requiredMode = REQUIRED)
        String transaction,

        @JsonProperty(required = true)
        @Schema(description = "Blockchain network identifier in CAIP-2 format", example = "eip155:84532", requiredMode = REQUIRED)
        String network,

        // TODO Not in the specs! Suppress this.
        PaymentRequirements requirements

) {
}
