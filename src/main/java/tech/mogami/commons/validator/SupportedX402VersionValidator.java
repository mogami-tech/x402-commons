package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jspecify.annotations.Nullable;
import tech.mogami.commons.constant.x402.X402Versions;

import static tech.mogami.commons.constant.x402.X402Versions.X402_SUPPORTED_VERSIONS;

/**
 * Validator for the {@link SupportedX402Version} annotation.
 */
public class SupportedX402VersionValidator implements ConstraintValidator<SupportedX402Version, Integer> {

    @Override
    public final boolean isValid(@Nullable final Integer version, final ConstraintValidatorContext constraintValidatorContext) {
        if (version == null) {
            return false;
        }

        return X402Versions.findByVersion(version)
                .filter(X402_SUPPORTED_VERSIONS::contains)
                .isPresent();
    }

}
