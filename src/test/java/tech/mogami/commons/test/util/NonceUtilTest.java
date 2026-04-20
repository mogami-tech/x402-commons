package tech.mogami.commons.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.util.NonceUtil;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Nonce Util tests")
public class NonceUtilTest {

    @Test
    @DisplayName("generateNonce()")
    void generateNonce() {
        assertThat(NonceUtil.generateNonce()).matches("0x[0-9a-f]{64}");
    }

    @Test
    @DisplayName("generateNonce() - Unique Nonce Generation")
    void shouldGenerateUniqueNonce() {
        assertThat(Stream.generate(NonceUtil::generateNonce)
                .limit(100)
                .collect(toSet())) // It's a set - so there is no duplicated values
                .hasSize(100);
    }

    @Test
    @DisplayName("shortenNonce() - should shorten long nonce")
    void shortenNonce() {
        var nonce = "0x0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef";
        assertThat(NonceUtil.shortenNonce(nonce))
                .isEqualTo("0x0123...cdef");
    }

    @Test
    @DisplayName("shortenNonce() - should return original when nonce is short")
    void shortenNonceWithSmallNonce() {
        var nonce = "0x1234";
        assertThat(NonceUtil.shortenNonce(nonce))
                .isEqualTo(nonce);
    }

    @Test
    @DisplayName("shortenNonce() - should return original when length equals prefix plus suffix")
    void shortenNonceWithMinimumNonce() {
        var nonce = "0x12345678";
        assertThat(NonceUtil.shortenNonce(nonce))
                .isEqualTo(nonce);
    }

    @Test
    @DisplayName("shortenNonce() - should return null when nonce is null")
    void shortenNonceWithNull() {
        assertThat(NonceUtil.shortenNonce(null))
                .isNull();
    }

}
