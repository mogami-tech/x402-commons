package tech.mogami.commons.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

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

    /** Minimum length of nonce to be shortened. */
    private static final int NONCE_SHORTEN_PREFIX_LENGTH = 6;

    /** Minimum length of nonce to be shortened. */
    private static final int NONCE_SHORTEN_SUFFIX_LENGTH = 4;

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

    /**
     * Shortens a nonce for display purposes.
     *
     * @param nonce the nonce to shorten
     * @return the shortened nonce
     */
    public static String shortenNonce(final String nonce) {
        if (StringUtils.length(nonce) > NONCE_SHORTEN_PREFIX_LENGTH + NONCE_SHORTEN_SUFFIX_LENGTH) {
            return String.format("%s...%s",
                    StringUtils.left(nonce, NONCE_SHORTEN_PREFIX_LENGTH),
                    StringUtils.right(nonce, NONCE_SHORTEN_SUFFIX_LENGTH));
        } else {
            return nonce;
        }
    }

}
