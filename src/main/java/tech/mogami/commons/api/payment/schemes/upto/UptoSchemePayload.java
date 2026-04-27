package tech.mogami.commons.api.payment.schemes.upto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.apache.commons.lang3.StringUtils;
import tech.mogami.commons.api.payment.schemes.SchemePayload;
import tech.mogami.commons.validator.BigIntegerString;
import tech.mogami.commons.validator.BlockchainAddress;
import tech.mogami.commons.validator.EIP712Signature;
import tech.mogami.commons.validator.UnixTimestampSeconds;

import java.math.BigInteger;
import java.util.Optional;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * Upto scheme payload.
 * It uses Permit2 (permitWitnessTransferFrom) to enable gasless transfers of up to a maximum ERC-20 token amount.
 * The actual settled amount is determined at settlement time based on resource consumption.
 *
 * @param signature            EIP-712 signature for the Permit2 authorization.
 * @param permit2Authorization Permit2 authorization parameters.
 */
@Builder(toBuilder = true)
@Jacksonized
@Schema(description = "Payload for the 'upto' scheme, using Permit2 permitWitnessTransferFrom")
@SuppressWarnings("unused")
public record UptoSchemePayload(

        @JsonProperty(required = true)
        @NotBlank(message = "{validation.uptoSchemePayload.signature.required}")
        @EIP712Signature(message = "{validation.uptoSchemePayload.signature.invalid}")
        @Schema(description = "EIP-712 signature for the Permit2 authorization (permitWitnessTransferFrom)", example = "0xabcdef1234567890...", requiredMode = REQUIRED)
        String signature,

        @JsonProperty(required = true)
        @Valid
        @NotNull(message = "{validation.uptoSchemePayload.permit2Authorization.required}")
        @Schema(description = "Permit2 authorization parameters", requiredMode = REQUIRED)
        Permit2Authorization permit2Authorization

) implements SchemePayload {

    /**
     * Get the nonce from the payload.
     *
     * @return the nonce if available
     */
    @JsonIgnore
    public Optional<String> getNonce() {
        return Optional.ofNullable(permit2Authorization)
                .map(Permit2Authorization::nonce)
                .map(StringUtils::trimToNull);
    }

    /**
     * Get the from address from the payload.
     *
     * @return the from address if available
     */
    @JsonIgnore
    public Optional<String> getFromAddress() {
        return Optional.ofNullable(permit2Authorization)
                .map(Permit2Authorization::from)
                .map(StringUtils::trimToNull);
    }

    /**
     * Get the to address from the payload.
     *
     * @return the to address if available
     */
    @JsonIgnore
    public Optional<String> getToAddress() {
        return Optional.ofNullable(permit2Authorization)
                .map(Permit2Authorization::witness)
                .map(Witness::to)
                .map(StringUtils::trimToNull);
    }

    /**
     * Get the maximum authorized amount from the payload.
     *
     * @return the amount if available
     */
    @JsonIgnore
    public Optional<BigInteger> getAmount() {
        return Optional.ofNullable(permit2Authorization)
                .map(Permit2Authorization::permitted)
                .map(Permitted::amount)
                .map(StringUtils::trimToNull)
                .flatMap(value -> {
                    try {
                        return Optional.of(new BigInteger(value));
                    } catch (NumberFormatException e) {
                        return Optional.empty();
                    }
                });
    }

    /**
     * The token and maximum amount permitted for transfer.
     *
     * @param token  ERC-20 token contract address
     * @param amount Maximum amount authorized in atomic units (as string representing uint256)
     */
    @Builder
    @Jacksonized
    public record Permitted(

            @JsonProperty(required = true)
            @NotBlank(message = "{validation.uptoSchemePayload.permit2Authorization.permitted.token.required}")
            @BlockchainAddress(message = "{validation.uptoSchemePayload.permit2Authorization.permitted.token.invalid}")
            @Schema(description = "ERC-20 token contract address", example = "0x036CbD53842c5426634e7929541eC2318f3dCF7e", requiredMode = REQUIRED)
            String token,

            @JsonProperty(required = true)
            @NotBlank(message = "{validation.uptoSchemePayload.permit2Authorization.permitted.amount.required}")
            @BigIntegerString(message = "{validation.uptoSchemePayload.permit2Authorization.permitted.amount.invalid}")
            @Schema(description = "Maximum amount authorized in atomic units (as string representing uint256)", example = "5000000", requiredMode = REQUIRED)
            String amount

    ) {
    }

    /**
     * Witness data binding the authorization to a specific recipient and facilitator.
     *
     * @param to          Recipient's wallet address
     * @param facilitator Facilitator's contract address (binds the authorization to a specific facilitator)
     * @param validAfter  Timestamp (in seconds) after which the authorization becomes valid
     */
    @Builder
    @Jacksonized
    public record Witness(

            @JsonProperty(required = true)
            @NotBlank(message = "{validation.uptoSchemePayload.permit2Authorization.witness.to.required}")
            @BlockchainAddress(message = "{validation.uptoSchemePayload.permit2Authorization.witness.to.invalid}")
            @Schema(description = "Recipient's wallet address", example = "0x209693Bc6afc0C5328bA36FaF03C514EF312287C", requiredMode = REQUIRED)
            String to,

            @JsonProperty(required = true)
            @NotBlank(message = "{validation.uptoSchemePayload.permit2Authorization.witness.facilitator.required}")
            @BlockchainAddress(message = "{validation.uptoSchemePayload.permit2Authorization.witness.facilitator.invalid}")
            @Schema(description = "Facilitator contract address binding the authorization to a specific facilitator", example = "0x4020A4f3b7b90ccA423B9fabCc0CE57C6C240002", requiredMode = REQUIRED)
            String facilitator,

            @JsonProperty(required = true)
            @NotBlank(message = "{validation.uptoSchemePayload.permit2Authorization.witness.validAfter.required}")
            @UnixTimestampSeconds(message = "{validation.uptoSchemePayload.permit2Authorization.witness.validAfter.invalid}")
            @Schema(description = "Timestamp (in seconds) after which the authorization becomes valid", example = "1740672089", requiredMode = REQUIRED)
            String validAfter

    ) {
    }

    /**
     * Permit2 authorization parameters required to reconstruct the message signed for permitWitnessTransferFrom.
     *
     * @param permitted The token and maximum amount permitted
     * @param from      Payer's wallet address
     * @param spender   Address authorized to spend (the x402Permit2Proxy contract)
     * @param nonce     A unique identifier to prevent replay attacks
     * @param deadline  Timestamp (in seconds) after which the authorization expires
     * @param witness   Witness data binding recipient and facilitator
     */
    @Builder
    @Jacksonized
    public record Permit2Authorization(

            @JsonProperty(required = true)
            @Valid
            @NotNull(message = "{validation.uptoSchemePayload.permit2Authorization.permitted.required}")
            @Schema(description = "The token and maximum amount permitted for transfer", requiredMode = REQUIRED)
            Permitted permitted,

            @JsonProperty(required = true)
            @NotBlank(message = "{validation.uptoSchemePayload.permit2Authorization.from.required}")
            @BlockchainAddress(message = "{validation.uptoSchemePayload.permit2Authorization.from.invalid}")
            @Schema(description = "Payer's wallet address", example = "0x857b06519E91e3A54538791bDbb0E22373e36b66", requiredMode = REQUIRED)
            String from,

            @JsonProperty(required = true)
            @NotBlank(message = "{validation.uptoSchemePayload.permit2Authorization.spender.required}")
            @BlockchainAddress(message = "{validation.uptoSchemePayload.permit2Authorization.spender.invalid}")
            @Schema(description = "Address authorized to spend tokens (the x402Permit2Proxy contract)", example = "0x4020A4f3b7b90ccA423B9fabCc0CE57C6C240002", requiredMode = REQUIRED)
            String spender,

            @JsonProperty(required = true)
            @NotBlank(message = "{validation.uptoSchemePayload.permit2Authorization.nonce.required}")
            @Schema(description = "32-byte random nonce to prevent replay attacks", example = "0xf3746613c2d920b5fdabc0856f2aeb2d4f88ee6037b8cc5d04a71a4462f13480", requiredMode = REQUIRED)
            String nonce,

            @JsonProperty(required = true)
            @NotBlank(message = "{validation.uptoSchemePayload.permit2Authorization.deadline.required}")
            @UnixTimestampSeconds(message = "{validation.uptoSchemePayload.permit2Authorization.deadline.invalid}")
            @Schema(description = "Timestamp (in seconds) after which the authorization expires", example = "1740672154", requiredMode = REQUIRED)
            String deadline,

            @JsonProperty(required = true)
            @Valid
            @NotNull(message = "{validation.uptoSchemePayload.permit2Authorization.witness.required}")
            @Schema(description = "Witness data binding the authorization to a specific recipient and facilitator", requiredMode = REQUIRED)
            Witness witness

    ) {
    }

}
