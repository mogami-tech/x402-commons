package tech.mogami.commons.api.facilitator;

import lombok.experimental.UtilityClass;

/**
 * Facilitator API endpoints.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused"})
public class FacilitatorApiEndpoints {

    /** Supported endpoint. */
    public static final String SUPPORTED_ENDPOINT = "/supported";

    /** Verify endpoint. */
    public static final String VERIFY_ENDPOINT = "/verify";

    /** Settle endpoint. */
    public static final String SETTLE_ENDPOINT = "/settle";

}
