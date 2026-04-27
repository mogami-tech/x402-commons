package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

import java.util.regex.Pattern;

import static tech.mogami.commons.constant.blockchain.BlockchainConstants.BLOCKCHAIN_ADDRESS_LENGTH;
import static tech.mogami.commons.constant.blockchain.BlockchainConstants.BLOCKCHAIN_ADDRESS_PREFIX;

/**
 * Validator for the {@link BlockchainAddress} annotation.
 */
public class BlockchainAddressValidator implements ConstraintValidator<BlockchainAddress, String> {

    /** Pattern matching exactly 40 hexadecimal characters (20 bytes). */
    private static final Pattern HEX_PATTERN = Pattern.compile("^[0-9a-fA-F]+$");

    @Override
    public final boolean isValid(@Nullable final String blockchainAddress, final ConstraintValidatorContext context) {
        if (StringUtils.isBlank(blockchainAddress)) {
            return true;
        }

        // Check the length of the address and if it starts with "0x".
        if (blockchainAddress.length() != BLOCKCHAIN_ADDRESS_LENGTH
                || !blockchainAddress.startsWith(BLOCKCHAIN_ADDRESS_PREFIX)) {
            return false;
        }

        // Check if the address contains only hexadecimal characters.
        return HEX_PATTERN.matcher(blockchainAddress.substring(BLOCKCHAIN_ADDRESS_PREFIX.length())).matches();
    }

}
