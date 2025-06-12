package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import static tech.mogami.commons.constant.BlockchainConstants.BLOCKCHAIN_ADDRESS_LENGTH;
import static tech.mogami.commons.constant.BlockchainConstants.BLOCKCHAIN_ADDRESS_PREFIX;

/**
 * Validator for the {@link BlockchainAddress} annotation.
 */
public class BlockchainAddressValidator implements ConstraintValidator<BlockchainAddress, String> {

    @Override
    public final boolean isValid(final String blockchainAddress, final ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(blockchainAddress)) {
            return true;
        }

        // Check the length of the address and if it starts with "0x".
        if (blockchainAddress.length() != BLOCKCHAIN_ADDRESS_LENGTH
                || !blockchainAddress.startsWith(BLOCKCHAIN_ADDRESS_PREFIX)) {
            return false;
        }

        // Check if the address contains only hexadecimal characters.
        return blockchainAddress.substring(BLOCKCHAIN_ADDRESS_PREFIX.length()).matches("^[0-9a-fA-F]+$");
    }

}
