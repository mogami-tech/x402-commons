package tech.mogami.commons.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;
import tech.mogami.commons.validator.BigIntegerString;
import tech.mogami.commons.validator.BlockchainAddress;
import tech.mogami.commons.validator.NetworkId;
import tech.mogami.commons.validator.Scheme;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

import static java.math.BigInteger.ZERO;

/**
 * Payment requirement returned to the client when he tries to access a resource.
 *
 * @param scheme            Scheme of the payment protocol to use.
 *                          A schene is a structured definition that specifies the format,
 *                          validation rules and processing logic for a specific type of transaction
 * @param network           Blockchain network identifier in CAIP-2 format (e.g., "eip155:84532")
 * @param amount            Required payment amount in atomic token units
 * @param asset             Address of the EIP-3009 compliant ERC20 contract (example: an ERC20 contract address).
 * @param payTo             Recipient wallet address or role constant (e.g., merchant)
 * @param maxTimeoutSeconds Maximum time allowed for payment completion
 * @param extra             Extra information about the payment details specific to the scheme
 *                          For `exact` scheme on the EVM network,
 *                          expects extra to contain the records `name` and `version` pertaining to asset
 */
@Builder
@Jacksonized
@Schema(description = "Payment requirement returned to the client when accessing a protected resource")
@SuppressWarnings("unused")
public record PaymentRequirements(

        @NotBlank(message = "{validation.paymentRequirements.scheme.required}")
        @Scheme(message = "{validation.paymentRequirements.scheme.invalid}")
        @Schema(description = "Payment scheme identifier", example = "exact")
        String scheme,

        @NotBlank(message = "{validation.paymentRequirements.network.required}")
        @NetworkId(message = "{validation.paymentRequirements.network.invalid}")
        @Schema(description = "Blockchain network identifier in CAIP-2 format", example = "eip155:84532")
        String network,

        @NotBlank(message = "{validation.paymentRequirements.amount.required}")
        @BigIntegerString(message = "{validation.paymentRequirements.amount.invalid}")
        @Schema(description = "Required payment amount in atomic token units", example = "100000")
        String amount,

        @NotBlank(message = "{validation.paymentRequirements.asset.required}")
        @BlockchainAddress(message = "{validation.paymentRequirements.asset.invalid}")
        @Schema(description = "Contract asset address", example = "0xABCDEF1234567890...")
        String asset,

        @NotBlank(message = "{validation.paymentRequirements.payTo.required}")
        @BlockchainAddress(message = "{validation.paymentRequirements.payTo.invalid}")
        @Schema(description = "Recipient wallet address or role constant (e.g., merchant)", example = "0x1234...")
        String payTo,

        @NotNull(message = "{validation.paymentRequirements.maxTimeoutSeconds.required}")
        @Positive(message = "{validation.paymentRequirements.maxTimeoutSeconds.positive}")
        @Schema(description = "Maximum time allowed for payment completion", example = "60")
        Integer maxTimeoutSeconds,

        @Schema(description = "Extra scheme-specific information. For `exact` on EVM: should contain asset `name` and `version`", example = "{\"name\": \"USDC\", \"version\": \"2\"}")
        @Singular("extra") Map<String, String> extra

) {

    /**
     * Get the amount required as a BigInteger.
     *
     * @return the amount required to be converted to BigInteger
     */
    @JsonIgnore
    public BigInteger amountAsBigInteger() {
        if (StringUtils.isBlank(amount)) {
            return ZERO;
        }
        try {
            BigInteger value = new BigInteger(amount.trim());
            if (value.signum() < 0) {
                throw new IllegalArgumentException("amount cannot be negative: " + amount);
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount: '" + amount + "'", e);
        }
    }

    /**
     * Get an extra value by its key.
     *
     * @param key the key of the extra value
     * @return an Optional containing the extra value if present, or empty if not found
     */
    public Optional<String> getExtra(@Nullable final String key) {
        return Optional.ofNullable(key)
                .filter(StringUtils::isNotEmpty)
                .map(extra::get);
    }

}
