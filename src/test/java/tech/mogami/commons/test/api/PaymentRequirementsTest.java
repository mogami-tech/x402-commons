package tech.mogami.commons.test.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.payment.PaymentRequirements;
import tech.mogami.commons.util.ValidationUtil;

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

        // Exact amount, shorter timeout, case-insensitive fields, extra superset.
        PaymentRequirements paid = PaymentRequirements.builder()
                .scheme("exact")
                .network("eip155:84532")
                .amount("1500")
                .asset("0xabcdef") // case-insensitive
                .payTo("0xpayee") // case-insensitive
                .maxTimeoutSeconds(30) // shorter timeout
                .extra(Map.of("name", "USDC", "version", "2", "foo", "bar"))
                .build();

        assertThat(required.isCompatibleWith(paid)).isTrue();
    }

    @Test
    @DisplayName("isCompatibleWith() when paid amount is greater than required")
    void higherAmountForIsCompatibleWith() {
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
                .amount("2000")
                .asset("0xABCDEF")
                .payTo("0xPAYEE")
                .maxTimeoutSeconds(60)
                .build();

        assertThat(required.isCompatibleWith(paid)).isTrue();
    }

    @Test
    @DisplayName("isCompatibleWith() when timeout exceeds maxTimeoutSeconds")
    void timeoutExceededForIsCompatibleWith() {
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
                .amount("1000")
                .asset("0xABCDEF")
                .payTo("0xPAYEE")
                .maxTimeoutSeconds(120)
                .build();

        assertThat(required.isCompatibleWith(paid)).isFalse();
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

    // =========================================================================
    // asset and payTo — accept non-EVM formats per spec
    // =========================================================================

    @Test
    @DisplayName("Solana address in asset should pass validation")
    void solanaAddressAssetPassesValidation() {
        var requirements = PaymentRequirements.builder()
                .scheme("exact")
                .network("solana:EtWTRABZaYq6iMfeYKouRu166VU2xqa1")
                .amount("10000")
                .asset("4zMMC9srt5Ri5X14GAgXhaHii3GnPAEERYPJgZJDncDU")
                .payTo("CKPKJWNdJEqa81x7CkZ14BVPiY6y16Sxs7owznqtWYp5")
                .maxTimeoutSeconds(60)
                .build();
        assertThat(ValidationUtil.findViolations(requirements)).isEmpty();
    }

    @Test
    @DisplayName("ISO 4217 currency code in asset should pass validation")
    void iso4217AssetPassesValidation() {
        var requirements = PaymentRequirements.builder()
                .scheme("exact")
                .network("eip155:84532")
                .amount("10000")
                .asset("USD")
                .payTo("merchant")
                .maxTimeoutSeconds(60)
                .build();
        assertThat(ValidationUtil.findViolations(requirements)).isEmpty();
    }

    @Test
    @DisplayName("Role constant in payTo should pass validation")
    void roleConstantPayToPassesValidation() {
        var requirements = PaymentRequirements.builder()
                .scheme("exact")
                .network("eip155:84532")
                .amount("10000")
                .asset("0x036CbD53842c5426634e7929541eC2318f3dCF7e")
                .payTo("merchant")
                .maxTimeoutSeconds(60)
                .build();
        assertThat(ValidationUtil.findViolations(requirements)).isEmpty();
    }

}
