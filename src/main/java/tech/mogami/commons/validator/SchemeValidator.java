package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;
import tech.mogami.commons.api.payment.schemes.Schemes;

/**
 * Validator for the {@link Scheme} annotation.
 */
public class SchemeValidator implements ConstraintValidator<Scheme, String> {

    @Override
    public final boolean isValid(@Nullable final String scheme, final ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(scheme)) {
            return true;
        }

        return Schemes.findByName(scheme).isPresent();
    }

}
