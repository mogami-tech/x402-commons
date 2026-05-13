package tech.mogami.commons.blockchain.signature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.crypto.StructuredDataEncoder;
import org.web3j.utils.Numeric;
import tech.mogami.commons.api.payment.PaymentRequirements;
import tech.mogami.commons.api.payment.schemes.exact.ExactSchemePayload;
import tech.mogami.commons.constant.network.Network;
import tech.mogami.commons.constant.network.Networks;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;

import static tech.mogami.commons.api.payment.schemes.exact.ExactSchemeConstants.EXACT_SCHEME_PARAMETER_NAME;
import static tech.mogami.commons.api.payment.schemes.exact.ExactSchemeConstants.EXACT_SCHEME_PARAMETER_VERSION;
import static tech.mogami.commons.constant.blockchain.BlockchainConstants.BLOCKCHAIN_ADDRESS_PREFIX;
import static tech.mogami.commons.constant.blockchain.BlockchainConstants.EIP712_SIGNATURE_LENGTH;

/**
 * Utility class for EIP-712 related operations.
 */
@UtilityClass
@SuppressWarnings({"HideUtilityClassConstructor", "unused"})
public class EIP712Helper {

    /** Shared Jackson mapper — thread-safe and reused across all calls. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /** Ethereum recovery id offset defined in the Yellow Paper. */
    private static final int ETHEREUM_V_OFFSET = 27;

    /** Byte length of the R component in a 65-byte signature. */
    private static final int SIGNATURE_R_END = 32;

    /** Byte length of R + S components in a 65-byte signature. */
    private static final int SIGNATURE_S_END = 64;

    /** Byte index of the V component in a 65-byte signature. */
    private static final int SIGNATURE_V_INDEX = 64;

    /** Hex-encoded length (in nibbles) of R and S components. */
    private static final int HEX_COMPONENT_LENGTH = 64;

    /** EIP-712 schema in JSON. */
    private static final String EIP712_SCHEMA_JSON = """
            {
              "EIP712Domain":[
                {"name":"name","type":"string"},
                {"name":"version","type":"string"},
                {"name":"chainId","type":"uint256"},
                {"name":"verifyingContract","type":"address"}
              ],
              "TransferWithAuthorization":[
                {"name":"from","type":"address"},
                {"name":"to","type":"address"},
                {"name":"value","type":"uint256"},
                {"name":"validAfter","type":"uint256"},
                {"name":"validBefore","type":"uint256"},
                {"name":"nonce","type":"bytes32"}
              ]
            }
            """;

    /**
     * Signs an authorization message using EIP-712 structured data signing.
     *
     * @param credentials                     the credentials of the signer
     * @param paymentsRequirements            the payment requirements containing network and scheme information
     * @param exactSchemePayloadAuthorization the exact scheme payload containing authorization details
     * @return the signature in hexadecimal format
     * @throws Exception if an error occurs during signing
     */
    public static String sign(final Credentials credentials,
                              final PaymentRequirements paymentsRequirements,
                              final ExactSchemePayload.Authorization exactSchemePayloadAuthorization) throws Exception {
        Objects.requireNonNull(credentials, "credentials must not be null");
        Objects.requireNonNull(paymentsRequirements, "paymentsRequirements must not be null");
        Objects.requireNonNull(exactSchemePayloadAuthorization, "exactSchemePayloadAuthorization must not be null");

        // Build the EIP-712 typed-data JSON (domain + message) exactly once
        String typedDataJson = buildTypedDataJson(paymentsRequirements, exactSchemePayloadAuthorization);

        // Sign and hex-encode
        return toHex(Sign.signTypedData(typedDataJson, credentials.getEcKeyPair()));
    }

    /**
     * Verifies a signature against the expected signer using EIP-712 structured data signing.
     *
     * @param signatureHex                    the signature in hexadecimal format
     * @param paymentsRequirements            the payment requirements containing network and scheme information
     * @param exactSchemePayloadAuthorization the exact scheme payload containing authorization details
     * @param expectedSigner                  the expected signer's address
     * @return true if the signature is valid for the expected signer, false otherwise
     * @throws Exception if an error occurs during verification
     */
    public static boolean verify(final String signatureHex,
                                 final PaymentRequirements paymentsRequirements,
                                 final ExactSchemePayload.Authorization exactSchemePayloadAuthorization,
                                 final String expectedSigner) throws Exception {
        if (StringUtils.isBlank(signatureHex)) {
            throw new IllegalArgumentException("signatureHex must not be null or blank");
        }
        if (signatureHex.length() != EIP712_SIGNATURE_LENGTH) {
            throw new IllegalArgumentException("signatureHex must be exactly EIP712_SIGNATURE_LENGTH (" + EIP712_SIGNATURE_LENGTH + ") characters long, got: " + signatureHex.length());
        }
        Objects.requireNonNull(paymentsRequirements, "paymentsRequirements must not be null");
        Objects.requireNonNull(exactSchemePayloadAuthorization, "exactSchemePayloadAuthorization must not be null");
        if (StringUtils.isBlank(expectedSigner)) {
            throw new IllegalArgumentException("expectedSigner must not be null or blank");
        }

        // Create the typed-data JSON exactly as in sign()
        String typedDataJson = buildTypedDataJson(paymentsRequirements, exactSchemePayloadAuthorization);

        // Hash according to EIP-712
        byte[] dataHash = new StructuredDataEncoder(typedDataJson).hashStructuredData();

        Sign.SignatureData sig = signatureDataFromHex(signatureHex);
        BigInteger recoveredKey = Sign.signedMessageHashToKey(dataHash, sig);
        String recoveredAddress = BLOCKCHAIN_ADDRESS_PREFIX + Keys.getAddress(recoveredKey);

        // Compare
        return recoveredAddress.equalsIgnoreCase(Keys.toChecksumAddress(expectedSigner));
    }

