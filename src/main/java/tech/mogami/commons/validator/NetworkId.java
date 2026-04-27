package tech.mogami.commons.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Network id annotation.
 *
 * <p>Accepts {@code null} and blank strings as valid (use {@code @NotBlank} to enforce presence).
 * Validates that the value matches a known network identifier (e.g. {@code eip155:84532},
 * case-insensitive).
 */
@Documented
@Constraint(validatedBy = NetworkIdValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface NetworkId {

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
