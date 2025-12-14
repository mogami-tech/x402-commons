package tech.mogami.commons.test.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.facilitator.settle.SettleRequest;
import tech.mogami.commons.api.facilitator.verify.VerifyRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.mogami.commons.constant.version.X402Versions.V1;
import static tech.mogami.commons.constant.version.X402Versions.V2;

@DisplayName("Get version test")
public class GetVersionTest {

    @Test
    @DisplayName("Get version on /verify request")
    public void testGetVersionOnVerifyRequest() {
        // Empty paymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .build()
                .getVersion()).isEmpty();

        // With value.
        assertThat(VerifyRequest.builder()
                .x402Version(1)
                .build()
                .getVersion()).hasValue(V1);
        assertThat(VerifyRequest.builder()
                .x402Version(2)
                .build()
                .getVersion()).hasValue(V2);
    }

    @Test
    @DisplayName("Get version on /settle request")
    public void testGetVersionOnSettleRequest() {
        // Empty paymentPayload in SettleRequest.
        assertThat(SettleRequest.builder()
                .build()
                .getNonce()).isEmpty();

        // With value.
        assertThat(SettleRequest.builder()
                .x402Version(1)
                .build()
                .getVersion()).hasValue(V1);
        assertThat(SettleRequest.builder()
                .x402Version(2)
                .build()
                .getVersion()).hasValue(V2);
    }

}
