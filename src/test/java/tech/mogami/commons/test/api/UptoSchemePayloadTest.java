package tech.mogami.commons.test.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.payment.schemes.upto.UptoSchemePayload;
import tech.mogami.commons.util.ValidationUtil;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SpellCheckingInspection")
@DisplayName("UptoSchemePayload tests")
public class UptoSchemePayloadTest {

    private static final String VALID_SIGNATURE = "0x2d6a7588d6acca505cbf0d9a4a227e0c52c6c34008c8e8986a1283259764173608a2ce6496642e377d6da8dbbf5836e9bd15092f9ecab05ded3d6293af148b571c";
    private static final String VALID_TOKEN = "0x036CbD53842c5426634e7929541eC2318f3dCF7e";
    private static final String VALID_AMOUNT = "5000000";
    private static final String VALID_FROM = "0x857b06519E91e3A54538791bDbb0E22373e36b66";
    private static final String VALID_SPENDER = "0x4020A4f3b7b90ccA423B9fabCc0CE57C6C240002";
    private static final String VALID_NONCE = "0xf3746613c2d920b5fdabc0856f2aeb2d4f88ee6037b8cc5d04a71a4462f13480";
    private static final String VALID_DEADLINE = "1740672154";
    private static final String VALID_TO = "0x209693Bc6afc0C5328bA36FaF03C514EF312287C";
    private static final String VALID_FACILITATOR = "0x4020A4f3b7b90ccA423B9fabCc0CE57C6C240002";
    private static final String VALID_VALID_AFTER = "1740672089";

    private UptoSchemePayload.Witness validWitness() {
        return UptoSchemePayload.Witness.builder()
                .to(VALID_TO)
                .facilitator(VALID_FACILITATOR)
                .validAfter(VALID_VALID_AFTER)
                .build();
    }

    private UptoSchemePayload.Permitted validPermitted() {
        return UptoSchemePayload.Permitted.builder()
                .token(VALID_TOKEN)
                .amount(VALID_AMOUNT)
                .build();
    }

    private UptoSchemePayload.Permit2Authorization validPermit2Authorization() {
        return UptoSchemePayload.Permit2Authorization.builder()
                .permitted(validPermitted())
                .from(VALID_FROM)
                .spender(VALID_SPENDER)
                .nonce(VALID_NONCE)
                .deadline(VALID_DEADLINE)
                .witness(validWitness())
                .build();
    }

    private UptoSchemePayload validPayload() {
        return UptoSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .permit2Authorization(validPermit2Authorization())
                .build();
    }

    // =========================================================================
    // Validation - UptoSchemePayload
    // =========================================================================

    @Test
    @DisplayName("Valid payload should have no violations")
    void validPayloadHasNoViolations() {
        assertThat(ValidationUtil.findViolations(validPayload())).isEmpty();
    }

    @Test
    @DisplayName("Null signature should fail validation")
    void nullSignature() {
        var payload = UptoSchemePayload.builder()
                .signature(null)
                .permit2Authorization(validPermit2Authorization())
                .build();
        assertThat(ValidationUtil.findViolations(payload))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("signature");
    }

    @Test
    @DisplayName("Invalid signature format should fail validation")
    void invalidSignatureFormat() {
        var payload = UptoSchemePayload.builder()
                .signature("not-a-signature")
                .permit2Authorization(validPermit2Authorization())
                .build();
        assertThat(ValidationUtil.findViolations(payload))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("signature");
    }

    @Test
    @DisplayName("Null permit2Authorization should fail validation")
    void nullPermit2Authorization() {
        var payload = UptoSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .permit2Authorization(null)
                .build();
        assertThat(ValidationUtil.findViolations(payload))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("permit2Authorization");
    }

    // =========================================================================
    // Validation - Permit2Authorization (cascaded)
    // =========================================================================

