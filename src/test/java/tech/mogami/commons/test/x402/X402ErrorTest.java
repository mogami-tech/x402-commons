package tech.mogami.commons.test.x402;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.core.methods.response.EthCompileSolidity;
import tech.mogami.commons.constant.X402Error;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.mogami.commons.constant.X402Error.ALL_X402_ERRORS;
import static tech.mogami.commons.constant.X402Error.INSUFFICIENT_FUNDS;

@DisplayName("X402 error tests")
public class X402ErrorTest {

    @Test
    @DisplayName("X402 error list should contain 16 errors")
    void errorList() {
        assertThat(ALL_X402_ERRORS).hasSize(16);
    }

    @Test
    @DisplayName("X402 error values should be correct")
    void errorValue() {
        assertThat(INSUFFICIENT_FUNDS)
                .returns("insufficient_funds", X402Error::code)
                .returns("Client does not have enough tokens to complete the payment", X402Error::defaultMessage);
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
