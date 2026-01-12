package tech.mogami.commons.test.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.payment.PaymentRequired;
import tech.mogami.commons.util.ValidationUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.mogami.commons.constant.version.X402Versions.V2;

@DisplayName("PaymentRequired tests")
public class PaymentRequiredTest {

    @Test
    @DisplayName("getVersion()")
    void getVersion() {
        assertThat(PaymentRequired.builder().build().getVersion())
                .isEmpty();
        assertThat(PaymentRequired.builder().x402Version(null).build().getVersion())
                .isEmpty();
        assertThat(PaymentRequired.builder().x402Version(2).build().getVersion())
                .isPresent()
                .get()
                .isEqualTo(V2);
    }

    @Test
    @DisplayName("isSupportedVersion()")
    void isSupportedVersion() {
        assertThat(PaymentRequired.builder().build().isSupportedVersion())
                .isFalse();
        assertThat(PaymentRequired.builder().x402Version(null).build().isSupportedVersion())
                .isFalse();
        assertThat(PaymentRequired.builder().x402Version(1).build().isSupportedVersion())
                .isFalse();
        assertThat(PaymentRequired.builder().x402Version(2).build().isSupportedVersion())
                .isTrue();
    }

    @Test
    @DisplayName("Validation")
    void validation() {
        assertThat(ValidationUtil.findViolations(PaymentRequired.builder().build()))
                .hasSize(3)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactlyInAnyOrder(
                        "x402Version",
                        "resource",
                        "accepts"
                );
    }

}
