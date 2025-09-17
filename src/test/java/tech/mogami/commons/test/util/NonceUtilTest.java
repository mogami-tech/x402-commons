package tech.mogami.commons.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.util.NonceUtil;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Nonce Util Tests")
public class NonceUtilTest {

    @Test
    @DisplayName("generateNonce()")
    void shouldGenerateNonceWithCorrectPrefixAndLength() {
        var nonce = NonceUtil.generateNonce();

        assertThat(nonce).isNotNull();
        assertThat(nonce).startsWith("0x");
        assertThat(nonce.length()).isEqualTo(2 + 64); // "0x" + 64 hex chars
    }

    @Test
    @DisplayName("generateNonce() - Hexadecimal Check")
    void shouldGenerateLowercaseHexOnly() {
        var nonce = NonceUtil.generateNonce();

        var hexPart = nonce.substring(2); // remove "0x"
        assertThat(hexPart).matches("^[0-9a-f]{64}$");
    }

    @RepeatedTest(100)
    @DisplayName("generateNonce() - Unique Nonce Generation")
    void shouldGenerateUniqueNonce() {
        var nonceList = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            var nonce = NonceUtil.generateNonce();
            assertThat(nonceList).doesNotContain(nonce);
            nonceList.add(nonce);
        }
    }

}
