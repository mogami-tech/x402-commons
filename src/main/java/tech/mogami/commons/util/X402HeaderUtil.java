package tech.mogami.commons.util;

import jakarta.validation.ConstraintViolation;
import lombok.experimental.UtilityClass;
import tech.mogami.commons.api.facilitator.settle.SettlementResponse;
import tech.mogami.commons.exception.InvalidX402HeaderException;
import tech.mogami.commons.exception.InvalidX402PaymentRequiredException;
import tech.mogami.commons.payment.PaymentPayload;
import tech.mogami.commons.payment.PaymentRequired;

import java.util.Set;

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
        // We decode it.
        final String decodedPaymentRequired;
        try {
            decodedPaymentRequired = Base64Util.decode(encodedHeader);
        } catch (IllegalArgumentException e) {
            throw new InvalidX402HeaderException("Invalid base64 payment-required header", e);
        }

        // We transform the encoded value into a PaymentRequired object.
        final PaymentRequired paymentRequired;
        try {
            paymentRequired = JsonUtil.fromJson(decodedPaymentRequired, PaymentRequired.class);
        } catch (final IllegalArgumentException e) {
            throw new InvalidX402HeaderException("Invalid payment-required JSON payload", e);
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
    public String encodePaymentRequired(final PaymentRequired paymentRequired) {
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
            throw new InvalidX402HeaderException("Unable to serialize payment-required payload", e);
        }

        // Return encoded in base64.
        return Base64Util.encode(json);
    }

    /**
     * Decodes the PaymentPayload from the given encoded payload.
     *
     * @param encodedHeader The encoded header.
     * @return The PaymentPayload.
     */
    public PaymentPayload decodePaymentPayload(final String encodedHeader) {
        // We decode it.
        final String decodedPaymentPayload;
        try {
            decodedPaymentPayload = Base64Util.decode(encodedHeader);
        } catch (IllegalArgumentException e) {
            throw new InvalidX402HeaderException("Invalid base64 payment payload header", e);
        }

        // We transform the encoded value into a PaymentPayload object.
        final PaymentPayload paymentPayload;
        try {
            paymentPayload = JsonUtil.fromJson(decodedPaymentPayload, PaymentPayload.class);
        } catch (final IllegalArgumentException e) {
            throw new InvalidX402HeaderException("Invalid x402 payment payload", e);
        }

        // We validate the PaymentPayload object.
        Set<ConstraintViolation<PaymentPayload>> violations = ValidationUtil.findViolations(paymentPayload);
        if (!violations.isEmpty()) {
            throw new InvalidX402HeaderException("Invalid payment payload object");
        }

        return paymentPayload;
    }

    /**
     * Encodes the PaymentPayload into a base64 string.
     *
     * @param paymentPayload The PaymentPayload.
     * @return The encoded string.
     */
    public String encodePaymentPayload(final PaymentPayload paymentPayload) {
        // Check violations.
        Set<ConstraintViolation<PaymentPayload>> violations = ValidationUtil.findViolations(paymentPayload);
        if (!violations.isEmpty()) {
            throw new InvalidX402HeaderException("Invalid payment payload object");
        }

        // Transform to JSON.
        final String json;
        try {
            json = JsonUtil.toJson(paymentPayload);
        } catch (RuntimeException e) {
            throw new InvalidX402HeaderException("Unable to serialize payment payload", e);
        }

        // Return encoded in base64.
        return Base64Util.encode(json);
    }

    /**
     * Decodes the SettlementResponse from the given encoded header.
     *
     * @param encodedHeader The encoded header.
     * @return The SettlementResponse.
     */
    public SettlementResponse decodeSettlementResponse(final String encodedHeader) {
        // We decode it.
        final String decodedSettlementResponse;
        try {
            decodedSettlementResponse = Base64Util.decode(encodedHeader);
        } catch (IllegalArgumentException e) {
            throw new InvalidX402HeaderException("Invalid base64 payment-response header", e);
        }

        // We transform the encoded value into a SettlementResponse object.
        final SettlementResponse settlementResponse;
        try {
            settlementResponse = JsonUtil.fromJson(decodedSettlementResponse, SettlementResponse.class);
        } catch (final IllegalArgumentException e) {
            throw new InvalidX402HeaderException("Invalid payment-response JSON payload", e);
        }

        Set<ConstraintViolation<SettlementResponse>> violations = ValidationUtil.findViolations(settlementResponse);
        if (!violations.isEmpty()) {
            throw new InvalidX402HeaderException("Invalid SettlementResponse object");
        }

        return settlementResponse;
    }

    /**
     * Encodes the SettlementResponse into a base64 string.
     *
     * @param settlementResponse The SettlementResponse.
     * @return The encoded string.
     */
    public String encodeSettlementResponse(final SettlementResponse settlementResponse) {
        // Check violations.
        Set<ConstraintViolation<SettlementResponse>> violations = ValidationUtil.findViolations(settlementResponse);
        if (!violations.isEmpty()) {
            throw new InvalidX402HeaderException("Invalid SettlementResponse object");
        }

        // Transform to JSON.
        final String json;
        try {
            json = JsonUtil.toJson(settlementResponse);
        } catch (RuntimeException e) {
            throw new InvalidX402HeaderException("Unable to serialize payment-response payload", e);
        }

        // Return encoded in base64.
        return Base64Util.encode(json);
    }

}
