package tech.mogami.commons.test.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.validator.EIP712SignatureValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("EIP-712 signature validator tests")
class EIP712SignatureValidatorTest {

    EIP712SignatureValidator validator = new EIP712SignatureValidator();

    @Test
    @DisplayName("Should return true for null signature")
    void nullSignature() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for blank signature")
    void blankSignature() {
        assertThat(validator.isValid("   ", null)).isTrue();
    }

    @Test
    @DisplayName("Should return true for valid signature")
    void validSignature() {
        assertThat(validator.isValid("0x2d6a7588d6acca505cbf0d9a4a227e0c52c6c34008c8e8986a1283259764173608a2ce6496642e377d6da8dbbf5836e9bd15092f9ecab05ded3d6293af148b571c", null)).isTrue();
    }

    @Test
    @DisplayName("Should return false for signature without '0x' prefix")
    void missingPrefix() {
        assertThat(validator.isValid("002d6a7588d6acca505cbf0d9a4a227e0c52c6c34008c8e8986a1283259764173608a2ce6496642e377d6da8dbbf5836e9bd15092f9ecab05ded3d6293af148b571c", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for signature that is too short")
    void tooShort() {
        assertThat(validator.isValid("0x2d6a7588d6acca505cbf0d9a4a227e0c52c6c34008c8e8986a128325976417360", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for signature that is too long")
    void tooLong() {
        assertThat(validator.isValid("0x2d6a7588d6acca505cbf0d9a4a227e0c52c6c34008c8e8986a1283259764173608a2ce6496642e377d6da8dbbf5836e9bd15092f9ecab05ded3d6293af148b571c00", null)).isFalse();
    }

    @Test
    @DisplayName("Should return false for signature with invalid hex character")
    void invalidHexCharacter() {
        assertThat(validator.isValid("0x2d6a7588d6acca505cbf0d9a4a227e0c52c6c34008c8e8986a1283259764173608a2ce6496642e377d6da8dbbf5836e9bd15092f9ecab05ded3d6293af148b571z", null)).isFalse();
    }

}
