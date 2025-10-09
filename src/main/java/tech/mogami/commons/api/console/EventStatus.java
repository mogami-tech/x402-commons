package tech.mogami.commons.api.console;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * EventStatus represents the status of an event in the x402 system.
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
@SuppressWarnings("unused")
public enum EventStatus {

    /** The event is pending and has not yet been processed. */
    @Schema(description = "The event is pending and has not yet been processed")
    PENDING,

    /** The event has been added to the database. */
    @Schema(description = "The event has been added to the database")
    ADDED,

    /** The event has bee, rejected due to an error. */
    @Schema(description = "The event has been rejected due to an error")
    REJECTED,

}
