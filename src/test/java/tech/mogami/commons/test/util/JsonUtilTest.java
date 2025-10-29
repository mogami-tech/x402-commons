package tech.mogami.commons.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.facilitator.verify.VerifyResponse;
import tech.mogami.commons.header.payment.PaymentPayload;
import tech.mogami.commons.util.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tech.mogami.commons.constant.EventType.X402_FACILITATOR_VERIFY_RESPONSE;
import static tech.mogami.commons.constant.EventType.X402_SERVER_URL_ACCESS_REQUEST;

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
        assertFalse(JsonUtil.isValidJson(json, X402_SERVER_URL_ACCESS_REQUEST.getExpectedJsonType()));
        assertTrue(JsonUtil.isValidJson(json, VerifyResponse.class));
        assertTrue(JsonUtil.isValidJson(json, X402_FACILITATOR_VERIFY_RESPONSE.getExpectedJsonType()));
    }

}
