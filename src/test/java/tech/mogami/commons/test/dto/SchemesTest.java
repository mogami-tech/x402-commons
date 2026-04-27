package tech.mogami.commons.test.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.payment.schemes.Schemes;
import tech.mogami.commons.api.payment.schemes.exact.ExactSchemePayload;

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

}
