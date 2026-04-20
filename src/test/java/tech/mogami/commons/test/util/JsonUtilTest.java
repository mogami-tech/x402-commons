package tech.mogami.commons.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.facilitator.verify.VerificationResponse;
import tech.mogami.commons.payment.PaymentPayload;
import tech.mogami.commons.util.JsonUtil;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JSON Util tests")
public class JsonUtilTest {

    @Test
    @DisplayName("isValidJson()")
    public void isValidJson() {
        var json = """
                {
                  "isValid": false,
                  "invalidReason": "invalid_payload",
                  "payer": "0xf6b42050A71Ca13f842eDa53C7d31B7C1BD94F6E"
                }
                """;
        assertThat(JsonUtil.isValidJson(json, PaymentPayload.class)).isFalse();
        assertThat(JsonUtil.isValidJson(json, VerificationResponse.class)).isTrue();
    }

}
