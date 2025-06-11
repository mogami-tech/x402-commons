package tech.mogami.commons.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.codec.binary.Hex;

import java.security.SecureRandom;

import static tech.mogami.commons.constant.BlockchainConstants.BLOCKCHAIN_ADDRESS_PREFIX;

/**
 * Utility class for nonce operations.
 */
@UtilityClass
@SuppressWarnings({"HideUtilityClassConstructor", "unused"})
public class NonceUtil {

    /** Length of the nonce in bytes. */
    public static final int INT = 32;

    /** Secure random instance for generating nonces. */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * Generates a random nonce.
     *
     * @return randomly generated nonce
     */
    public static String generateNonce() {
        byte[] bytes = new byte[INT];
        SECURE_RANDOM.nextBytes(bytes);
        return BLOCKCHAIN_ADDRESS_PREFIX + Hex.encodeHexString(bytes);
    }

}
