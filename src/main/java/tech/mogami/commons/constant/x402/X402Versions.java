package tech.mogami.commons.constant.x402;

import lombok.experimental.UtilityClass;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * List of all the {@link X402Version}.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused", "magicnumber"})
public class X402Versions {

    /** X402 version 1. */
    public static final X402Version V1 = X402Version.builder()
            .version(1)
            .build();

    /** X402 version 2. */
    public static final X402Version V2 = X402Version.builder()
            .version(2)
            .build();

    /** X402 currently supported by Mogami. */
    public static final X402Version X402_SUPPORTED_VERSION_BY_MOGAMI = V2;

    /** List of all X402 versions. */
    public static final List<X402Version> ALL_X402_VERSIONS = List.of(V1, V2);

    /** Map of all X402 versions by version number. */
    private static final Map<Integer, X402Version> X402_VERSIONS_BY_VERSION = ALL_X402_VERSIONS.stream()
            .collect(Collectors.toUnmodifiableMap(
                    X402Version::version,
                    Function.identity()
            ));

    /** List of X402 supported version. */
    public static final List<X402Version> X402_SUPPORTED_VERSIONS = List.of(V2);

    /** Map of X402 versions by version number. */
    private static final Map<Integer, X402Version> X402_SUPPORTED_VERSIONS_BY_VERSION = X402_SUPPORTED_VERSIONS.stream()
            .collect(Collectors.toUnmodifiableMap(
                    X402Version::version,
                    Function.identity()
            ));

    /**
     * Find a X402 version by its version number.
     *
     * @param version the version number
     * @return an Optional containing the X402 version if found, or empty if not found
     */
    public static Optional<X402Version> findByVersion(@Nullable final Integer version) {
        return Optional.ofNullable(version)
                .map(X402_VERSIONS_BY_VERSION::get);
    }

    /**
     * Find a X402 version by its version string.
     *
     * @param version the version string
     * @return an Optional containing the X402 version if found, or empty if not found or invalid
     */
    public static Optional<X402Version> findByVersion(@Nullable final String version) {
        try {
            return Optional.ofNullable(version)
                    .map(Integer::parseInt)
                    .flatMap(X402Versions::findByVersion);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

}
