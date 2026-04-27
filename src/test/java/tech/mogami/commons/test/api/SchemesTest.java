package tech.mogami.commons.test.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.payment.schemes.Schemes;
import tech.mogami.commons.api.payment.schemes.exact.ExactSchemePayload;
import tech.mogami.commons.api.payment.schemes.upto.UptoSchemePayload;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Schemes tests")
public class SchemesTest {

    @Test
    @DisplayName("findByName should return empty for null")
    void nullName() {
        assertThat(Schemes.findByName(null)).isEmpty();
    }

    @Test
    @DisplayName("findByName should return empty for empty string")
    void emptyName() {
        assertThat(Schemes.findByName("")).isEmpty();
    }

    @Test
    @DisplayName("findByName should return empty for unknown scheme")
    void unknownName() {
        assertThat(Schemes.findByName("unknown")).isEmpty();
    }

    @Test
    @DisplayName("findByName should return exact scheme")
    void exactScheme() {
        assertThat(Schemes.findByName("exact"))
                .isPresent()
                .hasValueSatisfying(scheme -> {
                    assertThat(scheme.name()).isEqualTo("exact");
                    assertThat(scheme.payloadClass()).isEqualTo(ExactSchemePayload.class);
                });
    }

    @Test
    @DisplayName("findByName should be case-insensitive")
    void caseInsensitive() {
        assertThat(Schemes.findByName("EXACT")).isPresent();
        assertThat(Schemes.findByName("Exact")).isPresent();
        assertThat(Schemes.findByName("eXaCt")).isPresent();
    }

    @Test
    @DisplayName("findByName should return upto scheme")
    void uptoScheme() {
        assertThat(Schemes.findByName("upto"))
                .isPresent()
                .hasValueSatisfying(scheme -> {
                    assertThat(scheme.name()).isEqualTo("upto");
                    assertThat(scheme.payloadClass()).isEqualTo(UptoSchemePayload.class);
                });
    }

    @Test
    @DisplayName("upto scheme should not be in SUPPORTED_SCHEMES")
    void uptoNotInSupportedSchemes() {
        assertThat(Schemes.SUPPORTED_SCHEMES)
                .noneMatch(scheme -> "upto".equals(scheme.name()));
    }

    @Test
    @DisplayName("SUPPORTED_SCHEMES should contain only exact scheme")
    void supportedSchemesContainsOnlyExact() {
        assertThat(Schemes.SUPPORTED_SCHEMES).hasSize(1);
        assertThat(Schemes.SUPPORTED_SCHEMES.getFirst().name()).isEqualTo("exact");
    }

}
