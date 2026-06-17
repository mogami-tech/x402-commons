package tech.mogami.commons.test.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.facilitator.PaymentContext;
import tech.mogami.commons.api.facilitator.verify.VerificationRequest;
import tech.mogami.commons.api.payment.PaymentPayload;
import tech.mogami.commons.api.payment.PaymentRequirements;
import tech.mogami.commons.api.payment.schemes.exact.ExactSchemePayload;
import tech.mogami.commons.api.payment.schemes.upto.UptoSchemePayload;
import tech.mogami.commons.exception.InvalidX402VersionException;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.mogami.commons.constant.network.Networks.BASE_SEPOLIA;
import static tech.mogami.commons.constant.x402.X402Versions.V2;

@DisplayName("PaymentContext interface tests")
public class PaymentContextTest {

    @Test
    @DisplayName("getVersion()")
    void getVersion() {
        // No payload.
        PaymentContext p = VerificationRequest.builder()
                .build();
        assertThatThrownBy(p::getVersion).isInstanceOf(InvalidX402VersionException.class);

        // With payload but no version.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder().build())
                .build();
        assertThatThrownBy(p::getVersion).isInstanceOf(InvalidX402VersionException.class);

        // With payload and invalid version.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder().x402Version(0).build())
                .build();
        assertThatThrownBy(p::getVersion).isInstanceOf(InvalidX402VersionException.class);

        // With payload and valid version.
        p = VerificationRequest.builder()
                .x402Version(2)
                .paymentPayload(PaymentPayload.builder().x402Version(2).build())
                .build();
        assertThat(p.getVersion()).isEqualTo(V2);
        assertThat(p.getPaymentId()).isEmpty();
        assertThat(p.getFrom()).isEmpty();
        assertThat(p.getTo()).isEmpty();
        assertThat(p.getAssetAmount()).isEmpty();
        assertThat(p.getAssetContract()).isEmpty();
        assertThat(p.getNetwork()).isEmpty();
    }

