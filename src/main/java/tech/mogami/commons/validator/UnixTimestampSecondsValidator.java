package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

/**
 * Validator for the {@link UnixTimestampSeconds} annotation.
 */
public class UnixTimestampSecondsValidator implements ConstraintValidator<UnixTimestampSeconds, String> {

    @Override
    public final boolean isValid(@Nullable final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }

        try {
            long parsedTimestamp = Long.parseLong(StringUtils.trim(value));
            return parsedTimestamp > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
