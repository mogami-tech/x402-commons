package tech.mogami.commons.test.console;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tech.mogami.commons.api.console.ConsoleApiEndpoints.API_BASE_URL;

@DisplayName("API base url tests")
public class APIBaseURLTest {

    @Test
    void getAPIBaseURL() {
        assertEquals(API_BASE_URL, "https://api.console.mogami.tech");
    }

}

