package tech.mogami.commons.test.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.facilitator.settle.SettleRequest;
import tech.mogami.commons.api.facilitator.verify.VerifyRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Get asset contract test")
public class GetAssetContractTest {

    @Test
    @DisplayName("Get asset contract on /verify request")
    public void testGetAssetContractOnVerifyRequest() {
        // Empty paymentRequirements in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .build()
                .getAssetContract()).isEmpty();

        // Empty asset contract in paymentRequirements in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .paymentRequirements(tech.mogami.commons.payment.PaymentRequirements.builder()
                        .build())
                .build()
                .getAssetContract()).isEmpty();

        // Valid asset contract in paymentRequirements in VerifyRequest.
        assertThat(VerifyRequest.builder()
                .paymentRequirements(tech.mogami.commons.payment.PaymentRequirements.builder()
                        .asset("0x1234567890abcdef")
                        .build())
                .build()
                .getAssetContract())
                .isPresent()
                .hasValue("0x1234567890abcdef");
    }

    @Test
    @DisplayName("Get asset contract on /settle request")
    public void testGetAssetContractOnSettleRequest() {
        // Empty paymentRequirements in SettleRequest.
        assertThat(SettleRequest.builder()
                .build()
                .getAssetContract()).isEmpty();

        // Empty asset contract in paymentRequirements in SettleRequest.
        assertThat(SettleRequest.builder()
                .paymentRequirements(tech.mogami.commons.payment.PaymentRequirements.builder()
                        .build())
                .build()
                .getAssetContract()).isEmpty();

        // Valid asset contract in paymentRequirements in SettleRequest.
        assertThat(SettleRequest.builder()
                .paymentRequirements(tech.mogami.commons.payment.PaymentRequirements.builder()
                        .asset("0xabcdef1234567890")
                        .build())
                .build()
                .getAssetContract())
                .isPresent()
                .hasValue("0xabcdef1234567890");
    }

}
