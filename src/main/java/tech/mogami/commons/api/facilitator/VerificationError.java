package tech.mogami.commons.api.facilitator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Facilitator verification errors.
 */
@RequiredArgsConstructor
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused"})
public enum VerificationError {

    /** Undefined error. */
    UNDEFINED("undefined"),

    /** Unsupported scheme. */
    UNSUPPORTED_SCHEME("unsupported_scheme"),

    /** Invalid network. */
    INVALID_NETWORK("invalid_network"),

    /** Invalid signature. */
    INVALID_EXACT_SIGNATURE("invalid_exact_evm_payload_signature"),

    /** Invalid exact EVM payload recipient mismatch. */
    INVALID_EXACT_EVM_PAYLOAD_RECIPIENT_MISMATCH("invalid_exact_evm_payload_recipient_mismatch"),

    /** Deadline on permit isn't far enough in the future. */
    INVALID_EXACT_EVM_PAYLOAD_VALID_BEFORE("invalid_exact_evm_payload_authorization_valid_before"),

    /** Deadline on permit is in the futur. */
    INVALID_EXACT_EVM_PAYLOAD_VALID_AFTER("invalid_exact_evm_payload_authorization_valid_after"),

    /** Insufficient funds. */
    INSUFFICIENT_FUNDS("insufficient_funds"),

    /** Value in payload is enough to cover paymentRequirements.maxAmountRequired. */
    INSUFFICIENT_PAYMENT_VALUE("invalid_exact_evm_payload_authorization_value");

    /** Getter for error code. */
    @Getter
    private final String errorCode;

}
