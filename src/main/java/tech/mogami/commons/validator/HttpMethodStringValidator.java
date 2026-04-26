package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

import static tech.mogami.commons.constant.util.HttpMethod.ALLOWED_METHODS;

/**
 * Validator for the {@link HttpMethodString} annotation.
 */
public class HttpMethodStringValidator implements ConstraintValidator<HttpMethodString, String> {

    @Override
    public final boolean isValid(@Nullable final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return false;
        }

        return ALLOWED_METHODS.stream().anyMatch(method -> method.name().equalsIgnoreCase(value));
    }

}
