package tech.mogami.commons.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.facilitator.verify.VerificationResponse;
import tech.mogami.commons.payment.PaymentPayload;
import tech.mogami.commons.util.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("JSON Util Tests")
public class JsonUtilTest {

    @Test
    @DisplayName("isValidJson()")
    public void testIsValidJson() {
        var json = """
                {
                  "isValid": false,
                  "invalidReason": "invalid_payload",
                  "payer": "0xf6b42050A71Ca13f842eDa53C7d31B7C1BD94F6E"
                }
                """;
        assertFalse(JsonUtil.isValidJson(json, PaymentPayload.class));
        assertTrue(JsonUtil.isValidJson(json, VerificationResponse.class));
    }

}
