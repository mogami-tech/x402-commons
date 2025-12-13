package tech.mogami.commons.test.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.constant.version.X402Version;
import tech.mogami.commons.constant.version.X402Versions;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.mogami.commons.constant.version.X402Versions.ALL_X402_VERSIONS;

@DisplayName("X402 versions tests")
public class X402VersionsTest {

    @Test
    @DisplayName("Test existing versions")
    void testExistingVersions() {
        assertThat(ALL_X402_VERSIONS.size()).isEqualTo(2);
        assertThat(ALL_X402_VERSIONS)
                .extracting(X402Version::version)
                .containsExactlyInAnyOrder(1, 2);
    }

    @Test
    @DisplayName("Test supported versions")
    void testSupportedVersions() {
        assertThat(X402Versions.X402_SUPPORTED_VERSIONS.size()).isEqualTo(1);
        assertThat(X402Versions.X402_SUPPORTED_VERSIONS)
                .extracting(X402Version::version)
                .containsExactlyInAnyOrder(1);
    }

    @Test
    @DisplayName("findByVersion()")
    void testFindByVersion() {
        assertThat(X402Versions.findByVersion(0)).isNotPresent();
        assertThat(X402Versions.findByVersion(1))
                .isPresent().get()
                .extracting(X402Version::version)
                .isEqualTo(1);
        assertThat(X402Versions.findByVersion("1"))
                .isPresent().get()
                .extracting(X402Version::version)
                .isEqualTo(1);
        assertThat(X402Versions.findByVersion(2))
                .isPresent().get()
                .extracting(X402Version::version)
                .isEqualTo(2);
        assertThat(X402Versions.findByVersion("2"))
                .isPresent().get()
                .extracting(X402Version::version)
                .isEqualTo(2);
    }

}
