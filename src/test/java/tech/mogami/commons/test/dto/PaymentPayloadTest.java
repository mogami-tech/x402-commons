package tech.mogami.commons.test.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.payment.PaymentPayload;
import tech.mogami.commons.api.payment.PaymentRequirements;
import tech.mogami.commons.api.payment.schemes.exact.ExactSchemePayload;
import tech.mogami.commons.test.BaseMogamiTest;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SpellCheckingInspection")
@DisplayName("PaymentPayload helper method tests")
public class PaymentPayloadTest extends BaseMogamiTest {

    private static final String VALID_SIGNATURE = "0x2d6a7588d6acca505cbf0d9a4a227e0c52c6c34008c8e8986a1283259764173608a2ce6496642e377d6da8dbbf5836e9bd15092f9ecab05ded3d6293af148b571c";
    private static final String VALID_FROM = "0x857b06519E91e3A54538791bDbb0E22373e36b66";
    private static final String VALID_TO = "0x209693Bc6afc0C5328bA36FaF03C514EF312287C";
    private static final String VALID_VALUE = "10000";
    private static final String VALID_VALID_AFTER = "1740672089";
    private static final String VALID_VALID_BEFORE = "1740672154";
    private static final String VALID_NONCE = "0xf3746613c2d920b5fdabc0856f2aeb2d4f88ee6037b8cc5d04a71a4462f13480";

    // =========================================================================
    // getNonce()
    // =========================================================================

    @Test
    @DisplayName("getNonce() should delegate to the typed payload via SchemePayload interface")
    void getNonce() {
        assertThat(getSamplePaymentPayload().getNonce()).hasValue(VALID_NONCE);
    }

    @Test
    @DisplayName("getNonce() should return empty when payload is null")
    void getNonceWithNullPayload() {
        PaymentPayload payload = getSamplePaymentPayload().toBuilder().payload(null).build();
        assertThat(payload.getNonce()).isEmpty();
    }

    @Test
    @DisplayName("getNonce() should return empty when accepted is null")
    void getNonceWithNullAccepted() {
        PaymentPayload payload = getSamplePaymentPayload().toBuilder().accepted(null).build();
        assertThat(payload.getNonce()).isEmpty();
    }

    @Test
    @DisplayName("getNonce() should return empty when scheme is unknown")
    void getNonceWithUnknownScheme() {
        PaymentRequirements unknownScheme = getSamplePaymentPayload().accepted().toBuilder().scheme("unknown-scheme").build();
        PaymentPayload payload = getSamplePaymentPayload().toBuilder().accepted(unknownScheme).build();
        assertThat(payload.getNonce()).isEmpty();
    }

    // =========================================================================
    // getFromAddress()
    // =========================================================================

    @Test
    @DisplayName("getFromAddress() should delegate to the typed payload via SchemePayload interface")
    void getFromAddress() {
        assertThat(getSamplePaymentPayload().getFromAddress()).hasValue(VALID_FROM);
    }

    @Test
    @DisplayName("getFromAddress() should return empty when payload is null")
    void getFromAddressWithNullPayload() {
        PaymentPayload payload = getSamplePaymentPayload().toBuilder().payload(null).build();
        assertThat(payload.getFromAddress()).isEmpty();
    }

    // =========================================================================
    // getToAddress()
    // =========================================================================

    @Test
    @DisplayName("getToAddress() should delegate to the typed payload via SchemePayload interface")
    void getToAddress() {
        assertThat(getSamplePaymentPayload().getToAddress()).hasValue(VALID_TO);
    }

    @Test
    @DisplayName("getToAddress() should return empty when payload is null")
    void getToAddressWithNullPayload() {
        PaymentPayload payload = getSamplePaymentPayload().toBuilder().payload(null).build();
        assertThat(payload.getToAddress()).isEmpty();
    }

    // =========================================================================
    // getAmount()
    // =========================================================================

    @Test
    @DisplayName("getAmount() should delegate to the typed payload via SchemePayload interface")
    void getAmount() {
        assertThat(getSamplePaymentPayload().getAmount()).hasValue(new BigInteger(VALID_VALUE));
    }

    @Test
    @DisplayName("getAmount() should return empty when payload is null")
    void getAmountWithNullPayload() {
        PaymentPayload payload = getSamplePaymentPayload().toBuilder().payload(null).build();
        assertThat(payload.getAmount()).isEmpty();
    }

    @Test
    @DisplayName("getAmount() should return empty when inner value is not a number")
    void getAmountWithInvalidValue() {
        ExactSchemePayload exactSchemePayload = ExactSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .authorization(ExactSchemePayload.Authorization.builder()
                        .from(VALID_FROM)
                        .to(VALID_TO)
                        .value("not-a-number")
                        .validAfter(VALID_VALID_AFTER)
                        .validBefore(VALID_VALID_BEFORE)
                        .nonce(VALID_NONCE)
                        .build())
                .build();
        PaymentPayload payload = getSamplePaymentPayload().toBuilder().payload(exactSchemePayload).build();
        assertThat(payload.getAmount()).isEmpty();
    }

}
