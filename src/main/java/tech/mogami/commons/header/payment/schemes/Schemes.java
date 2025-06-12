package tech.mogami.commons.header.payment.schemes;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Existing {@link Scheme}.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused", "magicnumber"})
public class Schemes {

    /** Exact scheme. */
    public static final Scheme EXACT_SCHEME = Scheme.builder()
            .name("exact")
            .build();

    /** All schemes. */
    private static final List<Scheme> ALL_SCHEMES = List.of(EXACT_SCHEME);

    /** Map of schemes by name. */
    private static final Map<String, Scheme> SCHEMES_BY_NAME = ALL_SCHEMES.stream()
            .collect(Collectors.toUnmodifiableMap(
                    scheme -> StringUtils.lowerCase(scheme.name()),
                    Function.identity()
            ));

    /**
     * Find a scheme by its name.
     *
     * @param name the name of the scheme
     * @return an Optional containing the scheme if found, or empty if not found
     */
    public static Optional<Scheme> findByName(final String name) {
        return Optional.ofNullable(name)
                .map(String::toLowerCase)
                .map(SCHEMES_BY_NAME::get);
    }

}
