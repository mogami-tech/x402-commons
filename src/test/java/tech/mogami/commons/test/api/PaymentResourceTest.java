package tech.mogami.commons.test.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.payment.PaymentResource;
import tech.mogami.commons.util.ValidationUtil;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PaymentResource tests")
public class PaymentResourceTest {

    @Test
    @DisplayName("Validation should work")
    void validation() {
        // Null url.
        assertThat(ValidationUtil.findViolations(PaymentResource.builder().build()))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("url");

        // Invalid url.
        assertThat(ValidationUtil.findViolations(PaymentResource.builder()
                .url("not-a-url")
                .build()))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("url");

        // Valid url.
        assertThat(ValidationUtil.findViolations(PaymentResource.builder()
                .url("https://api.example.com/premium-data")
                .build()))
                .isEmpty();
    }

}
