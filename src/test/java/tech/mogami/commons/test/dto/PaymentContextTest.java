package tech.mogami.commons.test.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.facilitator.PaymentContext;
import tech.mogami.commons.api.facilitator.verify.VerificationRequest;
import tech.mogami.commons.api.payment.PaymentPayload;
import tech.mogami.commons.api.payment.PaymentRequirements;
import tech.mogami.commons.api.payment.schemes.exact.ExactSchemePayload;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(p.getVersion()).isEmpty();

        // With payload but no version.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder().build())
                .build();
        assertThat(p.getVersion()).isEmpty();

        // With payload and invalid version.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder().x402Version(0).build())
                .build();
        assertThat(p.getVersion()).isEmpty();

        // With payload and valid version.
        p = VerificationRequest.builder()
                .paymentPayload(PaymentPayload.builder().x402Version(2).build())
                .build();
        assertThat(p.getVersion()).hasValue(V2);
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
        assertThat(p.getVersion()).isEmpty();
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
        assertThat(p.getVersion()).isEmpty();
        assertThat(p.getPaymentId()).isEmpty();
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
        assertThat(p.getVersion()).isEmpty();
        assertThat(p.getPaymentId()).isEmpty();
        assertThat(p.getFrom()).isEmpty();
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
        assertThat(p.getVersion()).isEmpty();
        assertThat(p.getPaymentId()).isEmpty();
        assertThat(p.getFrom()).isEmpty();
        assertThat(p.getTo()).isEmpty();
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
        assertThat(p.getVersion()).isEmpty();
        assertThat(p.getPaymentId()).isEmpty();
        assertThat(p.getFrom()).isEmpty();
        assertThat(p.getTo()).isEmpty();
        assertThat(p.getAssetAmount()).isEmpty();
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
        assertThat(p.getVersion()).isEmpty();
        assertThat(p.getPaymentId()).isEmpty();
        assertThat(p.getFrom()).isEmpty();
        assertThat(p.getTo()).isEmpty();
        assertThat(p.getAssetAmount()).isEmpty();
        assertThat(p.getAssetContract()).isEmpty();
        assertThat(p.getNetwork()).hasValue(BASE_SEPOLIA);
    }

}
