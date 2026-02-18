package tech.mogami.commons.payment.extensions;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;
import tech.mogami.commons.payment.extensions.bazaar.BazaarExtension;

/**
 * x402 extensions.
 *
 * @param bazaar bazaar extension enabling resource discovery and cataloging
 */
@Builder
@Jacksonized
@Schema(description = "Container for supported x402 protocol extensions")
@SuppressWarnings("unused")
public record Extensions(

        @Valid
        @Schema(description = "Bazaar extension enabling resource discovery", nullable = true)
        @Nullable BazaarExtension bazaar

) {
}
