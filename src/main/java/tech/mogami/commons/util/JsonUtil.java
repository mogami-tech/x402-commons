package tech.mogami.commons.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.Nullable;

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

    /** Mapper. */
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, true)
            .configure(WRITE_DATES_AS_TIMESTAMPS, false)
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
            .findAndRegisterModules();

    /**
     * Convert a JSON string to an object of the specified type.
     *
     * @param json the JSON string to convert
     * @param type the class type to convert the JSON string to
     * @param <T>  the type of the object to return
     * @return the object of the specified type
     * @throws IllegalArgumentException if parsing fails
     */
    public static <T> T fromJson(final String json, final Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error while loading JSON: " + e.getMessage(), e);
        }
    }

    /**
     * Convert an object to another type.
     *
     * @param object the object to convert
     * @param type   the class type to convert the object to
     * @param <T>    the type of the object to return
     * @return the converted object of the specified type
     */
    public <T> T convertValue(final Object object, final Class<T> type) {
        return MAPPER.convertValue(object, type);
    }

    /**
     * Convert an object to a JSON string.
     *
     * @param value the object to serialize (nullable)
     * @return the JSON string, or "null" if value is null
     * @throws IllegalStateException if serialization fails
     */
    public static String toJson(@Nullable final Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error while writing JSON: " + e.getMessage(), e);
        }
    }

    /**
     * Convert an object to a pretty-printed JSON string.
     *
     * @param value the object to serialize (nullable)
     * @return the JSON string, or "null" if value is null
     * @throws IllegalStateException if serialization fails
     */
    public static String toPrettyJson(@Nullable final Object value) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error while writing pretty JSON: " + e.getMessage(), e);
        }
    }

    /**
     * Check if a JSON string is valid for a given class type.
     *
     * @param json        the JSON string to validate
     * @param targetClass the class type to validate against
     * @return true if the JSON is valid for the class type, false otherwise
     */
    public static boolean isValidJson(final String json, final Class<?> targetClass) {
        try {
            MAPPER.readValue(json, targetClass);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

}
