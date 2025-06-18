package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

/**
 * Validator for the {@link BigIntegerString} annotation.
 */
public class BigIntegerStringValidator implements ConstraintValidator<BigIntegerString, String> {

    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }
        try {
            new BigInteger(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
