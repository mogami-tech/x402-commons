package tech.mogami.commons.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.payment.PaymentPayload;
import tech.mogami.commons.api.payment.PaymentRequirements;
import tech.mogami.commons.api.payment.PaymentResource;
import tech.mogami.commons.api.payment.schemes.exact.ExactSchemePayload;
import tech.mogami.commons.exception.InvalidX402HeaderException;
import tech.mogami.commons.test.BaseMogamiTest;
import tech.mogami.commons.util.X402HeaderUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.mogami.commons.api.payment.schemes.Schemes.EXACT_SCHEME;
import static tech.mogami.commons.constant.x402.X402Versions.V2;

@DisplayName("x402 header PaymentPayload Util tests")
public class X402HeaderPaymentPayloadUtilTest extends BaseMogamiTest {

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
                    assertThat(payload.x402Version()).isEqualTo(V2.version());

                    assertThat(payload.resource())
                            .isNotNull()
                            .returns("https://api.example.com/premium-data", PaymentResource::url)
                            .returns("Access to premium market data", PaymentResource::description)
                            .returns("application/json", PaymentResource::mimeType);

                    assertThat(payload.accepted())
                            .isNotNull()
                            .returns(EXACT_SCHEME.name(), PaymentRequirements::scheme)
                            .returns("eip155:84532", PaymentRequirements::network)
                            .returns("10000", PaymentRequirements::amount)
                            .returns("0x036CbD53842c5426634e7929541eC2318f3dCF7e", PaymentRequirements::asset)
                            .returns("0x209693Bc6afc0C5328bA36FaF03C514EF312287C", PaymentRequirements::payTo)
                            .returns(60, PaymentRequirements::maxTimeoutSeconds);
                    assertThat(payload.accepted().getExtra("name")).hasValue("USDC");
                    assertThat(payload.accepted().getExtra("version")).hasValue("2");

                    assertThat(payload.getTypedPayload()).isNotNull().isInstanceOf(ExactSchemePayload.class);
                    assertThat((ExactSchemePayload) payload.getTypedPayload())
                            .returns("0x2d6a7588d6acca505cbf0d9a4a227e0c52c6c34008c8e8986a1283259764173608a2ce6496642e377d6da8dbbf5836e9bd15092f9ecab05ded3d6293af148b571c", ExactSchemePayload::signature);
                    assertThat(((ExactSchemePayload) payload.getTypedPayload()).authorization())
                            .returns("0x857b06519E91e3A54538791bDbb0E22373e36b66", ExactSchemePayload.Authorization::from)
                            .returns("0x209693Bc6afc0C5328bA36FaF03C514EF312287C", ExactSchemePayload.Authorization::to)
                            .returns("10000", ExactSchemePayload.Authorization::value)
                            .returns("1740672089", ExactSchemePayload.Authorization::validAfter)
                            .returns("1740672154", ExactSchemePayload.Authorization::validBefore)
                            .returns("0xf3746613c2d920b5fdabc0856f2aeb2d4f88ee6037b8cc5d04a71a4462f13480", ExactSchemePayload.Authorization::nonce);
                });
    }

    @Test
    @DisplayName("encodePaymentPayload()")
    void encodePaymentPayload() {
        assertThatThrownBy(() -> X402HeaderUtil.encodePaymentPayload(PaymentPayload.builder().build()))
                .isInstanceOf(InvalidX402HeaderException.class)
                .hasMessageContaining("Invalid payment payload object");

        // We encode a sample payment payload and decode it next.
        String encodedPayload = X402HeaderUtil.encodePaymentPayload(getSamplePaymentPayload());
        assertThat(X402HeaderUtil.decodePaymentPayload(encodedPayload)).isNotNull()
                .satisfies(payload -> {
                    assertThat(payload.x402Version()).isEqualTo(V2.version());

                    assertThat(payload.resource())
                            .isNotNull()
                            .returns("https://api.example.com/premium-data", PaymentResource::url)
                            .returns("Access to premium market data", PaymentResource::description)
                            .returns("application/json", PaymentResource::mimeType);

                    assertThat(payload.accepted())
                            .isNotNull()
                            .returns(EXACT_SCHEME.name(), PaymentRequirements::scheme)
                            .returns("eip155:84532", PaymentRequirements::network)
                            .returns("10000", PaymentRequirements::amount)
                            .returns("0x036CbD53842c5426634e7929541eC2318f3dCF7e", PaymentRequirements::asset)
                            .returns("0x209693Bc6afc0C5328bA36FaF03C514EF312287C", PaymentRequirements::payTo)
                            .returns(60, PaymentRequirements::maxTimeoutSeconds);
                    assertThat(payload.accepted().getExtra("name")).hasValue("USDC");
                    assertThat(payload.accepted().getExtra("version")).hasValue("2");

                    assertThat(payload.getTypedPayload()).isNotNull().isInstanceOf(ExactSchemePayload.class);
                    assertThat((ExactSchemePayload) payload.getTypedPayload())
                            .returns("0x2d6a7588d6acca505cbf0d9a4a227e0c52c6c34008c8e8986a1283259764173608a2ce6496642e377d6da8dbbf5836e9bd15092f9ecab05ded3d6293af148b571c", ExactSchemePayload::signature);
                    assertThat(((ExactSchemePayload) payload.getTypedPayload()).authorization())
                            .returns("0x857b06519E91e3A54538791bDbb0E22373e36b66", ExactSchemePayload.Authorization::from)
                            .returns("0x209693Bc6afc0C5328bA36FaF03C514EF312287C", ExactSchemePayload.Authorization::to)
                            .returns("10000", ExactSchemePayload.Authorization::value)
                            .returns("1740672089", ExactSchemePayload.Authorization::validAfter)
                            .returns("1740672154", ExactSchemePayload.Authorization::validBefore)
                            .returns("0xf3746613c2d920b5fdabc0856f2aeb2d4f88ee6037b8cc5d04a71a4462f13480", ExactSchemePayload.Authorization::nonce);
                });
    }

}
