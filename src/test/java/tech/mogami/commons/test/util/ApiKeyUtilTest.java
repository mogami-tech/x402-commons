package tech.mogami.commons.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.util.ApiKeyUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static tech.mogami.commons.util.ApiKeyUtil.DEFAULT_API_KEY_LENGTH;

@DisplayName("API Key Util tests")
public class ApiKeyUtilTest {

    @Test
    @DisplayName("generateApiKey() - Default Length of 64")
    void generateApiKeyWithDefaultLength() {
        assertThat(ApiKeyUtil.generateApiKey())
                .isNotNull()
                .hasSize(DEFAULT_API_KEY_LENGTH)
                .matches("[a-zA-Z0-9]+");
    }

    @Test
    @DisplayName("generateApiKey() - Custom Length")
    void generateApiKeyWithCustomLength() {
        assertThat(ApiKeyUtil.generateApiKey(42))
                .isNotNull()
                .hasSize(42)
                .matches("[a-zA-Z0-9]+");
    }

    @Test
    @DisplayName("generateApiKey() - Invalid Length")
    void invalidKeyLength() {
        assertThatIllegalArgumentException().isThrownBy(() -> ApiKeyUtil.generateApiKey(0));
        assertThatIllegalArgumentException().isThrownBy(() -> ApiKeyUtil.generateApiKey(-1));
    }

    @Test
    @DisplayName("generateApiKey() - Unique Keys")
    void uniqueKeys() {
        assertThat(ApiKeyUtil.generateApiKey()).isNotEqualTo(ApiKeyUtil.generateApiKey());
    }

}
