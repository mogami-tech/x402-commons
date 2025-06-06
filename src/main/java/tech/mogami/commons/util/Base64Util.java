package tech.mogami.commons.util;

import lombok.experimental.UtilityClass;

import java.util.Base64;

/**
 * Utility class for Base64 encoding and decoding operations.
 */
@UtilityClass
@SuppressWarnings({"HideUtilityClassConstructor", "unused"})
public class Base64Util {

    /**
     * Encodes a string to Base64 format.
     *
     * @param input the string to encode
     * @return the Base64 encoded string
     */
    public static String encode(final String input) {
        return Base64.getEncoder().withoutPadding().encodeToString(input.getBytes());
    }

    /**
     * Decodes a Base64 encoded string.
     *
     * @param input the Base64 encoded string
     * @return the decoded string
     */
    public static String decode(final String input) {
        return new String(Base64.getDecoder().decode(input));
    }

}
