package tech.mogami.commons.api.facilitator.supported;

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
@SuppressWarnings("unused")
public record SupportedResponse(
        @Singular List<SupportedKind> kinds
) {

    /**
     * Single pair the facilitator can handle.
     *
     * @param x402Version x402 version
     * @param scheme      the scheme used for the payment
     * @param network     the network used for the payment
     */
    @Jacksonized
    @Builder
    @SuppressWarnings("unused")
    public record SupportedKind(
            int x402Version,
            String scheme,
            String network) {
    }

}


