package tech.mogami.commons.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Scheme annotation.
 *
 * <p>Accepts {@code null} and blank strings as valid (use {@code @NotBlank} to enforce presence).
 * Validates that the value matches a known payment scheme name (case-insensitive).
 */
@Documented
@Constraint(validatedBy = SchemeValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface Scheme {

    /**
     * Message to show when the validation fails.
     *
     * @return message
     */
    String message();

    /**
     * Groups.
     *
     * @return groups
     */
    Class<?>[] groups() default {};

    /**
     * Payload.
     *
     * @return payload
     */
    Class<?>[] payload() default {};

}