    @Test
    @DisplayName("Invalid from address should fail cascaded validation")
    void invalidFromAddress() {
        var auth = UptoSchemePayload.Permit2Authorization.builder()
                .permitted(validPermitted())
                .from("not-an-address")
                .spender(VALID_SPENDER)
                .nonce(VALID_NONCE)
                .deadline(VALID_DEADLINE)
                .witness(validWitness())
                .build();
        assertThat(ValidationUtil.findViolations(UptoSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .permit2Authorization(auth)
                .build()))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("permit2Authorization.from");
    }

    @Test
    @DisplayName("Invalid permitted amount should fail cascaded validation")
    void invalidPermittedAmount() {
        var auth = UptoSchemePayload.Permit2Authorization.builder()
                .permitted(UptoSchemePayload.Permitted.builder().token(VALID_TOKEN).amount("not-a-number").build())
                .from(VALID_FROM)
                .spender(VALID_SPENDER)
                .nonce(VALID_NONCE)
                .deadline(VALID_DEADLINE)
                .witness(validWitness())
                .build();
        assertThat(ValidationUtil.findViolations(UptoSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .permit2Authorization(auth)
                .build()))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("permit2Authorization.permitted.amount");
    }

    @Test
    @DisplayName("Invalid deadline timestamp should fail cascaded validation")
    void invalidDeadline() {
        var auth = UptoSchemePayload.Permit2Authorization.builder()
                .permitted(validPermitted())
                .from(VALID_FROM)
                .spender(VALID_SPENDER)
                .nonce(VALID_NONCE)
                .deadline("not-a-timestamp")
                .witness(validWitness())
                .build();
        assertThat(ValidationUtil.findViolations(UptoSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .permit2Authorization(auth)
                .build()))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("permit2Authorization.deadline");
    }

    @Test
    @DisplayName("Invalid witness validAfter should fail cascaded validation")
    void invalidWitnessValidAfter() {
        var auth = UptoSchemePayload.Permit2Authorization.builder()
                .permitted(validPermitted())
                .from(VALID_FROM)
                .spender(VALID_SPENDER)
                .nonce(VALID_NONCE)
                .deadline(VALID_DEADLINE)
                .witness(UptoSchemePayload.Witness.builder().to(VALID_TO).facilitator(VALID_FACILITATOR).validAfter("not-a-timestamp").build())
                .build();
        assertThat(ValidationUtil.findViolations(UptoSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .permit2Authorization(auth)
                .build()))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("permit2Authorization.witness.validAfter");
    }

    // =========================================================================
    // Helper methods
    // =========================================================================

    @Test
    @DisplayName("getNonce() should return nonce from permit2Authorization")
    void getNonce() {
        assertThat(validPayload().getNonce()).hasValue(VALID_NONCE);
    }

    @Test
    @DisplayName("getNonce() should return empty when permit2Authorization is null")
    void getNonceWithNullAuthorization() {
        var payload = UptoSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .permit2Authorization(null)
                .build();
        assertThat(payload.getNonce()).isEmpty();
    }

    @Test
    @DisplayName("getFromAddress() should return from address from permit2Authorization")
    void getFromAddress() {
        assertThat(validPayload().getFromAddress()).hasValue(VALID_FROM);
    }

    @Test
    @DisplayName("getToAddress() should return to address from witness")
    void getToAddress() {
        assertThat(validPayload().getToAddress()).hasValue(VALID_TO);
    }

    @Test
    @DisplayName("getAmount() should return parsed BigInteger value from permitted")
    void getAmount() {
        assertThat(validPayload().getAmount()).hasValue(new BigInteger("5000000"));
    }

    @Test
    @DisplayName("getAmount() should return empty when permit2Authorization is null")
    void getAmountWithNullAuthorization() {
        var payload = UptoSchemePayload.builder()
                .signature(VALID_SIGNATURE)
                .permit2Authorization(null)
                .build();
        assertThat(payload.getAmount()).isEmpty();
    }

}
