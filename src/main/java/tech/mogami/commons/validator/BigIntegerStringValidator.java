package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

import java.math.BigInteger;

/**
 * Validator for the {@link BigIntegerString} annotation.
 */
public class BigIntegerStringValidator implements ConstraintValidator<BigIntegerString, String> {

    @Override
    public final boolean isValid(@Nullable final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }

        try {
            new BigInteger(StringUtils.trim(value));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
