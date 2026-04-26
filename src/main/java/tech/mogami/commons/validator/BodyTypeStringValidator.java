package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;
import tech.mogami.commons.constant.util.BodyType;

import java.util.Arrays;

/**
 * Validator for the {@link BodyTypeString} annotation.
 */
public class BodyTypeStringValidator implements ConstraintValidator<BodyTypeString, String> {

    @Override
    public final boolean isValid(@Nullable final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return false;
        }

        return Arrays.stream(BodyType.values())
                .anyMatch(bodyType -> bodyType.value().equalsIgnoreCase(value));
    }

}
