package tech.mogami.commons.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Blockchain address annotation.
 *
 * <p>Accepts {@code null} and blank strings as valid (use {@code @NotBlank} to enforce presence).
 * Validates that the value is a well-formed EVM address: {@code 0x} prefix followed by exactly
 * 40 hex characters (case-insensitive).
 */
@Documented
@Constraint(validatedBy = BlockchainAddressValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface BlockchainAddress {

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