    /**
     * Builds the JSON representation of the typed data for EIP-712 signing.
     *
     * @param paymentsRequirements            the payment requirements containing network and scheme information
     * @param exactSchemePayloadAuthorization the exact scheme payload containing authorization details
     * @return the JSON string representation of the typed data
     * @throws JsonProcessingException if an error occurs during JSON processing
     */
    private static String buildTypedDataJson(final PaymentRequirements paymentsRequirements,
                                             final ExactSchemePayload.Authorization exactSchemePayloadAuthorization) throws JsonProcessingException {
        // Validate inputs =============================================================================================
        Network network = Networks.findByNetworkId(paymentsRequirements.network())
                .orElseThrow(() -> new IllegalArgumentException("Unsupported network: " + paymentsRequirements.network()));

        String tokenName = paymentsRequirements.extra().get(EXACT_SCHEME_PARAMETER_NAME);
        if (StringUtils.isBlank(tokenName)) {
            throw new IllegalArgumentException("Payment requirements extra parameter '" + EXACT_SCHEME_PARAMETER_NAME + "' must not be null or blank");
        }
        String tokenVersion = paymentsRequirements.extra().get(EXACT_SCHEME_PARAMETER_VERSION);
        if (StringUtils.isBlank(tokenVersion)) {
            throw new IllegalArgumentException("Payment requirements extra parameter '" + EXACT_SCHEME_PARAMETER_VERSION + "' must not be null or blank");
        }

        // Build the EIP-712 typed-data JSON (domain + message) ========================================================
        ObjectNode domain = OBJECT_MAPPER.createObjectNode();
        domain.put("name", tokenName);
        domain.put("version", tokenVersion);
        domain.put("chainId", network.chainId());
        domain.put("verifyingContract", paymentsRequirements.asset());

        ObjectNode msg = OBJECT_MAPPER.createObjectNode();
        msg.put("from", exactSchemePayloadAuthorization.from());
        msg.put("to", exactSchemePayloadAuthorization.to());
        msg.put("value", new BigInteger(exactSchemePayloadAuthorization.value()));
        msg.put("validAfter", new BigInteger(exactSchemePayloadAuthorization.validAfter()));
        msg.put("validBefore", new BigInteger(exactSchemePayloadAuthorization.validBefore()));
        msg.put("nonce", exactSchemePayloadAuthorization.nonce());

        ObjectNode root = OBJECT_MAPPER.createObjectNode();
        root.put("primaryType", "TransferWithAuthorization");
        root.set("types", OBJECT_MAPPER.readTree(EIP712_SCHEMA_JSON));
        root.set("domain", domain);
        root.set("message", msg);

        return OBJECT_MAPPER.writeValueAsString(root);
    }

    /**
     * Converts a SignatureData object to a hexadecimal string representation.
     *
     * @param sig the SignatureData object to convert
     * @return the hexadecimal string representation of the signature
     */
    private static String toHex(final Sign.SignatureData sig) {
        byte[] v = sig.getV();
        int vValue = v[0];
        if (vValue != ETHEREUM_V_OFFSET && vValue != ETHEREUM_V_OFFSET + 1) {
            vValue = vValue + ETHEREUM_V_OFFSET;
        }
        return BLOCKCHAIN_ADDRESS_PREFIX
                + Numeric.toHexStringNoPrefixZeroPadded(new BigInteger(1, sig.getR()), HEX_COMPONENT_LENGTH)
                + Numeric.toHexStringNoPrefixZeroPadded(new BigInteger(1, sig.getS()), HEX_COMPONENT_LENGTH)
                + String.format("%02x", vValue);
    }

    /**
     * Converts a hexadecimal signature string to a SignatureData object.
     *
     * @param signatureHex the hexadecimal signature string
     * @return the SignatureData object
     */
    private static Sign.SignatureData signatureDataFromHex(final String signatureHex) {
        byte[] sigBytes = Numeric.hexStringToByteArray(signatureHex);

        byte v = sigBytes[SIGNATURE_V_INDEX];
        if (v < ETHEREUM_V_OFFSET) {
            v += ETHEREUM_V_OFFSET; // align with Ethereum yellow-paper values
        }

        byte[] r = Arrays.copyOfRange(sigBytes, 0, SIGNATURE_R_END);
        byte[] s = Arrays.copyOfRange(sigBytes, SIGNATURE_R_END, SIGNATURE_S_END);

        return new Sign.SignatureData(v, r, s);
    }

}
