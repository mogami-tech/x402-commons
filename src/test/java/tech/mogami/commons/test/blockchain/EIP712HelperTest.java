package tech.mogami.commons.test.blockchain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import tech.mogami.commons.api.payment.PaymentRequirements;
import tech.mogami.commons.api.payment.schemes.exact.ExactSchemePayload;
import tech.mogami.commons.blockchain.signature.EIP712Helper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.mogami.commons.api.payment.schemes.Schemes.EXACT_SCHEME;
import static tech.mogami.commons.api.payment.schemes.exact.ExactSchemeConstants.EXACT_SCHEME_PARAMETER_NAME;
import static tech.mogami.commons.api.payment.schemes.exact.ExactSchemeConstants.EXACT_SCHEME_PARAMETER_VERSION;
import static tech.mogami.commons.constant.network.Networks.BASE_SEPOLIA;
import static tech.mogami.commons.test.BaseMogamiTestData.TEST_CLIENT_WALLET_ADDRESS_1;
import static tech.mogami.commons.test.BaseMogamiTestData.TEST_CLIENT_WALLET_ADDRESS_1_PRIVATE_KEY;
import static tech.mogami.commons.test.BaseMogamiTestData.TEST_SERVER_WALLET_ADDRESS_1;

/**
 * Tests for the EIP-712 helper class.
 */
@DisplayName("EIP-712 helper tests")
public class EIP712HelperTest {

    final String expectedSignature = "0x7d9463e2c7c98e33c08747882521be88cc02443a8c46f3a1f5b51ae8d1bdd9581fa41ab35c1cebfe70a79471640a1bde9ffadd377e38d708b5ca6a38b30300f61b";

    final PaymentRequirements paymentRequirements = PaymentRequirements.builder()
            .scheme(EXACT_SCHEME.name())
            .network(BASE_SEPOLIA.networkId())
            .amount("10000")
            .payTo(TEST_SERVER_WALLET_ADDRESS_1)
            .asset("0x036CbD53842c5426634e7929541eC2318f3dCF7e")
            .extra(EXACT_SCHEME_PARAMETER_NAME, "USDC")
            .extra(EXACT_SCHEME_PARAMETER_VERSION, "2")
            .build();

    final ExactSchemePayload.Authorization exactSchemePayloadAuthorization = ExactSchemePayload.Authorization.builder()
            .from(TEST_CLIENT_WALLET_ADDRESS_1)
            .to(TEST_SERVER_WALLET_ADDRESS_1)
            .value("10000")
            .validAfter("1748534647")
            .validBefore("1748534767")
            .nonce("0x9b750f5097972d82c02ac371278b83ecf3ca3be8387db59e664eb38c98f97a3d")
            .build();

    @Test
    @DisplayName("EIP-712 signature creation")
    public void signatureCreation() throws Exception {
        assertThat(EIP712Helper.sign(
                Credentials.create(TEST_CLIENT_WALLET_ADDRESS_1_PRIVATE_KEY),
                paymentRequirements,
                exactSchemePayloadAuthorization))
                .isEqualTo(expectedSignature);
    }

    @Test
    @DisplayName("EIP-712 signature verification")
    public void signatureVerification() throws Exception {
        assertThat(EIP712Helper.verify(
                expectedSignature,
                paymentRequirements,
                exactSchemePayloadAuthorization,
                Credentials.create(TEST_CLIENT_WALLET_ADDRESS_1_PRIVATE_KEY).getAddress()))
                .isTrue();
    }

    @Test
    @DisplayName("sign() throws NullPointerException when credentials is null")
    void signThrowsOnNullCredentials() {
        assertThatThrownBy(() -> EIP712Helper.sign(null, paymentRequirements, exactSchemePayloadAuthorization))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("credentials must not be null");
    }

    @Test
    @DisplayName("sign() throws NullPointerException when paymentsRequirements is null")
    void signThrowsOnNullPaymentRequirements() {
        assertThatThrownBy(() -> EIP712Helper.sign(
                        Credentials.create(TEST_CLIENT_WALLET_ADDRESS_1_PRIVATE_KEY), null, exactSchemePayloadAuthorization))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("paymentsRequirements must not be null");
    }

    @Test
    @DisplayName("sign() throws NullPointerException when authorization is null")
    void signThrowsOnNullAuthorization() {
        assertThatThrownBy(() -> EIP712Helper.sign(
                        Credentials.create(TEST_CLIENT_WALLET_ADDRESS_1_PRIVATE_KEY), paymentRequirements, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("exactSchemePayloadAuthorization must not be null");
    }

    @Test
    @DisplayName("verify() throws IllegalArgumentException when signatureHex is blank")
    void verifyThrowsOnBlankSignature() {
        assertThatThrownBy(() -> EIP712Helper.verify(
                        "  ", paymentRequirements, exactSchemePayloadAuthorization,
                        Credentials.create(TEST_CLIENT_WALLET_ADDRESS_1_PRIVATE_KEY).getAddress()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("signatureHex must not be null or blank");
    }

    @Test
    @DisplayName("verify() throws IllegalArgumentException when signatureHex has invalid length")
    void verifyThrowsOnInvalidSignatureLength() {
        assertThatThrownBy(() -> EIP712Helper.verify(
                        "0xdeadbeef", paymentRequirements, exactSchemePayloadAuthorization,
                        Credentials.create(TEST_CLIENT_WALLET_ADDRESS_1_PRIVATE_KEY).getAddress()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("EIP712_SIGNATURE_LENGTH")
                .hasMessageContaining("132");
    }

    @Test
    @DisplayName("verify() throws NullPointerException when paymentsRequirements is null")
    void verifyThrowsOnNullPaymentRequirements() {
        assertThatThrownBy(() -> EIP712Helper.verify(
                        expectedSignature, null, exactSchemePayloadAuthorization,
                        Credentials.create(TEST_CLIENT_WALLET_ADDRESS_1_PRIVATE_KEY).getAddress()))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("paymentsRequirements must not be null");
    }

    @Test
    @DisplayName("verify() throws NullPointerException when authorization is null")
    void verifyThrowsOnNullAuthorization() {
        assertThatThrownBy(() -> EIP712Helper.verify(
                        expectedSignature, paymentRequirements, null,
                        Credentials.create(TEST_CLIENT_WALLET_ADDRESS_1_PRIVATE_KEY).getAddress()))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("exactSchemePayloadAuthorization must not be null");
    }

    @Test
    @DisplayName("verify() throws IllegalArgumentException when expectedSigner is blank")
    void verifyThrowsOnBlankExpectedSigner() {
        assertThatThrownBy(() -> EIP712Helper.verify(
                        expectedSignature, paymentRequirements, exactSchemePayloadAuthorization, "  "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("expectedSigner must not be null or blank");
    }

}
