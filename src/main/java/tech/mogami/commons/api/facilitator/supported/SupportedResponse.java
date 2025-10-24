package tech.mogami.commons.api.facilitator.supported;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * Supported response for GET /supported.
 *
 * @param kinds list of supported (scheme, network) pairs
 */
@Builder
@Jacksonized
@Schema(description = "List of supported payment kinds by the facilitator")
@SuppressWarnings("unused")
public record SupportedResponse(

        @Schema(description = "List of supported payment kinds (x402 version, scheme, and network)")
        @Singular List<SupportedKind> kinds

) {

    /**
     * Single pair the facilitator can handle.
     *
     * @param x402Version x402 version
     * @param scheme      the scheme used for the payment
     * @param network     the network used for the payment
     */
    @Builder
    @Jacksonized
    @Schema(description = "Supported payment kind with version, scheme, and network")
    @SuppressWarnings("unused")
    public record SupportedKind(

            @Schema(description = "x402 protocol version supported", example = "1")
            int x402Version,

            @Schema(description = "Scheme identifier", example = "exact")
            String scheme,

            @Schema(description = "Blockchain network supported", example = "base-sepolia")
            String network

    ) {

        /**
         * Returns a formatted string representation of the SupportedKind.
         *
         * @return formatted string
         */
        public String toFormattedString() {
            return "%s / %s".formatted(network, scheme);
        }

    }

}


