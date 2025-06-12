package tech.mogami.commons.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * X402Version annotation.
 */
@Documented
@Constraint(validatedBy = SchemeValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface X402Version {

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
