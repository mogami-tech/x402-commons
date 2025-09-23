package tech.mogami.commons.api.console;

import lombok.experimental.UtilityClass;

/**
 * Console API endpoints.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused"})
public class ConsoleApiEndpoints {

    /** Base URL for console API. */
    public static final String API_BASE_URL = System.getenv().getOrDefault("CONSOLE_API_BASE_URL", "https://api.console.mogami.tech");

    /** Base URL for V1. */
    public static final String V1_PREFIX = API_BASE_URL + "/v1";

    /**
     * Console API endpoints for version 1.
     */
    public static class V1 {

        /** V1 events URL. */
        public static final String EVENTS_URL = V1_PREFIX + "/events";

        /** V1 event status URL. */
        public static final String EVENT_STATUS_URL = EVENTS_URL + "/{eventId}/status";

    }

}
