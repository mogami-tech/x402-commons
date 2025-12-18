package tech.mogami.commons.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * A custom deserializer that forces the deserialization of JSON values into Strings.
 */
@SuppressWarnings("unused")
public final class ForceStringDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        JsonNode node = parser.readValueAsTree();
        if (node.isTextual()) {
            return node.asText();
        }
        return node.toString(); // convert object or array to JSON string
    }

}
