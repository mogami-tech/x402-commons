package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

import java.util.regex.Pattern;

import static tech.mogami.commons.constant.blockchain.BlockchainConstants.BLOCKCHAIN_ADDRESS_LENGTH;
import static tech.mogami.commons.constant.blockchain.BlockchainConstants.BLOCKCHAIN_ADDRESS_PREFIX;

/**
 * Validator for the {@link PayTo} annotation.
 *
 * <p>Accepts:
 * <ul>
 *   <li>{@code null} and blank strings (presence is enforced separately via {@code @NotBlank})</li>
 *   <li>EVM addresses: {@code 0x} prefix followed by exactly 40 hexadecimal characters</li>
 *   <li>Non-EVM identifiers: alphanumeric characters, hyphens, and underscores
 *       (covers role constants such as {@code merchant} and non-EVM wallet addresses
 *       such as Solana base58 public keys)</li>
 * </ul>
 */
public class PayToValidator implements ConstraintValidator<PayTo, String> {

    /** Pattern for EVM address hex body: exactly 40 hex characters. */
    private static final Pattern EVM_HEX_PATTERN = Pattern.compile("^[0-9a-fA-F]{40}$");

    /** Pattern for non-EVM identifiers: letters, digits, hyphens and underscores. */
    private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("^[A-Za-z0-9_-]+$");

    @Override
    public final boolean isValid(@Nullable final String payTo, final ConstraintValidatorContext context) {
        if (StringUtils.isBlank(payTo)) {
            return true;
        }

        if (payTo.startsWith(BLOCKCHAIN_ADDRESS_PREFIX)) {
            return payTo.length() == BLOCKCHAIN_ADDRESS_LENGTH
                    && EVM_HEX_PATTERN.matcher(payTo.substring(BLOCKCHAIN_ADDRESS_PREFIX.length())).matches();
        }

        return IDENTIFIER_PATTERN.matcher(payTo).matches();
    }

}
