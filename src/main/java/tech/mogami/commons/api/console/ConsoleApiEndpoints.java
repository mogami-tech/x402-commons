package tech.mogami.commons.api.console;

import lombok.experimental.UtilityClass;

/**
 * Console API endpoints.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused"})
public class ConsoleApiEndpoints {

    /** Base URL for console API. */
    public static final String X402_CONSOLE_API_BASE_URL = "https://api.console.mogami.tech";

    /** Base directory for V1. */
    public static final String V1_PREFIX = "/v1";

    /**
     * Console API endpoints for version 1.
     */
    public static class V1 {

        /** V1 events endpoint. */
        public static final String EVENTS_ENDPOINT = V1_PREFIX + "/events";

        /** V1 event status endpoint. */
        public static final String EVENT_STATUS_ENDPOINT = EVENTS_ENDPOINT + "/{eventId}/status";

    }

}
