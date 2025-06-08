package tech.mogami.commons.header.payment;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;
import org.apache.commons.lang3.StringUtils;
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
@SuppressWarnings("unused")
public record PaymentRequirements(

        @NotBlank(message = "{validation.paymentRequirements.scheme.required}")
        @Scheme(message = "{validation.paymentRequirements.scheme.invalid}")
        String scheme,

        @NotBlank(message = "{validation.paymentRequirements.network.required}")
        @Network(message = "{validation.paymentRequirements.network.invalid}")
        String network,

        String maxAmountRequired,
        String resource,
        String description,
        String mimeType,
        String payTo,
        int maxTimeoutSeconds,
        String asset,
        @Singular("extra") Map<String, String> extra
) {

    /**
     * Get an extra value by its key.
     *
     * @param key the key of the extra value
     * @return an Optional containing the extra value if present, or empty if not found
     */
    public Optional<String> getExtra(final String key) {
        if (StringUtils.isEmpty(key)) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(extra.get(key));
        }
    }

}
