package tech.mogami.commons.constant;

import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

/**
 * The x402 protocol defines standard error codes that may be returned by facilitators or resource servers.
 */
public enum X402Error {

    /** Client does not have enough tokens to complete the payment. */
    INSUFFICIENT_FUNDS("insufficient_funds",
            "Client does not have enough tokens to complete the payment"),

    /** Payment authorization is not yet valid (before validAfter timestamp). */
    INVALID_EXACT_EVM_PAYLOAD_AUTHORIZATION_VALID_AFTER("invalid_exact_evm_payload_authorization_valid_after",
            "Payment authorization is not yet valid (before validAfter timestamp)"),

    /** Payment authorization has expired (after validBefore timestamp). */
    INVALID_EXACT_EVM_PAYLOAD_AUTHORIZATION_VALID_BEFORE("invalid_exact_evm_payload_authorization_valid_before",
            "Payment authorization has expired (after validBefore timestamp)"),

    /** The payment amount is insufficient for the required payment. */
    INVALID_EXACT_EVM_PAYLOAD_AUTHORIZATION_VALUE("invalid_exact_evm_payload_authorization_value",
            "Payment amount is insufficient for the required payment"),

    /** Payment authorization signature is invalid or improperly signed. */
    INVALID_EXACT_EVM_PAYLOAD_SIGNATURE("invalid_exact_evm_payload_signature",
            "Payment authorization signature is invalid or improperly signed"),

    /** The recipient address does not match payment requirements. */
    INVALID_EXACT_EVM_PAYLOAD_RECIPIENT_MISMATCH("invalid_exact_evm_payload_recipient_mismatch",
            "Recipient address does not match payment requirements"),

    /** Specified blockchain network is not supported. */
    INVALID_NETWORK("invalid_network",
            "Specified blockchain network is not supported"),

    /** Payment payload is malformed or contains invalid data. */
    INVALID_PAYLOAD("invalid_payload",
            "Payment payload is malformed or contains invalid data"),

    /** Payment requirements object is invalid or malformed. */
    INVALID_PAYMENT_REQUIREMENTS("invalid_payment_requirements",
            "Payment requirements object is invalid or malformed"),

    /** Specified payment scheme is not supported. */
    INVALID_SCHEME("invalid_scheme",
            "Specified payment scheme is not supported"),

    /** The payment scheme is not supported by the facilitator. */
    UNSUPPORTED_SCHEME("unsupported_scheme",
            "Payment scheme is not supported by the facilitator"),

    /** Protocol version is not supported. */
    INVALID_X402_VERSION("invalid_x402_version",
            "Protocol version is not supported"),

    /** Blockchain transaction failed or was rejected. */
    INVALID_TRANSACTION_STATE("invalid_transaction_state",
            "Blockchain transaction failed or was rejected"),

    /** Unexpected error occurred during payment verification. */
    UNEXPECTED_VERIFY_ERROR("unexpected_verify_error",
            "Unexpected error occurred during payment verification"),

    /** Unexpected error occurred during the payment settlement. */
    UNEXPECTED_SETTLE_ERROR("unexpected_settle_error",
            "Unexpected error occurred during payment settlement"),

    /** Unknown or unmapped error (Not in the specs - Added by Mogami in case of). */
    UNKNOWN("unknown_error",
            "Unknown or unmapped error");

    /**
     * Map of all codes to enum constants for fast lookup.
     */
    public static final Map<String, X402Error> ALL_X402_ERRORS = Map.ofEntries(
            Map.entry(INSUFFICIENT_FUNDS.code, INSUFFICIENT_FUNDS),
            Map.entry(INVALID_EXACT_EVM_PAYLOAD_AUTHORIZATION_VALID_AFTER.code, INVALID_EXACT_EVM_PAYLOAD_AUTHORIZATION_VALID_AFTER),
            Map.entry(INVALID_EXACT_EVM_PAYLOAD_AUTHORIZATION_VALID_BEFORE.code, INVALID_EXACT_EVM_PAYLOAD_AUTHORIZATION_VALID_BEFORE),
            Map.entry(INVALID_EXACT_EVM_PAYLOAD_AUTHORIZATION_VALUE.code, INVALID_EXACT_EVM_PAYLOAD_AUTHORIZATION_VALUE),
            Map.entry(INVALID_EXACT_EVM_PAYLOAD_SIGNATURE.code, INVALID_EXACT_EVM_PAYLOAD_SIGNATURE),
            Map.entry(INVALID_EXACT_EVM_PAYLOAD_RECIPIENT_MISMATCH.code, INVALID_EXACT_EVM_PAYLOAD_RECIPIENT_MISMATCH),
            Map.entry(INVALID_NETWORK.code, INVALID_NETWORK),
            Map.entry(INVALID_PAYLOAD.code, INVALID_PAYLOAD),
            Map.entry(INVALID_PAYMENT_REQUIREMENTS.code, INVALID_PAYMENT_REQUIREMENTS),
            Map.entry(INVALID_SCHEME.code, INVALID_SCHEME),
            Map.entry(UNSUPPORTED_SCHEME.code, UNSUPPORTED_SCHEME),
            Map.entry(INVALID_X402_VERSION.code, INVALID_X402_VERSION),
            Map.entry(INVALID_TRANSACTION_STATE.code, INVALID_TRANSACTION_STATE),
            Map.entry(UNEXPECTED_VERIFY_ERROR.code, UNEXPECTED_VERIFY_ERROR),
            Map.entry(UNEXPECTED_SETTLE_ERROR.code, UNEXPECTED_SETTLE_ERROR),
            Map.entry(UNKNOWN.code, UNKNOWN)
    );

    /** Error code. */
    private final String code;

    /** Error default message. */
    private final String defaultMessage;

    /**
     * Constructor.
     *
     * @param newCode           the error code
     * @param newDefaultMessage the default message
     */
    X402Error(final String newCode, final String newDefaultMessage) {
        this.code = StringUtils.lowerCase(newCode);
        this.defaultMessage = newDefaultMessage;
    }

    /**
     * Get the enum constant for the given error code.
     *
     * @param code the error code
     * @return the enum constant, or UNKNOWN if not found
     */
    public static X402Error fromCode(@Nullable final String code) {
        return Optional.ofNullable(code)
                .map(StringUtils::lowerCase)
                .map(ALL_X402_ERRORS::get)
                .orElse(UNKNOWN);
    }

    /**
     * Returns the error code.
     *
     * @return error code
     */
    public String code() {
        return code;
    }

    /**
     * Returns the default message.
     *
     * @return default message
     */
    public String defaultMessage() {
        return defaultMessage;
    }

}
