package tech.mogami.commons.api.facilitator.settle;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;
import tech.mogami.commons.api.payment.extensions.Extensions;
import tech.mogami.commons.validator.NetworkId;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * Response returned after settlement processing.
 *
 * @param success     Indicates whether the payment settlement was successful
 * @param errorReason Error reason if settlement failed (omitted if successful)
 * @param payer       Address of the payer's wallet
 * @param transaction Blockchain transaction hash (empty string if settlement failed)
 * @param network     Blockchain network identifier in CAIP-2 format
 * @param amount      Actual amount settled in atomic units (omitted if not applicable)
 * @param extensions  Protocol extensions data
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
        @NotNull(message = "{validation.settlementResponse.transaction.required}")
        @Schema(description = "Blockchain transaction hash (empty string if settlement failed)", example = "0x123...", requiredMode = REQUIRED)
        String transaction,

        @JsonProperty(required = true)
        @NotBlank(message = "{validation.settlementResponse.network.required}")
        @NetworkId(message = "{validation.settlementResponse.network.invalid}")
        @Schema(description = "Blockchain network identifier in CAIP-2 format", example = "eip155:84532", requiredMode = REQUIRED)
        String network,

        @Schema(description = "Actual amount settled in atomic units", example = "10000", nullable = true)
        @Nullable String amount,

        @Schema(description = "Protocol extensions data", nullable = true)
        @Nullable Extensions extensions

) {
}
