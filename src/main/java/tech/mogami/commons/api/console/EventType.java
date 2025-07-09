package tech.mogami.commons.api.console;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;
import tech.mogami.commons.api.facilitator.settle.SettleRequest;
import tech.mogami.commons.api.facilitator.settle.SettleResponse;
import tech.mogami.commons.api.facilitator.verify.VerifyRequest;
import tech.mogami.commons.api.facilitator.verify.VerifyResponse;
import tech.mogami.commons.header.payment.PaymentPayload;

/**
 * Event types for x402 server and facilitator interactions.
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum EventType {

    /** The x402 server URL access request with an X-PAYMENT header. */
    @Schema(description = "The x402 server URL access request with an X-PAYMENT header")
    X402_SERVER_URL_ACCESS_REQUEST,

    /** The x402 server calls /verify on its facilitator server. */
    @Schema(description = "The x402 server calls /verify on its facilitator server")
    X402_SERVER_PAYMENT_VERIFY_REQUEST,

    /** The facilitator receives a /verify HTTP request from the x402 server. */
    @Schema(description = "The facilitator receives a /verify HTTP request from the x402 server")
    X402_FACILITATOR_VERIFY_REQUEST,

    /** The facilitator sends a response to the x402 server after processing the /verify request. */
    @Schema(description = "The facilitator sends a response to the x402 server after processing the /verify request")
    X402_FACILITATOR_VERIFY_RESPONSE,

    /** The x402 server receives the response from the facilitator server. */
    @Schema(description = "The x402 server receives the response from the facilitator server")
    X402_SERVER_PAYMENT_VERIFY_RESPONSE,

    /** The x402 server calls /settle on its facilitator server. */
    @Schema(description = "The x402 server calls /settle on its facilitator server")
    X402_SERVER_PAYMENT_SETTLE_REQUEST,

    /** The facilitator receives a /settle HTTP request from the x402 server. */
    @Schema(description = "The facilitator receives a /settle HTTP request from the x402 server.")
    X402_FACILITATOR_SETTLE_REQUEST,

    /** The facilitator sends a response to the x402 server after processing the /settle request. */
    @Schema(description = "The facilitator sends a response to the x402 server after processing the /settle request.")
    X402_FACILITATOR_SETTLE_RESPONSE,

    /** The x402 server receives the response from the facilitator server. */
    @Schema(description = "The x402 server receives the response from the facilitator server")
    X402_SERVER_PAYMENT_SETTLE_RESPONSE;

    /**
     * Returns the actor that is responsible for this event type.
     *
     * @return the actor (X402_SERVER or X402_FACILITATOR)
     */
    public Actor actor() {
        if (StringUtils.startsWith(this.name(), "X402_SERVER")) {
            return Actor.X402_SERVER;
        } else if (StringUtils.startsWith(this.name(), "X402_FACILITATOR")) {
            return Actor.X402_FACILITATOR;
        } else {
            throw new IllegalStateException("Unknown actor for event type: " + this.name());
        }
    }

    /**
     * Get the expected JSON type for the event based on its type.
     *
     * @param eventType the type of the event
     * @return the expected JSON type for the event
     */
    private Class<?> getExpectedJsonType(final EventType eventType) {
        return switch (eventType) {
            case X402_SERVER_URL_ACCESS_REQUEST -> PaymentPayload.class;
            case X402_SERVER_PAYMENT_VERIFY_REQUEST, X402_FACILITATOR_VERIFY_REQUEST -> VerifyRequest.class;
            case X402_FACILITATOR_VERIFY_RESPONSE, X402_SERVER_PAYMENT_VERIFY_RESPONSE -> VerifyResponse.class;
            case X402_SERVER_PAYMENT_SETTLE_REQUEST, X402_FACILITATOR_SETTLE_REQUEST -> SettleRequest.class;
            case X402_FACILITATOR_SETTLE_RESPONSE, X402_SERVER_PAYMENT_SETTLE_RESPONSE -> SettleResponse.class;
        };
    }

}
