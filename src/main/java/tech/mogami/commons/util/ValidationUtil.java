package tech.mogami.commons.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.experimental.UtilityClass;

import java.util.Set;

/**
 * Utility class for validation operations.
 */
@UtilityClass
@SuppressWarnings({"HideUtilityClassConstructor", "unused"})
public class ValidationUtil {

    /** Validator instance for performing validations. */
    @SuppressWarnings({"resource"})
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Finds constraint violations for the given object.
     *
     * @param object the object to validate
     * @param <T>    the type of the object
     * @return a set of constraint violations
     */
    public static <T> Set<ConstraintViolation<T>> findViolations(final T object) {
        return VALIDATOR.validate(object);
    }

}
