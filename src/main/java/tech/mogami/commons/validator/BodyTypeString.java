package tech.mogami.commons.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Body type as a String annotation.
 *
 * <p>{@code null} and blank strings are treated as <strong>invalid</strong>; use this annotation
 * on required fields only, or combine with {@code @Nullable} handling as needed.
 * Valid values are defined by {@link tech.mogami.commons.constant.util.BodyType} (case-insensitive).
 */
@Documented
@Constraint(validatedBy = BodyTypeStringValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface BodyTypeString {

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
