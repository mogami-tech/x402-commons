package tech.mogami.commons.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.experimental.UtilityClass;

import java.util.Set;

/**
 * Utility class for validation operations.
 */
@UtilityClass
@SuppressWarnings({"HideUtilityClassConstructor", "unused"})
public class ValidationUtil {

    /** Validator instance for performing validations. */
    private static final Validator VALIDATOR;

    static {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            VALIDATOR = factory.getValidator();
        }
    }

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
