package tech.mogami.commons.test;

import tech.mogami.commons.header.payment.PaymentPayload;
import tech.mogami.commons.header.payment.PaymentRequirements;
import tech.mogami.commons.header.payment.schemes.exact.ExactSchemePayload;

import java.util.Base64;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static tech.mogami.commons.constant.X402Constants.X402_DEFAULT_PAYMENT_TIMEOUT_SECONDS;
import static tech.mogami.commons.constant.network.Networks.BASE_SEPOLIA;
import static tech.mogami.commons.constant.version.X402Versions.X402_SUPPORTED_VERSION_BY_MOGAMI;
import static tech.mogami.commons.header.payment.schemes.Schemes.EXACT_SCHEME;
import static tech.mogami.commons.header.payment.schemes.exact.ExactSchemeConstants.EXACT_SCHEME_PARAMETER_NAME;
import static tech.mogami.commons.header.payment.schemes.exact.ExactSchemeConstants.EXACT_SCHEME_PARAMETER_VERSION;

/**
 * Base test.
 */
@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class BaseTest extends BaseTestData {

    /**
     * Get default properties.
     *
     * @param configurationFile the config location
     * @return the default properties
     */
    protected Map<String, Object> getTestProperties(final String configurationFile) {
        return Map.of(
                "spring.config.location", "classpath:parameters/" + configurationFile + ".properties"
        );
    }

    /**
     * Get sample payment payload.
     *
     * @return the sample payment payload
     */
    protected PaymentPayload getSamplePaymentPayload() {
        return PaymentPayload.builder()
                .x402Version(X402_SUPPORTED_VERSION_BY_MOGAMI.version())
                .scheme(EXACT_SCHEME.name())
                .network(BASE_SEPOLIA.name())
                .payload(ExactSchemePayload.builder()
                        .signature("0xf268bbac717601c718075e60461516d6d36302e3d3c07be5c58a89d3dc10b3bf5dfc813c446f82c0f71e9dfef47fd894e16fc64d666553c89717f7730b3698531c")
                        .authorization(ExactSchemePayload.Authorization.builder()
                                .from("0x2980bc24bBFB34DE1BBC91479Cb712ffbCE02F73")
                                .to("0x7553F6FA4Fb62986b64f79aEFa1fB93ea64A22b1")
                                .value("1000")
                                .validAfter("1747639463")
                                .validBefore("1747639583")
                                .nonce("0x1d7e10376afe381748b69ffd53b87de4213374957a1adeea46cf3ccf9aebf199")
                                .build())
                        .build())
                .build();
    }

    /**
     * Get sample payment requirements.
     *
     * @return the sample payment requirements
     */
    protected PaymentRequirements getSamplePaymentRequirements() {
        return PaymentRequirements.builder()
                .scheme(EXACT_SCHEME.name())
                .network(BASE_SEPOLIA.name())
                .maxAmountRequired("1000")
                .resource("http://localhost:4021/weather")
                .description("")
                .mimeType("")
                .payTo("0x7553F6FA4Fb62986b64f79aEFa1fB93ea64A22b1")
                .maxTimeoutSeconds(X402_DEFAULT_PAYMENT_TIMEOUT_SECONDS)
                .asset("0x036CbD53842c5426634e7929541eC2318f3dCF7e")
                .extra(Map.of(EXACT_SCHEME_PARAMETER_NAME, "USDC"))
                .extra(Map.of(EXACT_SCHEME_PARAMETER_VERSION, "2"))
                .build();
    }

    /**
     * Get sample encoded payment header.
     *
     * @param nonce the nonce to include in the payment header
     * @return the sample encoded payment header in base64 format
     */
    protected String getSampleEncodedPaymentHeader(final String nonce) {
        String paymentHeader = """
                {
                    "x402Version": 1,
                    "scheme": "exact",
                    "network": "base-sepolia",
                    "payload": {
                      "signature": "0x1c7e56451968cc2c2816fc776c6f75483815408b2e087d568ce7e8509c59911b3c9353dbdff8b565680e9defd52336eb2213dfd83f1a07c20625e53d8fda2b951b",
                      "authorization": {
                        "from": "0x857b06519E91e3A54538791bDbb0E22373e36b66",
                        "to": "0x2980bc24bBFB34DE1BBC91479Cb712ffbCE02F73",
                        "value": "1000",
                        "validAfter": "1747486410",
                        "validBefore": "1747486530",
                        "nonce": "nonceValue"
                      }
                    }
                  }""".replace("nonceValue", nonce);
        return Base64
                .getEncoder()
                .withoutPadding()
                .encodeToString(paymentHeader.getBytes(UTF_8));
    }

}
