package tech.mogami.commons.header.payment.schemes;

import lombok.experimental.UtilityClass;

/**
 * Exact scheme constants.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused"})
public class ExactSchemeConstants {

    /** "Exact" scheme: a scheme that transfers a specific amount from a client to a resource server. */
    public static final String EXACT_SCHEME_NAME = "exact";

    /** Exact scheme parameter name. */
    public static final String EXACT_SCHEME_PARAMETER_NAME = "name";

    /** Exact scheme parameter version. */
    public static final String EXACT_SCHEME_PARAMETER_VERSION = "version";

}
