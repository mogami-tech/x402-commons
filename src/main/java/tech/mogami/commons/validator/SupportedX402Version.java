package tech.mogami.commons.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Supported x402 version annotation — validates that the version both exists and is actively supported.
 *
 * <p>{@code null} is treated as <strong>invalid</strong>; use {@code @NotNull} to make this explicit.
 * Use {@link ExistingX402Version} instead when any known version (including legacy ones) should be accepted.
 */
@Documented
@Constraint(validatedBy = SupportedX402VersionValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface SupportedX402Version {

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
