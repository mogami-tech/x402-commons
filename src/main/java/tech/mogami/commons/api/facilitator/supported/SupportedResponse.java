package tech.mogami.commons.api.facilitator.supported;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Map;

/**
 * Supported response for GET /supported.
 *
 * @param kinds      list of supported (scheme, network) pairs
 * @param extensions list of supported extension identifiers
 * @param signers    map of CAIP-2 patterns to public signer addresses
 */
@Builder
@Jacksonized
@Schema(description = "List of supported payment kinds by the facilitator")
@SuppressWarnings("unused")
public record SupportedResponse(

        @Schema(description = "List of supported payment kinds (x402 version, scheme, and network)")
        @Singular List<SupportedKind> kinds,

        @Schema(description = "List of extension identifiers implemented by the facilitator", example = "[]")
        @Singular List<String> extensions,

        @Schema(description = "Map of CAIP-2 patterns to public signer addresses",
                example = """
                        {
                          "eip155:*": ["0x1234567890abcdef1234567890abcdef12345678"],
                          "solana:*": ["CKPKJWNdJEqa81x7CkZ14BVPiY6y16Sxs7owznqtWYp5"]
                        }
                        """
        )
        @Singular Map<String, List<String>> signers

) {

    /**
     * Constructor.
     */
    public SupportedResponse {
        if (kinds == null) {
            kinds = List.of();
        }
        if (extensions == null) {
            extensions = List.of();
        }
        if (signers == null) {
            signers = Map.of();
        }
    }

    /**
     * Single pair the facilitator can handle.
     *
     * @param x402Version x402 version
     * @param scheme      the scheme used for the payment
     * @param network     the network used for the payment
     * @param extra       additional scheme-specific configuration
     */
    @Builder
    @Jacksonized
    @Schema(description = "Supported payment kind with version, scheme, and network")
    @SuppressWarnings("unused")
    public record SupportedKind(

            @Schema(description = "x402 protocol version supported", example = "2")
            Integer x402Version,

            @Schema(description = "Scheme identifier", example = "exact")
            String scheme,

            @Schema(description = "Blockchain network identifier in CAIP-2 format", example = "eip155:84532")
            String network,

            @Schema(description = "Additional scheme-specific configuration", nullable = true)
            Map<String, Object> extra

    ) {

        /**
         * Returns a formatted string representation of the SupportedKind.
         *
         * @return formatted string
         */
        @JsonIgnore
        public String toFormattedString() {
            return "x402:V%s/%s/%s".formatted(x402Version, network, scheme);
        }

    }

}


