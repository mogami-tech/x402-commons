package tech.mogami.commons.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.util.ApiKeyUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("API Key Util Tests")
public class ApiKeyUtilTest {

    @Test
    @DisplayName("generateApiKey() - Default Length")
    void shouldGenerateApiKeyWithDefaultLength() {
        var apiKey = ApiKeyUtil.generateApiKey();
        assertNotNull(apiKey);
        assertEquals(64, apiKey.length());
        assertTrue(apiKey.chars().allMatch(Character::isLetterOrDigit));
    }

    @Test
    @DisplayName("generateApiKey() - Custom Length")
    void shouldGenerateApiKeyWithCustomLength() {
        var length = 42;
        String apiKey = ApiKeyUtil.generateApiKey(length);
        assertNotNull(apiKey);
        assertEquals(length, apiKey.length());
        assertTrue(apiKey.chars().allMatch(Character::isLetterOrDigit));
    }

    @Test
    @DisplayName("generateApiKey() - Invalid Length")
    void shouldThrowExceptionIfLengthIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> ApiKeyUtil.generateApiKey(0));
        assertThrows(IllegalArgumentException.class, () -> ApiKeyUtil.generateApiKey(-1));
    }

    @Test
    @DisplayName("generateApiKey() - Unique Keys")
    void shouldGenerateUniqueKeys() {
        var key1 = ApiKeyUtil.generateApiKey();
        var key2 = ApiKeyUtil.generateApiKey();
        assertNotEquals(key1, key2);
    }

}
