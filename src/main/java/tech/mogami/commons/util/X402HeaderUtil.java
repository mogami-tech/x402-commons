package tech.mogami.commons.util;

import jakarta.validation.ConstraintViolation;
import lombok.experimental.UtilityClass;
import tech.mogami.commons.exception.InvalidX402HeaderException;
import tech.mogami.commons.exception.InvalidX402PaymentRequiredException;
import tech.mogami.commons.payment.PaymentRequired;

import java.util.Set;

import static tech.mogami.commons.constant.X402Constants.X402_PAYMENT_REQUIRED_HEADER;

/**
 * Utility class to treat X-402 headers.
 */
@UtilityClass
@SuppressWarnings({"HideUtilityClassConstructor", "unused"})
public class X402HeaderUtil {

    /**
     * Decodes the PaymentRequired from the given encoded header.
     *
     * @param encodedHeader The encoded header.
     * @return The PaymentRequired.
     */
    public static PaymentRequired decodePaymentRequired(final String encodedHeader) {
        // We decode it.
        final String decodedPaymentRequired;
        try {
            decodedPaymentRequired = Base64Util.decode(encodedHeader);
        } catch (IllegalArgumentException e) {
            throw new InvalidX402HeaderException("Invalid base64 " + X402_PAYMENT_REQUIRED_HEADER + " header", e);
        }

        // We transform the encoded value into a PaymentRequired object.
        final PaymentRequired paymentRequired;
        try {
            paymentRequired = JsonUtil.fromJson(decodedPaymentRequired, PaymentRequired.class);
        } catch (final IllegalArgumentException e) {
            throw new InvalidX402HeaderException("Invalid " + X402_PAYMENT_REQUIRED_HEADER + " JSON payload", e);
        }

        // We validate the PaymentRequired object.
        Set<ConstraintViolation<PaymentRequired>> violations = ValidationUtil.findViolations(paymentRequired);
        if (!violations.isEmpty()) {
            throw new InvalidX402PaymentRequiredException(violations);
        }

        return paymentRequired;
    }

    /**
     * Encodes the PaymentRequired into a base64 string.
     *
     * @param paymentRequired The PaymentRequired.
     * @return The encoded string.
     */
    public static String encodePaymentRequired(final PaymentRequired paymentRequired) {
        // Check violations.
        Set<ConstraintViolation<PaymentRequired>> violations = ValidationUtil.findViolations(paymentRequired);
        if (!violations.isEmpty()) {
            throw new InvalidX402PaymentRequiredException(violations);
        }

        // Transform to JSON.
        final String json;
        try {
            json = JsonUtil.toJson(paymentRequired);
        } catch (RuntimeException e) {
            throw new InvalidX402HeaderException(
                    "Unable to serialize " + X402_PAYMENT_REQUIRED_HEADER + " payload", e);
        }

        // Return encoded in base64.
        return Base64Util.encode(json);
    }

}
