package tech.mogami.commons.api.console;

import lombok.experimental.UtilityClass;

/**
 * Console API endpoints.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused"})
public class ConsoleApiEndpoints {

    /** Base URL for V1. */
    public static final String V1_PREFIX = "/v1";

    /**
     * Console API endpoints for version 1.
     */
    public static class V1 {

        /** V1 events URL. */
        public static final String EVENTS_URL = V1_PREFIX + "/events";

    }

}
