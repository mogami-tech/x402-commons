package tech.mogami.commons.api.payment.schemes.upto;

import lombok.experimental.UtilityClass;

/**
 * Constants for the "upto" payment scheme.
 *
 * <p>The upto scheme uses Permit2 ({@code permitWitnessTransferFrom}) to enable usage-based payments
 * where the client authorizes a maximum amount and the facilitator settles the actual amount consumed.
 * It requires a Permit2 authorization, whose parameters are carried in the {@code extra} map of
 * {@code PaymentRequirements}.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused"})
public class UptoSchemeConstants {

    /** Payment scheme identifier, as used in {@code PaymentRequirements.scheme} and {@code PaymentPayload.scheme}. */
    public static final String UPTO_SCHEME_NAME = "upto";

    /**
     * Key for the EIP-712 domain name in {@code PaymentRequirements.extra}.
     * Maps to the {@code name} field of the EIP-712 domain separator.
     * Example value: {@code "USD Coin"}.
     */
    public static final String UPTO_SCHEME_PARAMETER_NAME = "name";

    /**
     * Key for the EIP-712 domain version in {@code PaymentRequirements.extra}.
     * Maps to the {@code version} field of the EIP-712 domain separator.
     * Example value: {@code "2"}.
     */
    public static final String UPTO_SCHEME_PARAMETER_VERSION = "version";

    /**
     * Key for the facilitator address in {@code PaymentRequirements.extra}.
     * The client MUST include this address in {@code permit2Authorization.witness.facilitator}
     * to bind the authorization to a specific facilitator, preventing unauthorized settlement.
     * Example value: {@code "0xFacilitatorAddress1234567890123456789012"}.
     */
    public static final String UPTO_SCHEME_PARAMETER_FACILITATOR_ADDRESS = "facilitatorAddress";

}
