package tech.mogami.commons.test.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.deserializer.ForceStringDeserializer;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ForceStringDeserializer tests")
public class ForceStringDeserializerTest {

    record Wrapper(@JsonDeserialize(using = ForceStringDeserializer.class) String value) {
    }

    final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Should return the string value when node is textual")
    void textualNode() throws Exception {
        assertThat(mapper.readValue("{\"value\":\"hello\"}", Wrapper.class).value())
                .isEqualTo("hello");
    }

    @Test
    @DisplayName("Should return null when node is JSON null")
    void nullNode() throws Exception {
        assertThat(mapper.readValue("{\"value\":null}", Wrapper.class).value())
                .isNull();
    }

    @Test
    @DisplayName("Should return string representation when node is an integer")
    void integerNode() throws Exception {
        assertThat(mapper.readValue("{\"value\":42}", Wrapper.class).value())
                .isEqualTo("42");
    }

    @Test
    @DisplayName("Should return string representation when node is a boolean")
    void booleanNode() throws Exception {
        assertThat(mapper.readValue("{\"value\":true}", Wrapper.class).value())
                .isEqualTo("true");
    }

    @Test
    @DisplayName("Should return string representation when node is an object")
    void objectNode() throws Exception {
        assertThat(mapper.readValue("{\"value\":{\"a\":1}}", Wrapper.class).value())
                .isEqualTo("{\"a\":1}");
    }

    @Test
    @DisplayName("Should return string representation when node is an array")
    void arrayNode() throws Exception {
        assertThat(mapper.readValue("{\"value\":[1,2,3]}", Wrapper.class).value())
                .isEqualTo("[1,2,3]");
    }

}
