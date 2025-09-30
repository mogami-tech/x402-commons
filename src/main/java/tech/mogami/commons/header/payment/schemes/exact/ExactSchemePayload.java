package tech.mogami.commons.header.payment.schemes.exact;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.apache.commons.lang3.StringUtils;
import tech.mogami.commons.validator.BigIntegerString;
import tech.mogami.commons.validator.BlockchainAddress;

import java.util.Optional;

/**
 * Exact scheme payload.
 *
 * @param signature     the signature of the EIP-3009 transferWithAuthorization operation.
 * @param authorization parameters required to reconstruct the messaged signed for the transferWithAuthorization operation.
 */
@Builder(toBuilder = true)
@Jacksonized
@Schema(description = "Payload for the 'exact' scheme, using EIP-3009 transferWithAuthorization")
@SuppressWarnings("unused")
public record ExactSchemePayload(

        @NotBlank(message = "{validation.exactSchemePayload.signature.required}")
        @Schema(description = "Signature of the EIP-3009 transferWithAuthorization operation", example = "0xabcdef1234567890...")
        String signature,

        @Valid
        @NotNull(message = "{validation.exactSchemePayload.authorization.required}")
        @Schema(description = "Authorization parameters required to reconstruct the signed message")
        Authorization authorization) {

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
    @Builder
    @Jacksonized
    @SuppressWarnings("unused")
    public record Authorization(

            @NotBlank(message = "{validation.exactSchemePayload.authorization.from.required}")
            @BlockchainAddress(message = "{validation.exactSchemePayload.authorization.from.invalid}")
            @Schema(description = "Ethereum address of the token sender", example = "0x2980bc24bBFB34DE1BBC91479Cb712ffbCE02F73")
            String from,

            @NotBlank(message = "{validation.exactSchemePayload.authorization.to.required}")
            @BlockchainAddress(message = "{validation.exactSchemePayload.authorization.to.invalid}")
            @Schema(description = "Ethereum address of the token recipient", example = "0x1234567890abcdef1234567890abcdef12345678")
            String to,

            @NotBlank(message = "{validation.exactSchemePayload.authorization.value.required}")
            @BigIntegerString(message = "{validation.exactSchemePayload.authorization.value.invalid}")
            @Schema(description = "Token amount to be transferred (as string representing uint256)", example = "1000000000000000000")
            String value,

            @NotBlank(message = "{validation.exactSchemePayload.authorization.validAfter.required}")
            @Schema(description = "Timestamp (in seconds) after which the authorization becomes valid", example = "1718542400")
            String validAfter,

            @NotBlank(message = "{validation.exactSchemePayload.authorization.validBefore.required}")
            @Schema(description = "Timestamp (in seconds) before which the authorization is valid", example = "1718642400")
            String validBefore,

            @NotBlank(message = "{validation.exactSchemePayload.authorization.nonce.required}")
            @Schema(description = "Unique nonce to prevent replay of the authorization", example = "0xdeadbeefcafebabe12345678abcdef12")
            String nonce) {
    }

    /**
     * Get the nonce from the payload.
     *
     * @return the nonce if available
     */
    @JsonIgnore
    public Optional<String> getNonce() {
        if (authorization == null) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(StringUtils.trimToNull(authorization.nonce()));
        }
    }

}
