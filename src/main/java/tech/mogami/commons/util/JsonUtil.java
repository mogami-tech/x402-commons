package tech.mogami.commons.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

/**
 * Utility class for JSON operations.
 * <p>
 * This class provides methods for serializing and deserializing JSON data.
 * It is intended to be used with Jackson's ObjectMapper.
 * </p>
 */
@UtilityClass
@SuppressWarnings({"HideUtilityClassConstructor", "unused"})
public class JsonUtil {

    /** Mapper . */
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(WRITE_DATES_AS_TIMESTAMPS, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .findAndRegisterModules();

    /**
     * Convert a JSON string to an object of the specified type.
     *
     * @param json the JSON string to convert
     * @param type the class type to convert the JSON string to
     * @param <T>  the type of the object to return
     * @return the object of the specified type
     */
    public static <T> T fromJson(final String json, final Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error while loading JSON: ", e);
        }
    }

    /**
     * Convert an object to a JSON string.
     *
     * @param value the object to convert to JSON
     * @return the JSON string representation of the object
     */
    public static String toJson(final Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error while writing JSON: ", e);
        }
    }

}
