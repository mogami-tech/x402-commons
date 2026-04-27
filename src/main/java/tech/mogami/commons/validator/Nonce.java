package tech.mogami.commons.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Nonce annotation.
 * Validates that a value is a 32-byte random nonce to prevent replay attacks.
 * Expected format: {@code 0x} followed by exactly 64 hexadecimal characters.
 */
@Documented
@Constraint(validatedBy = NonceValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface Nonce {

    /**
     * Message to show when the validation fails.
     *
     * @return message
     */
    String message() default "{tech.mogami.commons.validator.Nonce.message}";

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
