package tech.mogami.commons.test.x402;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.constant.x402.X402Error;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.mogami.commons.constant.x402.X402Error.ALL_X402_ERRORS;
import static tech.mogami.commons.constant.x402.X402Error.INSUFFICIENT_FUNDS;
import static tech.mogami.commons.constant.x402.X402Error.INVALID_EXACT_EVM_PAYLOAD_AUTHORIZATION_VALUE;
import static tech.mogami.commons.constant.x402.X402Error.INVALID_UPTO_EVM_PAYLOAD_SETTLEMENT_EXCEEDS_AMOUNT;

@DisplayName("X402 error tests")
public class X402ErrorTest {

    @Test
    @DisplayName("X402 error list should contain 17 errors")
    void errorList() {
        assertThat(ALL_X402_ERRORS).hasSize(17);
    }

    @Test
    @DisplayName("X402 error values should be correct")
    void errorValue() {
        assertThat(INSUFFICIENT_FUNDS)
                .returns("insufficient_funds", X402Error::code)
                .returns("Client does not have enough tokens to complete the payment", X402Error::defaultMessage);

        // Spec §9: code must include _mismatch suffix.
        assertThat(INVALID_EXACT_EVM_PAYLOAD_AUTHORIZATION_VALUE)
                .returns("invalid_exact_evm_payload_authorization_value_mismatch", X402Error::code);

        // Spec scheme_upto_evm §4: upto-specific error code.
        assertThat(INVALID_UPTO_EVM_PAYLOAD_SETTLEMENT_EXCEEDS_AMOUNT)
                .returns("invalid_upto_evm_payload_settlement_exceeds_amount", X402Error::code)
                .returns("Attempted to settle for more than the authorized amount", X402Error::defaultMessage);
    }

    @Test
    @DisplayName("X402 error fromCode() should return correct error for existing and non-existing codes")
    void fromCode() {
        // Null value.
        assertThat(X402Error.fromCode(null))
                .returns("unknown_error", X402Error::code)
                .returns("Unknown or unmapped error", X402Error::defaultMessage);

        // Non existing error code.
        assertThat(X402Error.fromCode("NON_EXISTING_X402_ERROR_CODE"))
                .returns("unknown_error", X402Error::code)
                .returns("Unknown or unmapped error", X402Error::defaultMessage);

        // Existing value (string lower case).
        assertThat(X402Error.fromCode("insufficient_funds"))
                .returns("insufficient_funds", X402Error::code)
                .returns("Client does not have enough tokens to complete the payment", X402Error::defaultMessage);

        // Existing value (not all lower case).
        assertThat(X402Error.fromCode("insufficient_FUNDS"))
                .returns("insufficient_funds", X402Error::code)
                .returns("Client does not have enough tokens to complete the payment", X402Error::defaultMessage);
    }

}
