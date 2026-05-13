package tech.mogami.commons.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Pay-to annotation.
 *
 * <p>Accepts {@code null} and blank strings as valid (use {@code @NotBlank} to enforce presence).
 * Validates that the value is either:
 * <ul>
 *   <li>A valid EVM wallet address: {@code 0x} prefix followed by exactly 40 hex characters
 *       (case-insensitive)</li>
 *   <li>A valid recipient identifier (role constant or non-EVM address): a non-empty string
 *       composed only of alphanumeric characters, hyphens and underscores (e.g., {@code merchant},
 *       {@code CKPKJWNdJEqa81x7CkZ14BVPiY6y16Sxs7owznqtWYp5})</li>
 * </ul>
 */
@Documented
@Constraint(validatedBy = PayToValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface PayTo {

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
