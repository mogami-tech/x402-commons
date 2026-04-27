package tech.mogami.commons.test.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.constant.x402.X402Version;
import tech.mogami.commons.constant.x402.X402Versions;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.mogami.commons.constant.x402.X402Versions.ALL_X402_VERSIONS;
import static tech.mogami.commons.constant.x402.X402Versions.V1;
import static tech.mogami.commons.constant.x402.X402Versions.V2;
import static tech.mogami.commons.constant.x402.X402Versions.X402_SUPPORTED_VERSIONS;

@DisplayName("X402 versions tests")
public class ExistingX402VersionsTest {

    @Test
    @DisplayName("Should return all existing X402 versions")
    void existingVersions() {
        assertThat(ALL_X402_VERSIONS)
                .extracting(X402Version::version)
                .containsExactlyInAnyOrder(1, 2);
    }

    @Test
    @DisplayName("Should return the current supported version")
    void supportedVersions() {
        assertThat(X402_SUPPORTED_VERSIONS).containsExactly(V2);
    }

    @Test
    @DisplayName("findByVersion()")
    void fndByVersion() {
        assertThat(X402Versions.findByVersion(0)).isNotPresent();

        assertThat(X402Versions.findByVersion(1)).hasValue(V1);
        assertThat(X402Versions.findByVersion("1")).hasValue(V1);

        assertThat(X402Versions.findByVersion(2)).hasValue(V2);
        assertThat(X402Versions.findByVersion("2")).hasValue(V2);
    }

    @Test
    @DisplayName("canonical()")
    void canonical() {
        assertThat(ALL_X402_VERSIONS).allSatisfy(version ->
                assertThat(version.canonical()).isEqualTo(Integer.toString(version.version()))
        );
    }

}
