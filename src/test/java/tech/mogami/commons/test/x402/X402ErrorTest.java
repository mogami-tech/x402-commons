package tech.mogami.commons.test.x402;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.constant.X402Error;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("X402 error tests")
public class X402ErrorTest {

    @Test
    void errorCodeList() {
        assertThat(X402Error.ALL_X402_ERRORS).hasSize(16);
    }

    @Test
    void enumValue() {
        assertThat(X402Error.INSUFFICIENT_FUNDS)
                .satisfies(code -> {
                    assertThat(code.getCode()).isEqualTo("insufficient_funds");
                    assertThat(code.getDefaultMessage()).isEqualTo("Client does not have enough tokens to complete the payment");
                });
    }

    @Test
    void fromCode() {
        // Null value.
        assertThat(X402Error.fromCode(null))
                .satisfies(code -> {
                    assertThat(code.getCode()).isEqualTo("unknown_error");
                    assertThat(code.getDefaultMessage()).isEqualTo("Unknown or unmapped error");
                });
        // Non existing error code.
        assertThat(X402Error.fromCode("NON_EXISTING_X402_ERROR_CODE"))
                .satisfies(code -> {
                    assertThat(code.getCode()).isEqualTo("unknown_error");
                    assertThat(code.getDefaultMessage()).isEqualTo("Unknown or unmapped error");
                });
        // Existing value (string lower case).
        assertThat(X402Error.fromCode("insufficient_funds"))
                .satisfies(code -> {
                    assertThat(code.getCode()).isEqualTo("insufficient_funds");
                    assertThat(code.getDefaultMessage()).isEqualTo("Client does not have enough tokens to complete the payment");
                });
        // Existing value (not all lower case).
        assertThat(X402Error.fromCode("insufficient_FUNDS"))
                .satisfies(code -> {
                    assertThat(code.getCode()).isEqualTo("insufficient_funds");
                    assertThat(code.getDefaultMessage()).isEqualTo("Client does not have enough tokens to complete the payment");
                });
    }

}
