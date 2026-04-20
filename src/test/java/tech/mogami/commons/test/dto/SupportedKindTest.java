package tech.mogami.commons.test.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.facilitator.supported.SupportedResponse;
import tech.mogami.commons.constant.version.X402Version;
import tech.mogami.commons.constant.version.X402Versions;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.mogami.commons.constant.network.Networks.BASE_SEPOLIA;
import static tech.mogami.commons.constant.version.X402Versions.V2;
import static tech.mogami.commons.payment.schemes.Schemes.EXACT_SCHEME;

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
