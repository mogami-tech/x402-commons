package tech.mogami.commons.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class for Base64 encoding and decoding operations.
 */
@UtilityClass
@SuppressWarnings("unused")
public class Base64Util {

    /**
     * Encodes a string to Base64 format.
     *
     * @param input the string to encode
     * @return the Base64 encoded string
     */
    public static String encode(String input) {
        return java.util.Base64.getEncoder().withoutPadding().encodeToString(input.getBytes());
    }

    /**
     * Decodes a Base64 encoded string.
     *
     * @param input the Base64 encoded string
     * @return the decoded string
     */
    public static String decode(String input) {
        return new String(java.util.Base64.getDecoder().decode(input));
    }

}
