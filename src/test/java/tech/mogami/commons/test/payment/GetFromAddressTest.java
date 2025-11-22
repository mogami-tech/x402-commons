package tech.mogami.commons.test.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.facilitator.verify.VerifyRequest;
import tech.mogami.commons.payment.PaymentPayload;
import tech.mogami.commons.payment.schemes.exact.ExactSchemePayload;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.mogami.commons.payment.schemes.Schemes.EXACT_SCHEME;

@DisplayName("Get from address test")
public class GetFromAddressTest {

    @Test
    @DisplayName("Get from address on /verify request")
    public void testGetFromAddressOnVerifyRequest() {
        // Empty paymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .build()
                .getFromAddress()).isEmpty();

        // Empty payload in PaymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder().build())
                .build()
                .getFromAddress()).isEmpty();

        // Empty authorization in payload in PaymentPayload in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .payload(ExactSchemePayload.builder()
                                .authorization(ExactSchemePayload.Authorization.builder().build()))
                        .build())
                .build()
                .getFromAddress()).isEmpty();

        // From address is here.
        assertThat(VerifyRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .scheme(EXACT_SCHEME.name())
                        .payload(ExactSchemePayload.builder()
                                .authorization(ExactSchemePayload.Authorization.builder()
                                        .from("0x2980bc24bBFB34DE1BBC91479Cb712ffbCE02F73")
                                        .build())
                                .build())
                        .build())
                .build()
                .getFromAddress())
                .isPresent()
                .get()
                .isEqualTo("0x2980bc24bBFB34DE1BBC91479Cb712ffbCE02F73");
    }

}
