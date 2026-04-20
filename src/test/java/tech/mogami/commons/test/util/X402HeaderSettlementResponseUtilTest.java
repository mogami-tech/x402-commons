package tech.mogami.commons.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.facilitator.settle.SettlementResponse;
import tech.mogami.commons.exception.InvalidX402HeaderException;
import tech.mogami.commons.test.BaseMogamiTest;
import tech.mogami.commons.util.X402HeaderUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.mogami.commons.constant.network.Networks.BASE_SEPOLIA;

@DisplayName("x402 header SettlementResponse Util tests")
public class X402HeaderSettlementResponseUtilTest extends BaseMogamiTest {

    @Test
    @DisplayName("decodeSettlementResponse()")
    void decodeSettlementResponse() {
        assertThatThrownBy(() -> X402HeaderUtil.decodeSettlementResponse("INVALID_HEADER"))
                .isInstanceOf(InvalidX402HeaderException.class)
                .hasMessageContaining("Invalid base64 payment-response header");

        assertThat(X402HeaderUtil.decodeSettlementResponse(getSampleEncodedSettlementResponse()))
                .isNotNull()
                .returns(true, SettlementResponse::success)
                .returns("0x1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef", SettlementResponse::transaction)
                .returns(BASE_SEPOLIA.networkId(), SettlementResponse::network)
                .returns("0x857b06519E91e3A54538791bDbb0E22373e36b66", SettlementResponse::payer)
                .returns(null, SettlementResponse::errorReason);
    }

    @Test
    @DisplayName("encodeSettlementResponse()")
    void encodeSettlementResponse() {
        // We encode the value.
        var encodedSettlementResponse = X402HeaderUtil.encodeSettlementResponse(getSampleSettlementResponse());

        // We check that when decoding, we come back to what we encoded.
        assertThat(X402HeaderUtil.decodeSettlementResponse(encodedSettlementResponse)).isNotNull()
                .isNotNull()
                .returns(true, SettlementResponse::success)
                .returns("0x1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef", SettlementResponse::transaction)
                .returns(BASE_SEPOLIA.networkId(), SettlementResponse::network)
                .returns("0x857b06519E91e3A54538791bDbb0E22373e36b66", SettlementResponse::payer)
                .returns(null, SettlementResponse::errorReason);
    }

}
