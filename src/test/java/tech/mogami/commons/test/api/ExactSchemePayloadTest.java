package tech.mogami.commons.test.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.payment.schemes.exact.ExactSchemePayload;
import tech.mogami.commons.util.ValidationUtil;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SpellCheckingInspection")
@DisplayName("ExactSchemePayload tests")
public class ExactSchemePayloadTest {

    private static final String VALID_SIGNATURE = "0x2d6a7588d6acca505cbf0d9a4a227e0c52c6c34008c8e8986a1283259764173608a2ce6496642e377d6da8dbbf5836e9bd15092f9ecab05ded3d6293af148b571c";
    private static final String VALID_FROM = "0x857b06519E91e3A54538791bDbb0E22373e36b66";
    private static final String VALID_TO = "0x209693Bc6afc0C5328bA36FaF03C514EF312287C";
    private static final String VALID_VALUE = "10000";
    private static final String VALID_VALID_AFTER = "1740672089";
    private static final String VALID_VALID_BEFORE = "1740672154";
    private static final String VALID_NONCE = "0xf3746613c2d920b5fdabc0856f2aeb2d4f88ee6037b8cc5d04a71a4462f13480";

    private ExactSchemePayload.Authorization validAuthorization() {
        return ExactSchemePayload.Authorization.builder()
                .from(VALID_FROM)
                .to(VALID_TO)
                .value(VALID_VALUE)
                .validAfter(VALID_VALID_AFTER)
                .validBefore(VALID_VALID_BEFORE)
                .nonce(VALID_NONCE)
                .build();
    }

    private ExactSchemePayload validPayload() {
        return ExactSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .authorization(validAuthorization())
                .build();
    }

    // =========================================================================
    // Validation - ExactSchemePayload
    // =========================================================================

    @Test
    @DisplayName("Valid payload should have no violations")
    void validPayloadHasNoViolations() {
        assertThat(ValidationUtil.findViolations(validPayload())).isEmpty();
    }

    @Test
    @DisplayName("Null signature should fail validation")
    void nullSignature() {
        var payload = ExactSchemePayload.builder()
                .signature(null)
                .authorization(validAuthorization())
                .build();
        assertThat(ValidationUtil.findViolations(payload))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("signature");
    }

    @Test
    @DisplayName("Blank signature should fail validation")
    void blankSignature() {
        var payload = ExactSchemePayload.builder()
                .signature("   ")
                .authorization(validAuthorization())
                .build();
        assertThat(ValidationUtil.findViolations(payload))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("signature");
    }

    @Test
    @DisplayName("Invalid signature format should fail validation")
    void invalidSignatureFormat() {
        var payload = ExactSchemePayload.builder()
                .signature("not-a-signature")
                .authorization(validAuthorization())
                .build();
        assertThat(ValidationUtil.findViolations(payload))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("signature");
    }

    @Test
    @DisplayName("Null authorization should fail validation")
    void nullAuthorization() {
        var payload = ExactSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .authorization(null)
                .build();
        assertThat(ValidationUtil.findViolations(payload))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("authorization");
    }

    // =========================================================================
    // Validation - Authorization (cascaded)
    // =========================================================================

    @Test
    @DisplayName("Null from address should fail cascaded validation")
    void nullFromAddress() {
        var auth = ExactSchemePayload.Authorization.builder()
                .from(null)
                .to(VALID_TO)
                .value(VALID_VALUE)
                .validAfter(VALID_VALID_AFTER)
                .validBefore(VALID_VALID_BEFORE)
                .nonce(VALID_NONCE)
                .build();
        assertThat(ValidationUtil.findViolations(ExactSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .authorization(auth)
                .build()))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("authorization.from");
    }

