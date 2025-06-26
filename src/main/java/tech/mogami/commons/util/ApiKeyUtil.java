package tech.mogami.commons.util;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

/**
 * Utility class for API key operations.
 */
@UtilityClass
@SuppressWarnings({"HideUtilityClassConstructor", "unused"})
public class ApiKeyUtil {

    /** Default length for generated API keys. */
    public static final int DEFAULT_API_KEY_LENGTH = 64;

    /** Characters used for generating API keys. */
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

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
        StringBuilder apiKey = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = SECURE_RANDOM.nextInt(CHARACTERS.length());
            apiKey.append(CHARACTERS.charAt(index));
        }
        return apiKey.toString();
    }

}
