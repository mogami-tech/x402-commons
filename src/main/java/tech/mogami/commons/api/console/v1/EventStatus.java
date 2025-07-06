package tech.mogami.commons.api.console.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

/**
 * EventStatus represents the status of an event in the x402 console.
 *
 * @param status  The status of the event, such as PENDING, ADDED, or REJECTED.
 * @param message A specific message providing additional information about the event status.
 */
@Builder
@Jacksonized
@Schema(description = "EventStatus represents the status of an event in the x402 console")
public record EventStatus(

        @Schema(description = "The status of the event", example = "PENDING")
        String status,

        @Schema(description = "A specific message providing additional information about the event status", example = "Nonce not found in the payload")
        String message

) {
}
