package tech.mogami.commons.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * EIP-712 signature annotation.
 * Validates that the value is a well-formed EIP-712 signature: {@code 0x} followed by 130 lowercase hex characters (65 bytes).
 */
@Documented
@Constraint(validatedBy = EIP712SignatureValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface EIP712Signature {

    /**
     * Message to show when the validation fails.
     *
     * @return message
     */
    String message() default "{tech.mogami.commons.validator.EIP712Signature.message}";

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
