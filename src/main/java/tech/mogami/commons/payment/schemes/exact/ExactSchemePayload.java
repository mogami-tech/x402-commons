package tech.mogami.commons.payment.schemes.exact;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.apache.commons.lang3.StringUtils;
import tech.mogami.commons.validator.BigIntegerString;
import tech.mogami.commons.validator.BlockchainAddress;

import java.math.BigInteger;
import java.util.Optional;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * Exact scheme payload.
 * It uses EIP-3009 (Transfer with Authorization) to enable gasless transfers of specific amounts of ERC-20 tokens.
 *
 * @param signature     EIP-712 signature for authorization (transferWithAuthorization).
 * @param authorization EIP-3009 authorization parameters.
 */
@Builder(toBuilder = true)
@Jacksonized
@Schema(description = "Payload for the 'exact' scheme, using EIP-3009 transferWithAuthorization")
@SuppressWarnings("unused")
public record ExactSchemePayload(

        @JsonProperty(required = true)
        @NotBlank(message = "{validation.exactSchemePayload.signature.required}")
        @Schema(description = "EIP-712 signature for authorization (transferWithAuthorization)", example = "0xabcdef1234567890...")
        String signature,

        @JsonProperty(required = true)
        @Valid
        @NotNull(message = "{validation.exactSchemePayload.authorization.required}")
        @Schema(description = "EIP-3009 authorization parameters", requiredMode = REQUIRED)
        Authorization authorization

) {

    /**
     * Authorization parameters required to reconstruct the messaged signed for the transferWithAuthorization operation.
     *
     * @param from        Payer's wallet address
     * @param to          Recipient's wallet address
     * @param value       the number of tokens to be transferred
     * @param validAfter  the timestamp after which the authorization is isValid
     * @param validBefore the timestamp before which the authorization is isValid
     * @param nonce       a unique identifier for the authorization
     */
    @Builder
    @Jacksonized
    @SuppressWarnings("unused")
    public record Authorization(

            @JsonProperty(required = true)
            @NotBlank(message = "{validation.exactSchemePayload.authorization.from.required}")
            @BlockchainAddress(message = "{validation.exactSchemePayload.authorization.from.invalid}")
            @Schema(description = "Payer's wallet address", example = "0x2980bc24bBFB34DE1BBC91479Cb712ffbCE02F73", requiredMode = REQUIRED)
            String from,

            @JsonProperty(required = true)
            @NotBlank(message = "{validation.exactSchemePayload.authorization.to.required}")
            @BlockchainAddress(message = "{validation.exactSchemePayload.authorization.to.invalid}")
            @Schema(description = "Recipient's wallet address", example = "0x1234567890abcdef1234567890abcdef12345678", requiredMode = REQUIRED)
            String to,

            @JsonProperty(required = true)
            @NotBlank(message = "{validation.exactSchemePayload.authorization.value.required}")
            @BigIntegerString(message = "{validation.exactSchemePayload.authorization.value.invalid}")
            @Schema(description = "Payment amount in atomic units (as string representing uint256)", example = "1000000000000000000", requiredMode = REQUIRED)
            String value,

            @JsonProperty(required = true)
            @NotBlank(message = "{validation.exactSchemePayload.authorization.validAfter.required}")
            @Schema(description = "Timestamp (in seconds) after which the authorization becomes valid", example = "1718542400", requiredMode = REQUIRED)
            String validAfter,

            @JsonProperty(required = true)
            @NotBlank(message = "{validation.exactSchemePayload.authorization.validBefore.required}")
            @Schema(description = "Timestamp (in seconds) before which the authorization is valid", example = "1718642400", requiredMode = REQUIRED)
            String validBefore,

            @JsonProperty(required = true)
            @NotBlank(message = "{validation.exactSchemePayload.authorization.nonce.required}")
            @Schema(description = "32-byte random nonce to prevent replay attacks", example = "0xdeadbeefcafebabe12345678abcdef12", requiredMode = REQUIRED)
            String nonce

    ) {
    }

    /**
     * Get the nonce from the payload.
     *
     * @return the nonce if available
     */
    @JsonIgnore
    public Optional<String> getNonce() {
        if (authorization != null) {
            return Optional.ofNullable(StringUtils.trimToNull(authorization.nonce()));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Get the from address from the payload.
     *
     * @return the from address if available
     */
    @JsonIgnore
    public Optional<String> getFromAddress() {
        if (authorization != null) {
            return Optional.ofNullable(StringUtils.trimToNull(authorization.from()));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Get the to address from the payload.
     *
     * @return the to address if available
     */
    @JsonIgnore
    public Optional<String> getToAddress() {
        if (authorization != null) {
            return Optional.ofNullable(StringUtils.trimToNull(authorization.to()));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Get the amount from the payload.
     *
     * @return the amount if available
     */
    @JsonIgnore
    public Optional<java.math.BigInteger> getAmount() {
        if (authorization != null) {
            final String stringValue = StringUtils.trimToNull(authorization.value());
            if (stringValue != null) {
                try {
                    return Optional.of(new BigInteger(stringValue));
                } catch (NumberFormatException e) {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

}