    @Test
    @DisplayName("getPaymentId()")
    void getPaymentId() {
        // No payload.
        PaymentContext p = VerificationRequest.builder()
                .build();
        assertThat(p.getPaymentId()).isEmpty();

        // With payload but no nonce.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder().build())
                .build();
        assertThat(p.getPaymentId()).isEmpty();

        // With payload and nonce.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .payload(ExactSchemePayload.builder()
                                .signature("0xsignature")
                                .authorization(ExactSchemePayload.Authorization.builder()
                                        .nonce("payment-id-123")
                                        .build())
                                .build())
                        .build())
                .build();
        assertThat(p.getPaymentId()).hasValue("payment-id-123");
        assertThat(p.getFrom()).isEmpty();
        assertThat(p.getTo()).isEmpty();
        assertThat(p.getAssetAmount()).isEmpty();
        assertThat(p.getAssetContract()).isEmpty();
        assertThat(p.getNetwork()).isEmpty();
    }

    @Test
    @DisplayName("getFrom()")
    void getFrom() {
        // No payload.
        PaymentContext p = VerificationRequest.builder()
                .build();
        assertThat(p.getFrom()).isEmpty();

        // With payload but no from address.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder().build())
                .build();
        assertThat(p.getFrom()).isEmpty();

        // With payload and from address.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .payload(ExactSchemePayload.builder()
                                .signature("0xsignature")
                                .authorization(ExactSchemePayload.Authorization.builder()
                                        .from("0xFromAddress")
                                        .build())
                                .build())
                        .build())
                .build();
        assertThat(p.getFrom()).hasValue("0xFromAddress");
        assertThat(p.getTo()).isEmpty();
        assertThat(p.getAssetAmount()).isEmpty();
        assertThat(p.getAssetContract()).isEmpty();
        assertThat(p.getNetwork()).isEmpty();
    }

    @Test
    @DisplayName("getTo()")
    void getTo() {
        // No payload.
        PaymentContext p = VerificationRequest.builder()
                .build();
        assertThat(p.getTo()).isEmpty();

        // With payload but no to address.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder().build())
                .build();
        assertThat(p.getTo()).isEmpty();

        // With payload and to address.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .payload(ExactSchemePayload.builder()
                                .signature("0xsignature")
                                .authorization(ExactSchemePayload.Authorization.builder()
                                        .to("0xToAddress")
                                        .build())
                                .build())
                        .build())
                .build();
        assertThat(p.getTo()).hasValue("0xToAddress");
        assertThat(p.getAssetAmount()).isEmpty();
        assertThat(p.getAssetContract()).isEmpty();
        assertThat(p.getNetwork()).isEmpty();
    }

    @Test
    @DisplayName("getAssetAmount()")
    void getAssetAmount() {
        // No payload.
        PaymentContext p = VerificationRequest.builder()
                .build();
        assertThat(p.getAssetAmount()).isEmpty();

        // With payload but no amount.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder().build())
                .build();
        assertThat(p.getAssetAmount()).isEmpty();

        // With payload and amount.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .payload(ExactSchemePayload.builder()
                                .signature("0xsignature")
                                .authorization(ExactSchemePayload.Authorization.builder()
                                        .value("1000")
                                        .build())
                                .build())
                        .build())
                .build();
        assertThat(p.getAssetAmount()).contains(BigInteger.valueOf(1000));
        assertThat(p.getAssetContract()).isEmpty();
        assertThat(p.getNetwork()).isEmpty();
    }

    @Test
    @DisplayName("getAssetContract()")
    void getAssetContract() {
        // No payload.
        PaymentContext p = VerificationRequest.builder()
                .build();
        assertThat(p.getAssetContract()).isEmpty();

        // With payload but no asset contract.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder().build())
                .build();
        assertThat(p.getAssetContract()).isEmpty();

        // With payload and asset contract.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .payload(ExactSchemePayload.builder()
                                .signature("0xsignature")
                                .authorization(ExactSchemePayload.Authorization.builder().build())
                                .build())
                        .build())
                .paymentRequirements(PaymentRequirements.builder()
                        .asset("0xAssetContract")
                        .build())
                .build();
        assertThat(p.getAssetContract()).hasValue("0xAssetContract");
        assertThat(p.getNetwork()).isEmpty();

        // With blank asset contract (should be treated as absent).
        p = VerificationRequest.builder()
                .paymentRequirements(PaymentRequirements.builder()
                        .asset("   ")
                        .build())
                .build();
        assertThat(p.getAssetContract()).isEmpty();
    }

    @Test
    @DisplayName("getNetwork()")
    void getNetwork() {
        // No payload.
        PaymentContext p = VerificationRequest.builder()
                .build();
        assertThat(p.getNetwork()).isEmpty();

        // With payload but no network.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .build())
                .build();
        assertThat(p.getNetwork()).isEmpty();

        // With payload and network.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .payload(ExactSchemePayload.builder()
                                .signature("0xsignature")
                                .authorization(ExactSchemePayload.Authorization.builder().build())
                                .build())
                        .build())
                .paymentRequirements(PaymentRequirements.builder()
                        .network(BASE_SEPOLIA.networkId())
                        .build())
                .build();
        assertThat(p.getNetwork()).hasValue(BASE_SEPOLIA);
    }

    @Test
    @DisplayName("helpers should extract values from upto payload")
    void getValuesFromUptoPayload() {
        PaymentContext context = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder()
                        .accepted(PaymentRequirements.builder()
                                .scheme("upto")
                                .build())
                        .payload(UptoSchemePayload.builder()
                                .signature("0x2d6a7588d6acca505cbf0d9a4a227e0c52c6c34008c8e8986a1283259764173608a2ce6496642e377d6da8dbbf5836e9bd15092f9ecab05ded3d6293af148b571c")
                                .permit2Authorization(UptoSchemePayload.Permit2Authorization.builder()
                                        .permitted(UptoSchemePayload.Permitted.builder()
                                                .token("0x036CbD53842c5426634e7929541eC2318f3dCF7e")
                                                .amount("5000000")
                                                .build())
                                        .from("0x857b06519E91e3A54538791bDbb0E22373e36b66")
                                        .spender("0x4020A4f3b7b90ccA423B9fabCc0CE57C6C240002")
                                        .nonce("0xf3746613c2d920b5fdabc0856f2aeb2d4f88ee6037b8cc5d04a71a4462f13480")
                                        .deadline("1740672154")
                                        .witness(UptoSchemePayload.Witness.builder()
                                                .to("0x209693Bc6afc0C5328bA36FaF03C514EF312287C")
                                                .facilitator("0x4020A4f3b7b90ccA423B9fabCc0CE57C6C240002")
                                                .validAfter("1740672089")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        assertThat(context.getPaymentId()).hasValue("0xf3746613c2d920b5fdabc0856f2aeb2d4f88ee6037b8cc5d04a71a4462f13480");
        assertThat(context.getFrom()).hasValue("0x857b06519E91e3A54538791bDbb0E22373e36b66");
        assertThat(context.getTo()).hasValue("0x209693Bc6afc0C5328bA36FaF03C514EF312287C");
        assertThat(context.getAssetAmount()).contains(new BigInteger("5000000"));
    }

}
