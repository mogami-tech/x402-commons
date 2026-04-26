package tech.mogami.commons.constant.x402;

import lombok.experimental.UtilityClass;
import tech.mogami.commons.api.facilitator.settle.SettlementResponse;
import tech.mogami.commons.api.payment.PaymentPayload;
import tech.mogami.commons.api.payment.PaymentRequired;

/**
 * X402 constants.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused"})
public class X402Constants {

    /**
     * The server indicates payment is required using the HTTP 402 "Payment Required" status code (Server → Client).
     * Mechanism: HTTP 402 status code with PAYMENT-REQUIRED header.
     * Data Format: Base64-encoded {@link PaymentRequired} schema in header.
     */
    public static final String X402_PAYMENT_REQUIRED_HEADER = "payment-required";

    /**
     * Clients send payment data using the PAYMENT-SIGNATURE HTTP header (Client → Server).
     * Mechanism: PAYMENT-SIGNATURE header containing base64-encoded JSON.
     * Data Format: Base64-encoded {@link PaymentPayload} schema.
     */
    public static final String X402_PAYMENT_SIGNATURE_HEADER = "payment-signature";

    /**
     * Servers communicate payment settlement results using the PAYMENT-RESPONSE header (Server → Client).
     * Mechanism: PAYMENT-RESPONSE header containing base64-encoded JSON.
     * Data Format: Base64-encoded {@link SettlementResponse} schema.
     */
    public static final String X402_PAYMENT_RESPONSE_HEADER = "payment-response";

    /** X402 payment required message. */
    public static final String X402_PAYMENT_REQUIRED_MESSAGE = "Payment required";

    /** Default payment timeout in seconds. */
    public static final int X402_DEFAULT_PAYMENT_TIMEOUT_SECONDS = 60;

}
