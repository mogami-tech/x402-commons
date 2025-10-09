package tech.mogami.commons.header.payment;

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
import tech.mogami.commons.validator.Network;
import tech.mogami.commons.validator.Scheme;

import java.util.Map;
import java.util.Optional;

/**
 * Payment requirement returned to the client when he tries to access a resource.
 *
 * @param scheme            Scheme of the payment protocol to use.
 *                          A schene is a structured definition that specifies the format,
 *                          validation rules and processing logic for a specific type of transaction
 * @param network           Network of the blockchain to send payment on (e.g., 'base-mainnet')
 * @param maxAmountRequired Maximum amount required to pay for the resource in atomic units of the asset (e.g., '0.10')
 * @param resource          URL of resource to pay for
 * @param description       Description of the resource
 * @param mimeType          MIME type of the resource (e.g., application/json)
 * @param payTo             Address to pay value to
 * @param maxTimeoutSeconds Maximum time in seconds for the resource server to respond (e.g., 60)
 * @param asset             Address of the EIP-3009 compliant ERC20 contract (example: an ERC20 contract address).
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
        @Schema(description = "Scheme of the payment protocol to use", example = "exact")
        String scheme,

        @NotBlank(message = "{validation.paymentRequirements.network.required}")
        @Network(message = "{validation.paymentRequirements.network.invalid}")
        @Schema(description = "Blockchain network to send the payment on", example = "base-sepolia")
        String network,

        @NotBlank(message = "{validation.paymentRequirements.maxAmountRequired.required}")
        @BigIntegerString(message = "{validation.paymentRequirements.maxAmountRequired.invalid}")
        @Schema(description = "Maximum amount required to pay in atomic units (e.g., smallest token unit)", example = "100000")
        String maxAmountRequired,

        @NotBlank(message = "{validation.paymentRequirements.resource.required}")
        @Schema(description = "URL of the resource to pay for", example = "https://example.com/weather")
        String resource,

        @Schema(description = "Description of the resource", example = "Accurate weather data for your location")
        @Nullable String description,

        @Schema(description = "MIME type of the resource", example = "application/json")
        @Nullable String mimeType,

        @NotBlank(message = "{validation.paymentRequirements.payTo.required}")
        @BlockchainAddress(message = "{validation.paymentRequirements.payTo.invalid}")
        @Schema(description = "Address to which payment should be made", example = "0x1234abcd...")
        String payTo,

        @NotNull(message = "{validation.paymentRequirements.maxTimeoutSeconds.required}")
        @Positive(message = "{validation.paymentRequirements.maxTimeoutSeconds.positive}")
        @Schema(description = "Maximum allowed time in seconds for the server to respond", example = "60")
        Integer maxTimeoutSeconds,

        @NotBlank(message = "{validation.paymentRequirements.asset.required}")
        @BlockchainAddress(message = "{validation.paymentRequirements.asset.invalid}")
        @Schema(description = "Contract asset address", example = "0xABCDEF1234567890...")
        String asset,

        @Schema(description = "Extra scheme-specific information. For `exact` on EVM: should contain asset `name` and `version`.", example = "{\"name\": \"USDC\", \"version\": \"2\"}")
        @Singular("extra") Map<String, String> extra

) {

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
