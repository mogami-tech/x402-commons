package tech.mogami.commons.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.text.RandomStringGenerator;

import java.security.SecureRandom;

/**
 * Utility class for API key operations.
 */
@UtilityClass
@SuppressWarnings({"HideUtilityClassConstructor", "unused"})
public class ApiKeyUtil {

    /** Default length for generated API keys. */
    private static final int DEFAULT_API_KEY_LENGTH = 64;

    /** Secure random. */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * Generates a random API key with the default length.
     *
     * @return a randomly generated API key
     */
    public static String generateApiKey() {
        return generateApiKey(DEFAULT_API_KEY_LENGTH);
    }

    /**
     * Generates a random API key of the specified length.
     *
     * @param length the length of the API key
     * @return a randomly generated API key
     */
    public static String generateApiKey(final int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("API key length must be positive, not (" + length + ")");
        }

        return new RandomStringGenerator.Builder()
                .usingRandom(SECURE_RANDOM::nextInt)
                .withinRange('0', 'z') // Covers letters, digits, and a few symbols
                .filteredBy(Character::isLetterOrDigit)
                .get()
                .generate(length);
    }

}