    @Test
    @DisplayName("Invalid from address should fail cascaded validation")
    void invalidFromAddress() {
        var auth = ExactSchemePayload.Authorization.builder()
                .from("not-an-address")
                .to(VALID_TO)
                .value(VALID_VALUE)
                .validAfter(VALID_VALID_AFTER)
                .validBefore(VALID_VALID_BEFORE)
                .nonce(VALID_NONCE)
                .build();
        assertThat(ValidationUtil.findViolations(ExactSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .authorization(auth)
                .build()))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("authorization.from");
    }

    @Test
    @DisplayName("Invalid value should fail cascaded validation")
    void invalidValue() {
        var auth = ExactSchemePayload.Authorization.builder()
                .from(VALID_FROM)
                .to(VALID_TO)
                .value("not-a-number")
                .validAfter(VALID_VALID_AFTER)
                .validBefore(VALID_VALID_BEFORE)
                .nonce(VALID_NONCE)
                .build();
        assertThat(ValidationUtil.findViolations(ExactSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .authorization(auth)
                .build()))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("authorization.value");
    }

    @Test
    @DisplayName("Invalid validAfter timestamp should fail cascaded validation")
    void invalidValidAfter() {
        var auth = ExactSchemePayload.Authorization.builder()
                .from(VALID_FROM)
                .to(VALID_TO)
                .value(VALID_VALUE)
                .validAfter("not-a-timestamp")
                .validBefore(VALID_VALID_BEFORE)
                .nonce(VALID_NONCE)
                .build();
        assertThat(ValidationUtil.findViolations(ExactSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .authorization(auth)
                .build()))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("authorization.validAfter");
    }

    @Test
    @DisplayName("Invalid validBefore timestamp should fail cascaded validation")
    void invalidValidBefore() {
        var auth = ExactSchemePayload.Authorization.builder()
                .from(VALID_FROM)
                .to(VALID_TO)
                .value(VALID_VALUE)
                .validAfter(VALID_VALID_AFTER)
                .validBefore("not-a-timestamp")
                .nonce(VALID_NONCE)
                .build();
        assertThat(ValidationUtil.findViolations(ExactSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .authorization(auth)
                .build()))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("authorization.validBefore");
    }

    // =========================================================================
    // Helper methods
    // =========================================================================

    @Test
    @DisplayName("getNonce() should return nonce from authorization")
    void getNonce() {
        assertThat(validPayload().getNonce()).hasValue(VALID_NONCE);
    }

    @Test
    @DisplayName("getNonce() should return empty when authorization is null")
    void getNonceWithNullAuthorization() {
        var payload = ExactSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .authorization(null)
                .build();
        assertThat(payload.getNonce()).isEmpty();
    }

    @Test
    @DisplayName("getFromAddress() should return from address from authorization")
    void getFromAddress() {
        assertThat(validPayload().getFromAddress()).hasValue(VALID_FROM);
    }

    @Test
    @DisplayName("getToAddress() should return to address from authorization")
    void getToAddress() {
        assertThat(validPayload().getToAddress()).hasValue(VALID_TO);
    }

    @Test
    @DisplayName("getAmount() should return parsed BigInteger value")
    void getAmount() {
        assertThat(validPayload().getAmount()).hasValue(new BigInteger("10000"));
    }

    @Test
    @DisplayName("getAmount() should return empty when value is not a number")
    void getAmountWithInvalidValue() {
        var auth = ExactSchemePayload.Authorization.builder()
                .from(VALID_FROM)
                .to(VALID_TO)
                .value("not-a-number")
                .validAfter(VALID_VALID_AFTER)
                .validBefore(VALID_VALID_BEFORE)
                .nonce(VALID_NONCE)
                .build();
        assertThat(ExactSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .authorization(auth)
                .build()
                .getAmount())
                .isEmpty();
    }

    @Test
    @DisplayName("getAmount() should return empty when authorization is null")
    void getAmountWithNullAuthorization() {
        var payload = ExactSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .authorization(null)
                .build();
        assertThat(payload.getAmount()).isEmpty();
    }

}
