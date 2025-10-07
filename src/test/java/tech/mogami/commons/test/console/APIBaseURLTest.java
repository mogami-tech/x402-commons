package tech.mogami.commons.test.console;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tech.mogami.commons.api.console.ConsoleApiEndpoints.X402_CONSOLE_API_BASE_URL;

@DisplayName("API base url tests")
public class APIBaseURLTest {

    @Test
    @DisplayName("Check API base URL")
    void getAPIBaseURL() {
        assertEquals(X402_CONSOLE_API_BASE_URL, "https://api.console.mogami.tech");
    }

}

