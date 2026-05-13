package tech.mogami.commons.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.payment.PaymentRequired;
import tech.mogami.commons.api.payment.PaymentRequirements;
import tech.mogami.commons.api.payment.PaymentResource;
import tech.mogami.commons.exception.InvalidX402HeaderException;
import tech.mogami.commons.exception.InvalidX402PaymentRequiredException;
import tech.mogami.commons.test.BaseMogamiTest;
import tech.mogami.commons.util.X402HeaderUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.mogami.commons.api.payment.schemes.Schemes.EXACT_SCHEME;
import static tech.mogami.commons.api.payment.schemes.exact.ExactSchemeConstants.EXACT_SCHEME_PARAMETER_NAME;
import static tech.mogami.commons.api.payment.schemes.exact.ExactSchemeConstants.EXACT_SCHEME_PARAMETER_VERSION;
import static tech.mogami.commons.constant.network.Networks.BASE_SEPOLIA;
import static tech.mogami.commons.constant.network.contract.BaseContracts.BASE_SEPOLIA_USDC_CONTRACT;
import static tech.mogami.commons.constant.x402.X402Constants.X402_PAYMENT_REQUIRED_HEADER;
import static tech.mogami.commons.constant.x402.X402Versions.V2;

@DisplayName("x402 header PaymentRequired Util tests")
public class X402HeaderPaymentRequiredUtilTest extends BaseMogamiTest {

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
                .satisfies(paymentRequired -> {
                    assertThat(paymentRequired.getX402Version()).isEqualTo(V2);
                    assertThat(paymentRequired.error()).isEqualTo("PAYMENT-SIGNATURE header is required");
                    assertThat(paymentRequired.resource())
                            .returns("https://api.example.com/premium-data", PaymentResource::url)
                            .returns("Access to premium market data", PaymentResource::description)
                            .returns("application/json", PaymentResource::mimeType);
                    assertThat(paymentRequired.accepts())
                            .hasSize(1)
                            .first()
                            .returns(EXACT_SCHEME.name(), PaymentRequirements::scheme)
                            .returns(BASE_SEPOLIA.networkId(), PaymentRequirements::network)
                            .returns("10000", PaymentRequirements::amount)
                            .returns(BASE_SEPOLIA_USDC_CONTRACT, PaymentRequirements::asset)
                            .returns("0x209693Bc6afc0C5328bA36FaF03C514EF312287C", PaymentRequirements::payTo)
                            .returns(60, PaymentRequirements::maxTimeoutSeconds)
                            .satisfies(paymentRequirements -> {
                                assertThat(paymentRequirements.getExtra(EXACT_SCHEME_PARAMETER_NAME)).hasValue("USDC");
                                assertThat(paymentRequirements.getExtra(EXACT_SCHEME_PARAMETER_VERSION)).hasValue("2");
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

        // We decode a valid payment ===================================================================================
        PaymentRequired original = X402HeaderUtil.decodePaymentRequired(getSampleEncodedPaymentRequired());
        String encoded = X402HeaderUtil.encodePaymentRequired(original);
        assertThat(encoded).isNotBlank();

        // We encode it and check if they are the same =================================================================
        PaymentRequired parsed = X402HeaderUtil.decodePaymentRequired(encoded);
        assertThat(parsed).isEqualTo(original);
    }

}
