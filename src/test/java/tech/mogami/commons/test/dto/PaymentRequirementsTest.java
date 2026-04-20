package tech.mogami.commons.test.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.payment.PaymentRequirements;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SpellCheckingInspection")
@DisplayName("PaymentRequired tests")
public class PaymentRequirementsTest {

    @Test
    @DisplayName("isCompatibleWith() when payment satisfies requirements")
    void ValidRequirementsForIsCompatibleWith() {
        PaymentRequirements required = PaymentRequirements.builder()
                .scheme("exact")
                .network("eip155:84532")
                .amount("1500")
                .asset("0xABCDEF")
                .payTo("0xPAYEE")
                .maxTimeoutSeconds(60)
                .extra(Map.of("name", "USDC", "version", "2"))
                .build();

        PaymentRequirements paid = PaymentRequirements.builder()
                .scheme("exact")
                .network("eip155:84532")
                .amount("1500") // more than required
                .asset("0xabcdef") // case-insensitive
                .payTo("0xpayee") // case-insensitive
                .maxTimeoutSeconds(30) // shorter timeout
                .extra(Map.of("name", "USDC", "version", "2", "foo", "bar"))
                .build();

        assertThat(required.isCompatibleWith(paid)).isTrue();
    }

    @Test
    @DisplayName("isCompatibleWith() when amount is too low")
    void invalidAmountForIsCompatibleWith() {
        PaymentRequirements required = PaymentRequirements.builder()
                .scheme("exact")
                .network("eip155:84532")
                .amount("1000")
                .asset("0xABCDEF")
                .payTo("0xPAYEE")
                .maxTimeoutSeconds(60)
                .build();

        PaymentRequirements paid = PaymentRequirements.builder()
                .scheme("exact")
                .network("eip155:84532")
                .amount("999")
                .asset("0xABCDEF")
                .payTo("0xPAYEE")
                .maxTimeoutSeconds(60)
                .build();

        assertThat(required.isCompatibleWith(paid)).isFalse();
    }

    @Test
    @DisplayName("isCompatibleWith() when other is null")
    void nullValueForIsCompatibleWith() {
        PaymentRequirements required = PaymentRequirements.builder()
                .scheme("exact")
                .network("eip155:84532")
                .amount("1000")
                .asset("0xABCDEF")
                .payTo("0xPAYEE")
                .maxTimeoutSeconds(60)
                .build();

        assertThat(required.isCompatibleWith(null)).isFalse();
    }

    @Test
    @DisplayName("isCompatibleWith() when extra is missing")
    void extraMissingForIsCompatibleWith() {
        PaymentRequirements required = PaymentRequirements.builder()
                .scheme("exact")
                .network("eip155:84532")
                .amount("1000")
                .asset("0xABCDEF")
                .payTo("0xPAYEE")
                .maxTimeoutSeconds(60)
                .extra(Map.of("version", "2"))
                .build();

        PaymentRequirements paid = PaymentRequirements.builder()
                .scheme("exact")
                .network("eip155:84532")
                .amount("2000")
                .asset("0xABCDEF")
                .payTo("0xPAYEE")
                .maxTimeoutSeconds(30)
                .build();

        assertThat(required.isCompatibleWith(paid)).isFalse();
    }

}
