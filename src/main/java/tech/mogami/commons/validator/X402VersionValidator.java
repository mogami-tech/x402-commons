package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import tech.mogami.commons.constant.version.X402Versions;

/**
 * Validator for the {@link X402Version} annotation.
 */
public class X402VersionValidator implements ConstraintValidator<X402Version, Integer> {

    @Override
    public boolean isValid(final Integer version, final ConstraintValidatorContext constraintValidatorContext) {
        if (version == null) {
            return true;
        }

        return X402Versions.findByVersion(version).isPresent();
    }

}
