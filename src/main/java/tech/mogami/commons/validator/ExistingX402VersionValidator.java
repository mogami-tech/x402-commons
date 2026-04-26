package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jspecify.annotations.Nullable;
import tech.mogami.commons.constant.x402.X402Versions;

/**
 * Validator for the {@link ExistingX402Version} annotation.
 */
public class ExistingX402VersionValidator implements ConstraintValidator<ExistingX402Version, Integer> {

    @Override
    public final boolean isValid(@Nullable final Integer version, final ConstraintValidatorContext constraintValidatorContext) {
        if (version == null) {
            return true;
        }

        return X402Versions.findByVersion(version).isPresent();
    }

}
