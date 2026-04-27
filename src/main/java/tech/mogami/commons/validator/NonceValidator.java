package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

import java.util.regex.Pattern;

import static tech.mogami.commons.constant.blockchain.BlockchainConstants.EVM_ADDRESS_PREFIX;

/**
 * Validator for the {@link Nonce} annotation.
 * A valid nonce is a 32-byte random value encoded as a hex string with the {@code 0x} prefix,
 * resulting in exactly 66 characters (2 for the prefix + 64 hexadecimal characters).
 */
public class NonceValidator implements ConstraintValidator<Nonce, String> {

    /** Expected total string length: "0x" prefix (2) + 32 bytes encoded as hex (64). */
    private static final int NONCE_STRING_LENGTH = 66;

    /** Pattern matching exactly 64 hexadecimal characters. */
    private static final Pattern HEX_PATTERN = Pattern.compile("^[0-9a-fA-F]{64}$");

    @Override
    public final boolean isValid(@Nullable final String nonce, final ConstraintValidatorContext context) {
        if (StringUtils.isBlank(nonce)) {
            return true;
        }

        if (nonce.length() != NONCE_STRING_LENGTH || !nonce.startsWith(EVM_ADDRESS_PREFIX)) {
            return false;
        }

        return HEX_PATTERN.matcher(nonce.substring(EVM_ADDRESS_PREFIX.length())).matches();
    }

}
