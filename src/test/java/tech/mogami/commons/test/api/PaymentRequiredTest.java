package tech.mogami.commons.test.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.payment.PaymentRequired;
import tech.mogami.commons.api.payment.PaymentRequirements;
import tech.mogami.commons.api.payment.extensions.Extensions;
import tech.mogami.commons.api.payment.extensions.bazaar.BazaarExtension;
import tech.mogami.commons.api.payment.extensions.bazaar.BazaarHttpInput;
import tech.mogami.commons.api.payment.extensions.bazaar.BazaarInfo;
import tech.mogami.commons.constant.util.HttpMethod;
import tech.mogami.commons.exception.InvalidX402VersionException;
import tech.mogami.commons.util.JsonUtil;
import tech.mogami.commons.util.ValidationUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.mogami.commons.constant.x402.X402Versions.V2;

@DisplayName("PaymentRequired tests")
public class PaymentRequiredTest {

    @Test
    @DisplayName("getX402Version()")
    void getX402Version() {
        assertThatThrownBy(() -> PaymentRequired.builder()
                .build().getX402Version())
                .isInstanceOf(InvalidX402VersionException.class);

        assertThatThrownBy(() -> PaymentRequired.builder()
                .x402Version(null)
                .build().getX402Version())
                .isInstanceOf(InvalidX402VersionException.class);

        assertThatThrownBy(() -> PaymentRequired.builder()
                .x402Version(999)
                .build().getX402Version())
                .isInstanceOf(InvalidX402VersionException.class);

        assertThat(PaymentRequired.builder()
                .x402Version(2)
                .build().getX402Version()).isEqualTo(V2);
    }

    @Test
    @DisplayName("isSupportedVersion()")
    void isSupportedVersion() {
        assertThat(PaymentRequired.builder()
                .build().isSupportedVersion()).isFalse();

        assertThat(PaymentRequired.builder()
                .x402Version(null)
                .build().isSupportedVersion()).isFalse();

        assertThat(PaymentRequired.builder()
                .x402Version(1)
                .build().isSupportedVersion()).isFalse();

        assertThat(PaymentRequired.builder()
                .x402Version(2)
                .build().isSupportedVersion()).isTrue();
    }

    @Test
    @DisplayName("Validation should work")
    void validation() {
        // Null fields — both @NotNull and @NotEmpty fire on accepts.
        assertThat(ValidationUtil.findViolations(PaymentRequired.builder().build()))
                .hasSize(4)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactlyInAnyOrder(
                        "x402Version",
                        "resource",
                        "accepts",
                        "accepts"
                );

        // Empty accepts list.
        assertThat(ValidationUtil.findViolations(PaymentRequired.builder()
                .x402Version(2)
                .accepts(List.of())
                .build()))
                .extracting(v -> v.getPropertyPath().toString())
                .contains("accepts");

        // Invalid PaymentRequirements inside accepts — cascading validation.
        assertThat(ValidationUtil.findViolations(PaymentRequired.builder()
                .x402Version(2)
                .accepts(List.of(PaymentRequirements.builder().build()))
                .build()))
                .extracting(v -> v.getPropertyPath().toString())
                .anyMatch(p -> p.startsWith("accepts"));
    }

    @Test
    @DisplayName("extensions field is typed as Extensions and serialized correctly")
    void extensionsFieldIsTyped() {
        var schema = JsonUtil.fromJson("{\"type\":\"object\"}", com.fasterxml.jackson.databind.JsonNode.class);
        var bazaarExtension = BazaarExtension.builder()
                .info(BazaarInfo.builder()
                        .input(BazaarHttpInput.builder().method(HttpMethod.GET).build())
                        .build())
                .schema(schema)
                .build();
        var extensions = Extensions.builder().bazaar(bazaarExtension).build();
        var paymentRequired = PaymentRequired.builder()
                .x402Version(2)
                .extensions(extensions)
                .build();

        assertThat(paymentRequired.extensions()).isNotNull();
        assertThat(paymentRequired.extensions().bazaar()).isEqualTo(bazaarExtension);

        String json = JsonUtil.toJson(paymentRequired);
        assertThat(json).contains("\"extensions\"");
        assertThat(json).contains("\"bazaar\"");

        var deserialized = JsonUtil.fromJson(json, PaymentRequired.class);
        assertThat(deserialized.extensions()).isNotNull();
        assertThat(deserialized.extensions().bazaar()).isNotNull();
    }

    @Test
    @DisplayName("Null extensions in PaymentRequired should not produce violations")
    void nullExtensionsAreValid() {
        var paymentRequired = PaymentRequired.builder()
                .x402Version(2)
                .extensions(null)
                .build();
        assertThat(ValidationUtil.findViolations(paymentRequired))
                .extracting(v -> v.getPropertyPath().toString())
                .doesNotContain("extensions");
    }

}
