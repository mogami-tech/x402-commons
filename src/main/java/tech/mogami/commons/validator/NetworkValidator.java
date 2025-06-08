package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import tech.mogami.commons.constant.network.Networks;

/**
 * Validator for the {@link Network} annotation.
 */
public class NetworkValidator implements ConstraintValidator<Network, String> {

    @Override
    public final boolean isValid(final String network, final ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(network)) {
            return true;
        }

        return Networks.findByName(network).isPresent();
    }

}
