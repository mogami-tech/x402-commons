package tech.mogami.commons.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * HTTP method as a String annotation.
 */
@Documented
@Constraint(validatedBy = HttpMethodStringValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface HttpMethodString {

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
