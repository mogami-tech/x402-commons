package tech.mogami.commons.test.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.facilitator.supported.SupportedResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.mogami.commons.api.payment.schemes.Schemes.EXACT_SCHEME;
import static tech.mogami.commons.constant.network.Networks.BASE_SEPOLIA;
import static tech.mogami.commons.constant.x402.X402Versions.V2;

@DisplayName("SupportedKind tests")
public class SupportedKindTest {

    @Test
    @DisplayName("toFormattedString()")
    void toFormattedString() {
        assertThat(SupportedResponse.SupportedKind.builder()
                .build()
                .toFormattedString()).isEqualTo("x402:Vnull/null/null");

        assertThat(SupportedResponse.SupportedKind.builder()
                .x402Version(V2.version())
                .scheme(EXACT_SCHEME.name())
                .network(BASE_SEPOLIA.networkId())
                .build()
                .toFormattedString()).isEqualTo("x402:V2/eip155:84532/exact");
    }

}
