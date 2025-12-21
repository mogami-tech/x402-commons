package tech.mogami.commons.test;

import tech.mogami.commons.payment.PaymentPayload;
import tech.mogami.commons.payment.PaymentRequirements;

import java.util.Base64;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Base test.
 */
@SuppressWarnings({"unused"})
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
     * Get sample encoded payment required.
     *
     * @return the sample encoded payment required
     */
    protected String getSampleEncodedPaymentRequired() {
        return "eyJ4NDAyVmVyc2lvbiI6MiwiZXJyb3IiOiJQQVlNRU5ULVNJR05BVFVSRSBoZWFkZXIgaXMgcmVxdWlyZWQiLCJyZXNvdXJjZSI6eyJ1cmwiOiJodHRwczovL2FwaS5leGFtcGxlLmNvbS9wcmVtaXVtLWRhdGEiLCJkZXNjcmlwdGlvbiI6IkFjY2VzcyB0byBwcmVtaXVtIG1hcmtldCBkYXRhIiwibWltZVR5cGUiOiJhcHBsaWNhdGlvbi9qc29uIn0sImFjY2VwdHMiOlt7InNjaGVtZSI6ImV4YWN0IiwibmV0d29yayI6ImVpcDE1NTo4NDUzMiIsImFtb3VudCI6IjEwMDAwIiwiYXNzZXQiOiIweDAzNkNiRDUzODQyYzU0MjY2MzRlNzkyOTU0MWVDMjMxOGYzZENGN2UiLCJwYXlUbyI6IjB4MjA5NjkzQmM2YWZjMEM1MzI4YkEzNkZhRjAzQzUxNEVGMzEyMjg3QyIsIm1heFRpbWVvdXRTZWNvbmRzIjo2MCwiZXh0cmEiOnsibmFtZSI6IlVTREMiLCJ2ZXJzaW9uIjoiMiJ9fV19";
    }

    /**
     * Get empty JSON.
     *
     * @return just {}
     */
    protected String getEmptyJson() {
        return "ewoKfQ==";
    }

    /**
     * Get sample encoded payment required (V1).
     *
     * @return the sample encoded payment required
     */
    protected String getSampleEncodedPaymentRequiredV1() {
        return "eyJ4NDAyVmVyc2lvbiI6MSwiZXJyb3IiOiJQQVlNRU5ULVNJR05BVFVSRSBoZWFkZXIgaXMgcmVxdWlyZWQiLCJyZXNvdXJjZSI6eyJ1cmwiOiJodHRwczovL2FwaS5leGFtcGxlLmNvbS9wcmVtaXVtLWRhdGEiLCJkZXNjcmlwdGlvbiI6IkFjY2VzcyB0byBwcmVtaXVtIG1hcmtldCBkYXRhIiwibWltZVR5cGUiOiJhcHBsaWNhdGlvbi9qc29uIn0sImFjY2VwdHMiOlt7InNjaGVtZSI6ImV4YWN0IiwibmV0d29yayI6ImVpcDE1NTo4NDUzMiIsImFtb3VudCI6IjEwMDAwIiwiYXNzZXQiOiIweDAzNkNiRDUzODQyYzU0MjY2MzRlNzkyOTU0MWVDMjMxOGYzZENGN2UiLCJwYXlUbyI6IjB4MjA5NjkzQmM2YWZjMEM1MzI4YkEzNkZhRjAzQzUxNEVGMzEyMjg3QyIsIm1heFRpbWVvdXRTZWNvbmRzIjo2MCwiZXh0cmEiOnsibmFtZSI6IlVTREMiLCJ2ZXJzaW9uIjoiMiJ9fV19";
    }

    /**
     * Get sample encoded payment payload without accepts.
     *
     * @return the sample encoded payment payload without accepts
     */
    protected String getSampleEncodedPaymentPayloadWithoutAccepts() {
        return "ewogICJ4NDAyVmVyc2lvbiI6IDIsCiAgImVycm9yIjogIlBBWU1FTlQtU0lHTkFUVVJFIGhlYWRlciBpcyByZXF1aXJlZCIsCiAgInJlc291cmNlIjogewogICAgInVybCI6ICJodHRwczovL2FwaS5leGFtcGxlLmNvbS9wcmVtaXVtLWRhdGEiLAogICAgImRlc2NyaXB0aW9uIjogIkFjY2VzcyB0byBwcmVtaXVtIG1hcmtldCBkYXRhIiwKICAgICJtaW1lVHlwZSI6ICJhcHBsaWNhdGlvbi9qc29uIgogIH0KfQ==";
    }

    /**
     * Get sample encoded payment response.
     *
     * @return the sample encoded payment response
     */
    protected String getSampleEncodedPaymentResponse() {
        return "eyJzdWNjZXNzIjpmYWxzZSwiZXJyb3JSZWFzb24iOiJpbnN1ZmZpY2llbnRfZnVuZHMiLCJ0cmFuc2FjdGlvbiI6IiIsIm5ldHdvcmsiOiJlaXAxNTU6ODQ1MzIiLCJwYXllciI6IjB4ODU3YjA2NTE5RTkxZTNBNTQ1Mzg3OTFiRGJiMEUyMjM3M2UzNmI2NiJ9";
    }

    /**
     * Get sample payment payload.
     *
     * @return the sample payment payload
     */
    protected PaymentPayload getSamplePaymentPayload() {
        // TODO Rewrite this.
//        return PaymentPayload.builder()
//                .x402Version(X402_SUPPORTED_VERSION_BY_MOGAMI.version())
//                .scheme(EXACT_SCHEME.name())
//                .network(BASE_SEPOLIA.name())
//                .payload(ExactSchemePayload.builder()
//                        .signature("0xf268bbac717601c718075e60461516d6d36302e3d3c07be5c58a89d3dc10b3bf5dfc813c446f82c0f71e9dfef47fd894e16fc64d666553c89717f7730b3698531c")
//                        .authorization(ExactSchemePayload.Authorization.builder()
//                                .from("0x2980bc24bBFB34DE1BBC91479Cb712ffbCE02F73")
//                                .to("0x7553F6FA4Fb62986b64f79aEFa1fB93ea64A22b1")
//                                .value("1000")
//                                .validAfter("1747639463")
//                                .validBefore("1747639583")
//                                .nonce("0x1d7e10376afe381748b69ffd53b87de4213374957a1adeea46cf3ccf9aebf199")
//                                .build())
//                        .build())
//                .build();
        return null;
    }

    /**
     * Get sample payment requirements.
     *
     * @return the sample payment requirements
     */
    protected PaymentRequirements getSamplePaymentRequirements() {
        // TODO Rewrite this.
//        return PaymentRequirements.builder()
//                .scheme(EXACT_SCHEME.name())
//                .network(BASE_SEPOLIA.name())
//                .amount("1000")
//                .resource("http://localhost:4021/weather")
//                .description("")
//                .mimeType("")
//                .payTo("0x7553F6FA4Fb62986b64f79aEFa1fB93ea64A22b1")
//                .maxTimeoutSeconds(X402_DEFAULT_PAYMENT_TIMEOUT_SECONDS)
//                .asset("0x036CbD53842c5426634e7929541eC2318f3dCF7e")
//                .extra(Map.of(EXACT_SCHEME_PARAMETER_NAME, "USDC"))
//                .extra(Map.of(EXACT_SCHEME_PARAMETER_VERSION, "2"))
//                .build();
        return null;
    }

    /**
     * Get sample encoded payment header.
     *
     * @param nonce the nonce to include in the payment header
     * @return the sample encoded payment header in base64 format
     */
    protected String getSampleEncodedPaymentHeader(final String nonce) {
        // TODO Rewrite this.
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
