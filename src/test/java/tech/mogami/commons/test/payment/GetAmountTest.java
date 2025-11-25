package tech.mogami.commons.test.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.facilitator.settle.SettleRequest;
import tech.mogami.commons.api.facilitator.verify.VerifyRequest;
import tech.mogami.commons.payment.PaymentPayload;
import tech.mogami.commons.payment.schemes.exact.ExactSchemePayload;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Get amount test")
public class GetAmountTest {

    @Test
    @DisplayName("Get amount on /verify request")
    public void testGetAmountOnVerifyRequest() {
        // Empty paymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .build()
                .getAmount()).isEmpty();

        // Empty payload in PaymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .build())
                .build()
                .getAmount()).isEmpty();

        // Empty authorization in payload in PaymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .payload(ExactSchemePayload.builder()
                                .authorization(ExactSchemePayload.Authorization.builder().build()))
                        .build())
                .build()
                .getAmount()).isEmpty();

        // The amount is present.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .payload(ExactSchemePayload.builder()
                                .authorization(ExactSchemePayload.Authorization.builder()
                                        .value("12345")
                                        .build())
                                .build())
                        .build())
                .build()
                .getAmount())
                .isPresent()
                .get().isEqualTo(BigInteger.valueOf(12345L));
    }

    @Test
    @DisplayName("Get amount on /settle request")
    public void testGetAmountOnSettleRequest() {
        // Empty paymentPayload in SettleRequest.
        assertThat(SettleRequest.builder()
                .build()
                .getAmount()).isEmpty();

        // Empty payload in PaymentPayload in SettleRequest.
        assertThat(SettleRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .build())
                .build()
                .getAmount()).isEmpty();

        // Empty authorization in payload in PaymentPayload in SettleRequest.
        assertThat(SettleRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .payload(ExactSchemePayload.builder()
                                .authorization(ExactSchemePayload.Authorization.builder().build()))
                        .build())
                .build()
                .getAmount()).isEmpty();

        // The amount is present.
        assertThat(SettleRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .payload(ExactSchemePayload.builder()
                                .authorization(ExactSchemePayload.Authorization.builder()
                                        .value("67890")
                                        .build())
                                .build())
                        .build())
                .build()
                .getAmount())
                .isPresent()
                .get().isEqualTo(BigInteger.valueOf(67890L));
    }

}
