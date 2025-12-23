package tech.mogami.commons.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import tech.mogami.commons.constant.network.Networks;

/**
 * Validator for the {@link NetworkId} annotation.
 */
public class NetworkIdValidator implements ConstraintValidator<NetworkId, String> {

    @Override
    public final boolean isValid(final String networkId, final ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(networkId)) {
            return true;
        }

        return Networks.findByNetworkId(networkId).isPresent();
    }

}
