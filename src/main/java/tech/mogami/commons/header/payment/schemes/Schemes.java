package tech.mogami.commons.header.payment.schemes;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Optional;

/**
 * Supported schemes.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused", "magicnumber"})
public class Schemes {

    /** Exact scheme. */
    public static final Scheme EXACT_SCHEME = Scheme.builder()
            .name("exact")
            .build();

    /** Map of networks by name. */
    private static final Map<String, Scheme> SCHEMES_BY_NAME = Map.of(
            EXACT_SCHEME.name(), EXACT_SCHEME
    );

    /**
     * Find a network by its name.
     * TODO Should the search of a scheme name be case sensitive ?
     *
     * @param name the name of the network
     * @return an Optional containing the Network if found, or empty if not found
     */
    public static Optional<Scheme> findByName(final String name) {
        return Optional.ofNullable(SCHEMES_BY_NAME.get(name));
    }

}
