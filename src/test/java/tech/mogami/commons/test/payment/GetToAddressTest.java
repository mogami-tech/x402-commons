package tech.mogami.commons.test.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.facilitator.verify.VerifyRequest;
import tech.mogami.commons.payment.PaymentPayload;
import tech.mogami.commons.payment.schemes.exact.ExactSchemePayload;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.mogami.commons.payment.schemes.Schemes.EXACT_SCHEME;

@DisplayName("Get to address test")
public class GetToAddressTest {

    @Test
    @DisplayName("Get to address on /verify request")
    public void testGetToAddressOnVerifyRequest() {
        // Empty paymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .build()
                .getToAddress()).isEmpty();

        // Empty payload in PaymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder().build())
                .build()
                .getToAddress()).isEmpty();

        // Empty authorization in payload in PaymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .payload(ExactSchemePayload.builder()
                                .authorization(ExactSchemePayload.Authorization.builder().build()))
                        .build())
                .build()
                .getToAddress()).isEmpty();

        // To address is here.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .scheme(EXACT_SCHEME.name())
                        .payload(ExactSchemePayload.builder()
                                .authorization(ExactSchemePayload.Authorization.builder()
                                        .to("0x2980bc24bBFB34DE1BBC91479Cb712ffbCE02F73")
                                        .build())
                                .build())
                        .build())
                .build()
                .getToAddress())
                .isPresent()
                .get()
                .isEqualTo("0x2980bc24bBFB34DE1BBC91479Cb712ffbCE02F73");
    }

}
