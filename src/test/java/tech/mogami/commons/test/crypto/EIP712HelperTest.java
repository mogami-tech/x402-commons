package tech.mogami.commons.test.crypto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import tech.mogami.commons.crypto.signature.EIP712Helper;
import tech.mogami.commons.payment.PaymentPayload;
import tech.mogami.commons.payment.PaymentRequirements;
import tech.mogami.commons.payment.schemes.exact.ExactSchemePayload;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.mogami.commons.constant.network.Networks.BASE_SEPOLIA;
import static tech.mogami.commons.constant.version.X402Versions.X402_SUPPORTED_VERSION_BY_MOGAMI;
import static tech.mogami.commons.payment.schemes.Schemes.EXACT_SCHEME;
import static tech.mogami.commons.payment.schemes.exact.ExactSchemeConstants.EXACT_SCHEME_PARAMETER_NAME;
import static tech.mogami.commons.payment.schemes.exact.ExactSchemeConstants.EXACT_SCHEME_PARAMETER_VERSION;
import static tech.mogami.commons.test.BaseTestData.TEST_CLIENT_WALLET_ADDRESS_1;
import static tech.mogami.commons.test.BaseTestData.TEST_CLIENT_WALLET_ADDRESS_1_PRIVATE_KEY;
import static tech.mogami.commons.test.BaseTestData.TEST_SERVER_WALLET_ADDRESS_1;

/**
 * Tests for the EIP-712 helper class.
 */
@DisplayName("EIP-712 helper Tests")
public class EIP712HelperTest {

    final String expectedSignature = "0x7d9463e2c7c98e33c08747882521be88cc02443a8c46f3a1f5b51ae8d1bdd9581fa41ab35c1cebfe70a79471640a1bde9ffadd377e38d708b5ca6a38b30300f61b";

    final PaymentRequirements paymentRequirements = PaymentRequirements.builder()
            .scheme(EXACT_SCHEME.name())
            .network(BASE_SEPOLIA.name())
            .amount("10000")
            //.resource("http://localhost/weather")
            .payTo(TEST_SERVER_WALLET_ADDRESS_1)
            .asset("0x036CbD53842c5426634e7929541eC2318f3dCF7e")
            .extra(EXACT_SCHEME_PARAMETER_NAME, "USDC")
            .extra(EXACT_SCHEME_PARAMETER_VERSION, "2")
            .build();

    final PaymentPayload paymentPayload = PaymentPayload.builder()
            .x402Version(X402_SUPPORTED_VERSION_BY_MOGAMI.version())
            //.scheme(EXACT_SCHEME.name())
            //.network(BASE_SEPOLIA.name())
            .payload(ExactSchemePayload.builder()
                    .authorization(ExactSchemePayload.Authorization.builder()
                            .from(TEST_CLIENT_WALLET_ADDRESS_1)
                            .to(TEST_SERVER_WALLET_ADDRESS_1)
                            .value("10000")
                            .validAfter("1748534647")
                            .validBefore("1748534767")
                            .nonce("0x9b750f5097972d82c02ac371278b83ecf3ca3be8387db59e664eb38c98f97a3d")
                            .build())
                    .build())
            .build();

    @Test
    @DisplayName("EIP-712 signature creation")
    public void signatureCreation() throws Exception {
        assertThat(EIP712Helper.sign(
                Credentials.create(TEST_CLIENT_WALLET_ADDRESS_1_PRIVATE_KEY),
                paymentRequirements,
                paymentPayload))
                .isEqualTo(expectedSignature);
    }

    @Test
    @DisplayName("EIP-712 signature verification")
    public void signatureVerification() throws Exception {
        assertThat(EIP712Helper.verify(
                expectedSignature,
                paymentRequirements,
                paymentPayload,
                Credentials.create(TEST_CLIENT_WALLET_ADDRESS_1_PRIVATE_KEY).getAddress()))
                .isTrue();
    }

}
