package tech.mogami.commons.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.util.Base64Util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("Base64 Util tests")
public class Base64UtilTest {

    @Test
    @DisplayName("encode() and decode() - Basic Functionality")
    void encodeAndDecode() {
        var encoded = Base64Util.encode("Hello Mogami!");
        assertThat(encoded)
                .isNotNull()
                .isNotEqualTo("Hello Mogami!");
        assertThat(Base64Util.decode(encoded)).isEqualTo("Hello Mogami!");
    }

    @Test
    @DisplayName("encode() - Empty String")
    void encodeEmptyString() {
        var encoded = Base64Util.encode("");
        assertThat(encoded).isEmpty();
        assertThat(Base64Util.decode(encoded)).isEmpty();
    }

    @Test
    @DisplayName("encode() - Special Characters")
    void encodeSpecialCharacters() {
        assertThatIllegalArgumentException().isThrownBy(() -> Base64Util.decode("@#%!not-base64"));
    }

}
