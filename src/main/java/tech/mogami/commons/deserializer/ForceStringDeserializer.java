package tech.mogami.commons.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * A custom deserializer that forces the deserialization of JSON values into Strings.
 */
public final class ForceStringDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();
        if (node.isTextual()) {
            return node.asText();
        }
        return node.toString(); // convert object or array to JSON string
    }

}
