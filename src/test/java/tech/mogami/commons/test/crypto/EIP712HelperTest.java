package tech.mogami.commons.test.crypto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import tech.mogami.commons.crypto.signature.EIP712Helper;
import tech.mogami.commons.header.payment.PaymentPayload;
import tech.mogami.commons.header.payment.PaymentRequirements;
import tech.mogami.commons.header.payment.schemes.exact.ExactSchemePayload;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.mogami.commons.constant.network.Networks.BASE_SEPOLIA;
import static tech.mogami.commons.constant.version.X402Versions.X402_SUPPORTED_VERSION_BY_MOGAMI;
import static tech.mogami.commons.header.payment.schemes.Schemes.EXACT_SCHEME;
import static tech.mogami.commons.header.payment.schemes.exact.ExactSchemeConstants.EXACT_SCHEME_PARAMETER_NAME;
import static tech.mogami.commons.header.payment.schemes.exact.ExactSchemeConstants.EXACT_SCHEME_PARAMETER_VERSION;
import static tech.mogami.commons.test.BaseTestData.TEST_CLIENT_WALLET_ADDRESS_1;
import static tech.mogami.commons.test.BaseTestData.TEST_CLIENT_WALLET_ADDRESS_1_PRIVATE_KEY;
import static tech.mogami.commons.test.BaseTestData.TEST_SERVER_WALLET_ADDRESS_1;

/**
 * Tests for the EIP-712 helper class.
 * {
 * "x402Version": 1,
 * "scheme": "exact",
 * "network": "base-sepolia",
 * "payload": {
 * "signature": "0xde533856d81c76984a8dbc8d563bbb6d6d4ca36ce6c4d6e8cf315de3bfc14ab26d6bcdc37549aeed78bf92e39d5180268f8f399a4ffb816cfbf500823882b6001c",
 * "authorization": {
 * "from": "0x2980bc24bBFB34DE1BBC91479Cb712ffbCE02F73",
 * "to": "0x7553F6FA4Fb62986b64f79aEFa1fB93ea64A22b1",
 * "value": "10000",
 * "validAfter": "1748534647",
 * "validBefore": "1748534767",
 * "nonce": "0x9b750f5097972d82c02ac371278b83ecf3ca3be8387db59e664eb38c98f97a3d"
 * }
 * }
 * }
 */
@DisplayName("EIP-712 helper Tests")
public class EIP712HelperTest {

    String expectedSignature = "0xde533856d81c76984a8dbc8d563bbb6d6d4ca36ce6c4d6e8cf315de3bfc14ab26d6bcdc37549aeed78bf92e39d5180268f8f399a4ffb816cfbf500823882b6001c";

    Credentials credentials = Credentials.create(TEST_CLIENT_WALLET_ADDRESS_1_PRIVATE_KEY);

    PaymentRequirements paymentRequirements = PaymentRequirements.builder()
            .scheme(EXACT_SCHEME.name())
            .network(BASE_SEPOLIA.name())
            .maxAmountRequired("10000")
            .resource("http://localhost/weather")
            .payTo(TEST_SERVER_WALLET_ADDRESS_1)
            .asset("0x036CbD53842c5426634e7929541eC2318f3dCF7e")
            .extra(EXACT_SCHEME_PARAMETER_NAME, "USDC")
            .extra(EXACT_SCHEME_PARAMETER_VERSION, "2")
            .build();

    PaymentPayload paymentPayload = PaymentPayload.builder()
            .x402Version(X402_SUPPORTED_VERSION_BY_MOGAMI.version())
            .scheme(EXACT_SCHEME.name())
            .network(BASE_SEPOLIA.name())
            .payload(ExactSchemePayload.builder()
                    .authorization(ExactSchemePayload.Authorization.builder()
                            .from(TEST_CLIENT_WALLET_ADDRESS_1)
                            .to(TEST_SERVER_WALLET_ADDRESS_1)
                            .value("10000")
                            .validAfter("1748534647")
                            .validBefore("1748534767")
                            .nonce("0x9b750f5097972d82c02ac371278b83ecf3ca3be8387db59e664eb38c98f97a3d")
                            .build()
                    ).build()
            ).build();

    @Test
    @DisplayName("Valid EIP-712 signature")
    public void signingTest() throws Exception {
        assertThat(EIP712Helper.sign(credentials, paymentRequirements, paymentPayload))
                .isEqualTo(expectedSignature);
    }

    @Test
    @DisplayName("Valid EIP-712 signature verification")
    public void testSignVerification() throws Exception {
        assertThat(EIP712Helper.verify(
                expectedSignature,
                paymentRequirements,
                paymentPayload,
                credentials.getAddress()
        )).isTrue();
    }

}
