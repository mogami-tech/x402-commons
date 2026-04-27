package tech.mogami.commons.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.payment.PaymentRequired;
import tech.mogami.commons.test.BaseMogamiTest;
import tech.mogami.commons.util.ValidationUtil;
import tech.mogami.commons.util.X402HeaderUtil;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Validation Util tests")
public class ValidationUtilTest extends BaseMogamiTest {

    @Test
    @DisplayName("findViolations() - returns violations for an invalid object")
    void findViolationsOnInvalidObject() {
        var violations = ValidationUtil.findViolations(PaymentRequired.builder().build());
        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("findViolations() - returns no violations for a valid object")
    void findViolationsOnValidObject() {
        var validPaymentRequired = X402HeaderUtil.decodePaymentRequired(getSampleEncodedPaymentRequired());
        var violations = ValidationUtil.findViolations(validPaymentRequired);
        assertThat(violations).isEmpty();
    }

}
