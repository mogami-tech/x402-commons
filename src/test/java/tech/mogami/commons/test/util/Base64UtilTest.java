package tech.mogami.commons.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.util.Base64Util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Base64 Util Tests")
public class Base64UtilTest {

    @Test
    @DisplayName("encode() and decode() - Basic Functionality")
    void shouldEncodeAndDecodeStringCorrectly() {
        var original = "Hello Mogami!";
        var encoded = Base64Util.encode(original);
        var decoded = Base64Util.decode(encoded);

        assertNotNull(encoded);
        assertNotEquals(original, encoded); // encoding should transform
        assertEquals(original, decoded);    // round-trip ok
    }

    @Test
    @DisplayName("encode() - Empty String")
    void shouldEncodeEmptyStringToEmptyBase64() {
        var encoded = Base64Util.encode("");
        var decoded = Base64Util.decode(encoded);

        assertEquals("", encoded); // base64 of empty string is empty
        assertEquals("", decoded);
    }

    @Test
    @DisplayName("encode() - Special Characters")
    void shouldThrowOnInvalidBase64Input() {
        var invalidBase64 = "@#%!not-base64";

        assertThrows(IllegalArgumentException.class, () -> {
            Base64Util.decode(invalidBase64);
        });
    }

}
