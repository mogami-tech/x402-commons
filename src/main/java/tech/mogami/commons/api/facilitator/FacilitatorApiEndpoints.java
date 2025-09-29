package tech.mogami.commons.api.facilitator;

import lombok.experimental.UtilityClass;

/**
 * Facilitator API endpoints.
 * TODO rename URL variables to X402_FACILITATOR_..._ENDPOINT.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused"})
public class FacilitatorApiEndpoints {

    /** Supported URL. */
    public static final String SUPPORTED_URL = "/supported";

    /** Verify URL. */
    public static final String VERIFY_URL = "/verify";

    /** Settle URL. */
    public static final String SETTLE_URL = "/settle";

}
