package tech.mogami.commons.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * x402 version annotation — validates that the version exists in the known version registry.
 *
 * <p>Accepts {@code null} as valid (use {@code @NotNull} to enforce presence).
 * Use {@link SupportedX402Version} instead when only actively-supported versions should be accepted.
 */
@Documented
@Constraint(validatedBy = ExistingX402VersionValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface ExistingX402Version {

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
