package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

import static tech.mogami.commons.constant.blockchain.BlockchainConstants.BLOCKCHAIN_ADDRESS_PREFIX;
import static tech.mogami.commons.constant.blockchain.BlockchainConstants.EIP712_SIGNATURE_LENGTH;

/**
 * Validator for the {@link EIP712Signature} annotation.
 */
public class EIP712SignatureValidator implements ConstraintValidator<EIP712Signature, String> {

    @Override
    public final boolean isValid(@Nullable final String signature, final ConstraintValidatorContext context) {
        if (StringUtils.isBlank(signature)) {
            return true;
        }

        if (signature.length() != EIP712_SIGNATURE_LENGTH || !signature.startsWith(BLOCKCHAIN_ADDRESS_PREFIX)) {
            return false;
        }

        return signature.substring(BLOCKCHAIN_ADDRESS_PREFIX.length()).matches("^[0-9a-fA-F]+$");
    }

}
