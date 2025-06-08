package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import tech.mogami.commons.header.payment.schemes.Schemes;

/**
 * Scheme validator.
 */
public class SchemeValidator implements ConstraintValidator<Scheme, String> {

    @Override
    public final boolean isValid(final String scheme, final ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(scheme)) {
            return true;
        }

        return Schemes.findByName(scheme).isPresent();
    }

}
