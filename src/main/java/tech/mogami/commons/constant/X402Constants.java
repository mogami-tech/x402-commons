package tech.mogami.commons.constant;

import lombok.experimental.UtilityClass;

/**
 * X402 constants.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused"})
public class X402Constants {

    /** X402 payment required header. */
    public static final String X402_X_PAYMENT_HEADER = "X-PAYMENT";

    /** X402 payment required header decoded - This is the added by the Mogami server to the response. */
    public static final String X402_X_PAYMENT_HEADER_DECODED = "X-PAYMENT-DECODED";

    /** X402 header containing the settlement Response as Base64 encoded JSON if the payment was executed successfully. */
    public static final String X402_X_PAYMENT_RESPONSE = "X-PAYMENT-RESPONSE";

    /** X402 payment required message. */
    public static final String X402_PAYMENT_REQUIRED_MESSAGE = "Payment required";

    /** Default payment timeout in seconds. */
    public static final int X402_DEFAULT_PAYMENT_TIMEOUT_SECONDS = 60;

}
