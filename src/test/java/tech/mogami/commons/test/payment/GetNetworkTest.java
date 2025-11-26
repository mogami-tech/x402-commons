package tech.mogami.commons.test.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.facilitator.settle.SettleRequest;
import tech.mogami.commons.api.facilitator.verify.VerifyRequest;
import tech.mogami.commons.payment.PaymentPayload;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Get network test")
public class GetNetworkTest {

    @Test
    @DisplayName("Get network on /verify request")
    public void testGetNetworkOnVerifyRequest() {
        // Empty paymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .build()
                .getNetworkDetails()).isEmpty();

        // Empty paymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder().build())
                .build()
                .getNetworkDetails()).isEmpty();

        // Invalid network in paymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .network("invalid")
                        .build())
                .build()
                .getNetworkDetails()).isEmpty();

        // The network is present.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .network("BASE")
                        .build())
                .build()
                .getNetworkDetails())
                .isPresent()
                .get()
                .extracting("chainId")
                .isEqualTo(8453);
    }

    @Test
    @DisplayName("Get network on /settle request")
    public void testGetNetworkOnSettleRequest() {
        // Empty verifyRequest.
        assertThat(SettleRequest.builder()
                .build()
                .getNetworkDetails()).isEmpty();

        // Empty paymentPayload in SettleRequest.
        assertThat(SettleRequest.builder()
                .paymentPayload(PaymentPayload.builder().build())
                .build()
                .getNetworkDetails()).isEmpty();

        // Invalid network in paymentPayload in SettleRequest.
        assertThat(SettleRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .network("invalid")
                        .build())
                .build()
                .getNetworkDetails()).isEmpty();

        // The network is present.
        assertThat(SettleRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .network("Base-SePolia")
                        .build())
                .build()
                .getNetworkDetails())
                .isPresent()
                .get()
                .extracting("name")
                .isEqualTo("base-sepolia");
    }

}
