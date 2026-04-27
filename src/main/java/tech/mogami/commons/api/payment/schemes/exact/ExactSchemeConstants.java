package tech.mogami.commons.api.payment.schemes.exact;

import lombok.experimental.UtilityClass;

/**
 * Constants for the "exact" payment scheme.
 *
 * <p>The exact scheme uses EIP-3009 (transferWithAuthorization) to enable gasless transfers
 * of specific ERC-20 token amounts. It requires an EIP-712 domain, whose parameters are
 * carried in the {@code extra} map of {@code PaymentRequirements}.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused"})
public class ExactSchemeConstants {

    /** Payment scheme identifier, as used in {@code PaymentRequirements.scheme} and {@code PaymentPayload.scheme}. */
    public static final String EXACT_SCHEME_NAME = "exact";

    /**
     * Key for the EIP-712 domain name in {@code PaymentRequirements.extra}.
     * Maps to the {@code name} field of the EIP-712 domain separator.
     * Example value: {@code "USD Coin"}.
     */
    public static final String EXACT_SCHEME_PARAMETER_NAME = "name";

    /**
     * Key for the EIP-712 domain version in {@code PaymentRequirements.extra}.
     * Maps to the {@code version} field of the EIP-712 domain separator.
     * Example value: {@code "2"}.
     */
    public static final String EXACT_SCHEME_PARAMETER_VERSION = "version";

}
