package tech.mogami.commons.api.console.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import tech.mogami.commons.api.console.EventType;

import java.time.Instant;

/**
 * An even represents a x402 event captured by the x402 server or the x402 facilitator.
 *
 * @param type         The type of the event, such as X402_SERVER_URL_ACCESS_REQUEST, etc.
 * @param payload      Event payload in JSON format.
 * @param errorMessage A specific error message, if any.
 * @param timestamp    The timestamp of the event.
 */
@Builder
@Jacksonized
@Schema(description = "An even represents a x402 event captured by the x402 server or the x402 facilitator")
@SuppressWarnings("unused")
public record EventRequest(

        @NotNull(message = "{validation.console.event.type.required}")
        @Schema(description = "The type of event", example = "X402_SERVER_URL_ACCESS_REQUEST")
        EventType type,

        @NotBlank(message = "{validation.console.event.payload.required}")
        @Schema(description = "Event payload in JSON format", example = "{\"transactionHash\":\"0x1234567890abcdef\",\"status\":\"success\"}")
        String payload,

        @Schema(description = "A specific error message, if any", example = "Connexion to blockchain node failed")
        String errorMessage,

        @NotNull(message = "{validation.console.event.timestamp.required}")
        @PastOrPresent(message = "{validation.console.event.timestamp.pastOrPresent}")
        @Schema(description = "The timestamp of the event", example = "2023-10-01T12:00:00Z")
        Instant timestamp

) {

}
