package tech.mogami.commons.test.util;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.exception.InvalidX402HeaderException;
import tech.mogami.commons.payment.schemes.exact.ExactSchemePayload;
import tech.mogami.commons.test.BaseTest;
import tech.mogami.commons.util.X402HeaderUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.mogami.commons.constant.version.X402Versions.X402_SUPPORTED_VERSION_BY_MOGAMI;
import static tech.mogami.commons.payment.schemes.Schemes.EXACT_SCHEME;

@DisplayName("x402 header PaymentPayload Util Tests")
public class X402HeaderPaymentPayloadUtilTest extends BaseTest {

    @Test
    @DisplayName("decodePaymentPayload()")
    void decodePaymentPayload() {
        assertThatThrownBy(() -> X402HeaderUtil.decodePaymentPayload("INVALID_HEADER"))
                .isInstanceOf(InvalidX402HeaderException.class)
                .hasMessageContaining("Invalid base64 payment payload header");

        assertThatThrownBy(() -> X402HeaderUtil.decodePaymentPayload(getEmptyJson()))
                .isInstanceOf(InvalidX402HeaderException.class)
                .hasMessageContaining("Invalid payment payload object");

        assertThat(X402HeaderUtil.decodePaymentPayload(getSampleEncodedPaymentPayload())).isNotNull()
                .satisfies(payload -> {
                    assertThat(payload.x402Version()).isEqualTo(X402_SUPPORTED_VERSION_BY_MOGAMI.version());
                    assertThat(payload.resource())
                            .isNotNull()
                            .satisfies(paymentResource -> {
                                assertThat(paymentResource).isNotNull();
                                assertThat(paymentResource.url()).contains("https://api.example.com/premium-data");
                                assertThat(paymentResource.description()).contains("Access to premium market data");
                                assertThat(paymentResource.mimeType()).isEqualTo("application/json");
                            });
                    assertThat(payload.accepted()).isNotNull()
                            .satisfies(accept -> {
                                assertThat(accept.scheme()).isEqualTo(EXACT_SCHEME.name());
                                assertThat(accept.network()).isEqualTo("eip155:84532");
                                assertThat(accept.amount()).isEqualTo("10000");
                                assertThat(accept.asset()).isEqualTo("0x036CbD53842c5426634e7929541eC2318f3dCF7e");
                                assertThat(accept.payTo()).isEqualTo("0x209693Bc6afc0C5328bA36FaF03C514EF312287C");
                                assertThat(accept.maxTimeoutSeconds()).isEqualTo(60);
                                assertThat(accept.getExtra("name"))
                                        .isPresent()
                                        .get()
                                        .isEqualTo("USDC");
                                assertThat(accept.getExtra("version"))
                                        .isPresent()
                                        .get()
                                        .isEqualTo("2");
                            });
                    assertThat(payload.getPayloadAs()).isNotNull()
                            .isInstanceOfSatisfying(ExactSchemePayload.class, p -> {
                                AssertionsForClassTypes.assertThat(p.signature()).isEqualTo("0x2d6a7588d6acca505cbf0d9a4a227e0c52c6c34008c8e8986a1283259764173608a2ce6496642e377d6da8dbbf5836e9bd15092f9ecab05ded3d6293af148b571c");
                                AssertionsForClassTypes.assertThat(p.authorization().from()).isEqualTo("0x857b06519E91e3A54538791bDbb0E22373e36b66");
                                AssertionsForClassTypes.assertThat(p.authorization().to()).isEqualTo("0x209693Bc6afc0C5328bA36FaF03C514EF312287C");
                                AssertionsForClassTypes.assertThat(p.authorization().value()).isEqualTo("10000");
                                AssertionsForClassTypes.assertThat(p.authorization().validAfter()).isEqualTo("1740672089");
                                AssertionsForClassTypes.assertThat(p.authorization().validBefore()).isEqualTo("1740672154");
                                AssertionsForClassTypes.assertThat(p.authorization().nonce()).isEqualTo("0xf3746613c2d920b5fdabc0856f2aeb2d4f88ee6037b8cc5d04a71a4462f13480");
                            });
                });
    }

}
