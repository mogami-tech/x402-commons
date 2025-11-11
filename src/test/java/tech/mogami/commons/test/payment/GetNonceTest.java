package tech.mogami.commons.test.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.facilitator.verify.VerifyRequest;
import tech.mogami.commons.payment.PaymentPayload;
import tech.mogami.commons.payment.schemes.exact.ExactSchemePayload;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.mogami.commons.payment.schemes.Schemes.EXACT_SCHEME;

@DisplayName("Get nonce test")
public class GetNonceTest {

    @Test
    @DisplayName("Get nonce on /verify request")
    public void testGetNonceOnVerifyRequest() {
        // Empty paymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .build()
                .getNonce()).isEmpty();

        // Empty payload in PaymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .build())
                .build()
                .getNonce()).isEmpty();

        // Invalid scheme in PaymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .scheme("invalid")
                        .payload(ExactSchemePayload.builder()
                                .build())
                        .build())
                .build()
                .getNonce()).isEmpty();

        // Empty authorization in payload in PaymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .scheme(EXACT_SCHEME.name())
                        .payload(ExactSchemePayload.builder()
                                .build())
                        .build())
                .build()
                .getNonce()).isEmpty();

        // All is here but nonce is blank in authorization in payload in PaymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .scheme(EXACT_SCHEME.name())
                        .payload(ExactSchemePayload.builder()
                                .authorization(ExactSchemePayload.Authorization.builder()
                                        .nonce(" ")
                                        .build())
                                .build())
                        .build())
                .build()
                .getNonce()).isEmpty();

        // All is here and nonce is valid in authorization in payload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .scheme(EXACT_SCHEME.name())
                        .payload(ExactSchemePayload.builder()
                                .authorization(ExactSchemePayload.Authorization.builder()
                                        .nonce(" unique-nonce-123 ")
                                        .build())
                                .build())
                        .build())
                .build()
                .getNonce()).hasValue("unique-nonce-123");
    }

    @Test
    @DisplayName("Get nonce on /settle request")
    public void testGetNonceOnSettleRequest() {
        // Empty paymentPayload in SettleRequest.
        assertThat(tech.mogami.commons.api.facilitator.settle.SettleRequest.builder()
                .build()
                .getNonce()).isEmpty();

        // Empty payload in PaymentPayload in SettleRequest.
        assertThat(tech.mogami.commons.api.facilitator.settle.SettleRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .build())
                .build()
                .getNonce()).isEmpty();

        // Invalid scheme in PaymentPayload in SettleRequest.
        assertThat(tech.mogami.commons.api.facilitator.settle.SettleRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .scheme("invalid")
                        .payload(ExactSchemePayload.builder()
                                .build())
                        .build())
                .build()
                .getNonce()).isEmpty();

        // Empty authorization in payload in PaymentPayload in SettleRequest.
        assertThat(tech.mogami.commons.api.facilitator.settle.SettleRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .scheme(EXACT_SCHEME.name())
                        .payload(ExactSchemePayload.builder()
                                .build())
                        .build())
                .build()
                .getNonce()).isEmpty();

        // All is here but nonce is blank in authorization in payload in PaymentPayload in SettleRequest.
        assertThat(tech.mogami.commons.api.facilitator.settle.SettleRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .scheme(EXACT_SCHEME.name())
                        .payload(ExactSchemePayload.builder()
                                .authorization(ExactSchemePayload.Authorization.builder()
                                        .nonce(" ")
                                        .build())
                                .build())
                        .build())
                .build()
                .getNonce()).isEmpty();

        // All is here and nonce is valid in authorization in payload in SettleRequest.
        assertThat(tech.mogami.commons.api.facilitator.settle.SettleRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .scheme(EXACT_SCHEME.name())
                        .payload(ExactSchemePayload.builder()
                                .authorization(ExactSchemePayload.Authorization.builder()
                                        .nonce(" unique-nonce-123 ")
                                        .build())
                                .build())
                        .build())
                .build()
                .getNonce()).hasValue("unique-nonce-123");
    }

}
