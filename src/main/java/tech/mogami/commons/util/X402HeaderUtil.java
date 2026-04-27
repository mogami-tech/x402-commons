package tech.mogami.commons.util;

import jakarta.validation.ConstraintViolation;
import lombok.experimental.UtilityClass;
import tech.mogami.commons.api.facilitator.settle.SettlementResponse;
import tech.mogami.commons.api.payment.PaymentPayload;
import tech.mogami.commons.api.payment.PaymentRequired;
import tech.mogami.commons.exception.InvalidX402HeaderException;
import tech.mogami.commons.exception.InvalidX402PaymentRequiredException;

import java.util.Set;
import java.util.function.Function;

import static tech.mogami.commons.constant.x402.X402Constants.X402_PAYMENT_REQUIRED_HEADER;
import static tech.mogami.commons.constant.x402.X402Constants.X402_PAYMENT_RESPONSE_HEADER;

/**
 * Utility class to treat x402 headers.
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
    public PaymentRequired decodePaymentRequired(final String encodedHeader) {
        return decodeHeader(
                encodedHeader,
                "Invalid base64 " + X402_PAYMENT_REQUIRED_HEADER + " header",
                PaymentRequired.class,
                "Invalid payment-required JSON payload",
                InvalidX402PaymentRequiredException::new
        );
    }

    /**
     * Encodes the PaymentRequired into a base64 string.
     *
     * @param paymentRequired The PaymentRequired.
     * @return The encoded string.
     */
    public String encodePaymentRequired(final PaymentRequired paymentRequired) {
        return encodeHeader(
                paymentRequired,
                InvalidX402PaymentRequiredException::new,
                "Unable to serialize payment-required payload"
        );
    }

    /**
     * Decodes the PaymentPayload from the given encoded payload.
     *
     * @param encodedHeader The encoded header.
     * @return The PaymentPayload.
     */
    public PaymentPayload decodePaymentPayload(final String encodedHeader) {
        return decodeHeader(
                encodedHeader,
                "Invalid base64 payment payload header",
                PaymentPayload.class,
                "Invalid x402 payment payload",
                violations -> new InvalidX402HeaderException("Invalid payment payload object", violations)
        );
    }

    /**
     * Encodes the PaymentPayload into a base64 string.
     *
     * @param paymentPayload The PaymentPayload.
     * @return The encoded string.
     */
    public String encodePaymentPayload(final PaymentPayload paymentPayload) {
        return encodeHeader(
                paymentPayload,
                violations -> new InvalidX402HeaderException("Invalid payment payload object", violations),
                "Unable to serialize payment payload"
        );
    }

    /**
     * Decodes the SettlementResponse from the given encoded header.
     *
     * @param encodedHeader The encoded header.
     * @return The SettlementResponse.
     */
    public SettlementResponse decodeSettlementResponse(final String encodedHeader) {
        return decodeHeader(
                encodedHeader,
                "Invalid base64 " + X402_PAYMENT_RESPONSE_HEADER + " header",
                SettlementResponse.class,
                "Invalid payment-response JSON payload",
                violations -> new InvalidX402HeaderException("Invalid SettlementResponse object", violations)
        );
    }

    /**
     * Encodes the SettlementResponse into a base64 string.
     *
     * @param settlementResponse The SettlementResponse.
     * @return The encoded string.
     */
    public String encodeSettlementResponse(final SettlementResponse settlementResponse) {
        return encodeHeader(
                settlementResponse,
                violations -> new InvalidX402HeaderException("Invalid SettlementResponse object", violations),
                "Unable to serialize payment-response payload"
        );
    }

    /**
     * Generic helper that decodes a base64 header into a typed object and validates it.
     *
     * @param encodedHeader      the raw base64 header value
     * @param base64ErrorMessage error message used when base64 decoding fails
     * @param type               expected target type
     * @param jsonErrorMessage   error message used when JSON deserialization fails
     * @param onViolations       maps validation violations to a domain-specific runtime exception
     * @param <T>                decoded and validated target type
     * @return the decoded and validated object
     */
    private static <T> T decodeHeader(final String encodedHeader,
                                      final String base64ErrorMessage,
                                      final Class<T> type,
                                      final String jsonErrorMessage,
                                      final Function<Set<ConstraintViolation<T>>, RuntimeException> onViolations) {
        final String decoded;
        try {
            decoded = Base64Util.decode(encodedHeader);
        } catch (IllegalArgumentException e) {
            throw new InvalidX402HeaderException(base64ErrorMessage, e);
        }

        final T object;
        try {
            object = JsonUtil.fromJson(decoded, type);
        } catch (IllegalArgumentException e) {
            throw new InvalidX402HeaderException(jsonErrorMessage, e);
        }

        Set<ConstraintViolation<T>> violations = ValidationUtil.findViolations(object);
        if (!violations.isEmpty()) {
            throw onViolations.apply(violations);
        }

        return object;
    }

    /**
     * Generic helper that validates, serializes, and base64-encodes a header payload.
     *
     * @param object                    object to validate and encode
     * @param onViolations              maps validation violations to a domain-specific runtime exception
     * @param serializationErrorMessage error message used when JSON serialization fails
     * @param <T>                       payload type
     * @return the base64-encoded JSON payload
     */
    private static <T> String encodeHeader(final T object,
                                           final Function<Set<ConstraintViolation<T>>, RuntimeException> onViolations,
                                           final String serializationErrorMessage) {
        Set<ConstraintViolation<T>> violations = ValidationUtil.findViolations(object);
        if (!violations.isEmpty()) {
            throw onViolations.apply(violations);
        }

        final String json;
        try {
            json = JsonUtil.toJson(object);
        } catch (RuntimeException e) {
            throw new InvalidX402HeaderException(serializationErrorMessage, e);
        }

        return Base64Util.encode(json);
    }

}
