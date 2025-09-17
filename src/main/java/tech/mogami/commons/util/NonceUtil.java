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
    private static final int NONCE_LENGTH = 32;

    /** Secure random instance for generating nonce's. */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * Generates a random nonce.
     *
     * @return the generated nonce string (non-null)
     */
    public static String generateNonce() {
        byte[] bytes = new byte[NONCE_LENGTH];
        SECURE_RANDOM.nextBytes(bytes);
        return BLOCKCHAIN_ADDRESS_PREFIX + Hex.encodeHexString(bytes);
    }

}
