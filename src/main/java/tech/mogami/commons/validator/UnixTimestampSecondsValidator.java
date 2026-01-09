package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

/**
 * Validator for the {@link UnixTimestampSeconds} annotation.
 */
public class UnixTimestampSecondsValidator implements ConstraintValidator<UnixTimestampSeconds, String> {

    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }

        try {
            long parsedTimestamp = Long.parseLong(value);
            return parsedTimestamp > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
