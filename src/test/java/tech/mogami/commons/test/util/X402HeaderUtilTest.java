package tech.mogami.commons.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.exception.InvalidX402HeaderException;
import tech.mogami.commons.exception.InvalidX402PaymentRequiredException;
import tech.mogami.commons.payment.PaymentRequired;
import tech.mogami.commons.test.BaseTest;
import tech.mogami.commons.util.X402HeaderUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tech.mogami.commons.constant.X402Constants.X402_PAYMENT_REQUIRED_HEADER;
import static tech.mogami.commons.constant.network.Networks.BASE_SEPOLIA;
import static tech.mogami.commons.constant.network.contract.BaseContracts.BASE_SEPOLIA_USDC_CONTRACT;
import static tech.mogami.commons.constant.version.X402Versions.X402_SUPPORTED_VERSION_BY_MOGAMI;
import static tech.mogami.commons.payment.schemes.Schemes.EXACT_SCHEME;
import static tech.mogami.commons.payment.schemes.exact.ExactSchemeConstants.EXACT_SCHEME_PARAMETER_NAME;
import static tech.mogami.commons.payment.schemes.exact.ExactSchemeConstants.EXACT_SCHEME_PARAMETER_VERSION;

@DisplayName("x402 header Util Tests")
public class X402HeaderUtilTest extends BaseTest {

    @Test
    @DisplayName("decodePaymentRequired()")
    void decodePaymentRequired() {
        assertThatThrownBy(() -> X402HeaderUtil.decodePaymentRequired("INVALID_HEADER"))
                .isInstanceOf(InvalidX402HeaderException.class)
                .hasMessageContaining("Invalid base64 " + X402_PAYMENT_REQUIRED_HEADER + " header");

        assertThatThrownBy(() -> X402HeaderUtil.decodePaymentRequired(getEmptyJson()))
                .isInstanceOf(InvalidX402PaymentRequiredException.class)
                .hasMessageContaining("Invalid x402 payment requirements");

        assertThat(X402HeaderUtil.decodePaymentRequired(getSampleEncodedPaymentRequired())).isNotNull()
                .satisfies(p -> {
                    assertTrue(p.getVersion().isPresent());
                    assertThat(p.getVersion().get()).isEqualTo(X402_SUPPORTED_VERSION_BY_MOGAMI);
                    assertThat(p.error()).isEqualTo("PAYMENT-SIGNATURE header is required");
                    assertThat(p.resource())
                            .satisfies(paymentResource -> {
                                assertThat(paymentResource.url()).contains("https://api.example.com/premium-data");
                                assertThat(paymentResource.description()).contains("Access to premium market data");
                                assertThat(paymentResource.mimeType()).isEqualTo("application/json");
                            });
                    assertThat(p.accepts())
                            .hasSize(1)
                            .satisfies(accepts -> {
                                assertThat(accepts.getFirst())
                                        .satisfies(accept -> {
                                            assertThat(accept.scheme()).isEqualTo(EXACT_SCHEME.name());
                                            assertThat(accept.network()).isEqualTo(BASE_SEPOLIA.networkId());
                                            assertThat(accept.amount()).isEqualTo("10000");
                                            assertThat(accept.asset()).isEqualTo(BASE_SEPOLIA_USDC_CONTRACT);
                                            assertThat(accept.payTo()).isEqualTo("0x209693Bc6afc0C5328bA36FaF03C514EF312287C");
                                            assertThat(accept.maxTimeoutSeconds()).isEqualTo(60);
                                            assertThat(accept.getExtra(EXACT_SCHEME_PARAMETER_NAME))
                                                    .isPresent()
                                                    .get()
                                                    .isEqualTo("USDC");
                                            assertThat(accept.getExtra(EXACT_SCHEME_PARAMETER_VERSION))
                                                    .isPresent()
                                                    .get()
                                                    .isEqualTo("2");
                                        });
                            });
                });
    }

    @Test
    @DisplayName("encodePaymentRequired()")
    void encodePaymentRequired() {
        // Invalid PaymentRequired object ==============================================================================
        assertThatThrownBy(() -> X402HeaderUtil.encodePaymentRequired(PaymentRequired.builder().build()))
                .isInstanceOf(InvalidX402PaymentRequiredException.class)
                .hasMessageContaining("Invalid x402 payment requirements");

        // Valid PaymentRequired object ================================================================================
        PaymentRequired original = X402HeaderUtil.decodePaymentRequired(getSampleEncodedPaymentRequired());
        String encoded = X402HeaderUtil.encodePaymentRequired(original);
        assertThat(encoded).isNotBlank();

        // round-trip safety ===========================================================================================
        PaymentRequired parsed = X402HeaderUtil.decodePaymentRequired(encoded);
        assertThat(parsed).isEqualTo(original);
    }

}
