package tech.mogami.commons.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.exception.InvalidX402HeaderException;
import tech.mogami.commons.test.BaseTest;
import tech.mogami.commons.util.X402HeaderUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.mogami.commons.constant.network.Networks.BASE_SEPOLIA;

@DisplayName("x402 header SettlementResponse Util Tests")
public class X402HeaderSettlementResponseUtilTest extends BaseTest {

    @Test
    @DisplayName("decodeSettlementResponse()")
    void decodeSettlementResponse() {
        assertThatThrownBy(() -> X402HeaderUtil.decodeSettlementResponse("INVALID_HEADER"))
                .isInstanceOf(InvalidX402HeaderException.class)
                .hasMessageContaining("Invalid base64 payment-response header");

        assertThat(X402HeaderUtil.decodeSettlementResponse(getSampleEncodedSettlementResponse())).isNotNull()
                .satisfies(settlementResponse -> {
                    assertThat(settlementResponse.success()).isTrue();
                    assertThat(settlementResponse.transaction()).isEqualTo("0x1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef");
                    assertThat(settlementResponse.errorReason()).isBlank();
                    assertThat(settlementResponse.network()).isEqualTo(BASE_SEPOLIA.networkId());
                    assertThat(settlementResponse.payer()).isEqualTo("0x857b06519E91e3A54538791bDbb0E22373e36b66");
                });
    }

    @Test
    @DisplayName("encodeSettlementResponse()")
    void encodeSettlementResponse() {
        // We encode the value.
        var encodedSettlementResponse = X402HeaderUtil.encodeSettlementResponse(getSampleSettlementResponse());

        // We check that when decoding, we come back to what we encoded.
        assertThat(X402HeaderUtil.decodeSettlementResponse(encodedSettlementResponse)).isNotNull()
                .satisfies(settlementResponse -> {
                    assertThat(settlementResponse.success()).isTrue();
                    assertThat(settlementResponse.transaction()).isEqualTo("0x1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef");
                    assertThat(settlementResponse.errorReason()).isBlank();
                    assertThat(settlementResponse.network()).isEqualTo(BASE_SEPOLIA.networkId());
                    assertThat(settlementResponse.payer()).isEqualTo("0x857b06519E91e3A54538791bDbb0E22373e36b66");
                });
    }

}
