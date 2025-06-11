package tech.mogami.commons.constant.version;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Existing {@link X402Version}.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused", "magicnumber"})
public class X402Versions {

    /** X402 version 1. */
    public static final X402Version V1 = X402Version.builder()
            .version(1)
            .build();

    /** X402 currently supported by Mogami. */
    public static final X402Version X402_SUPPORTED_VERSION_BY_MOGAMI = V1;

    /** List of X402 suppoerted version. */
    public static final List<X402Version> X402_SUPPORTED_VERSIONS = List.of(V1);

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
    public static Optional<X402Version> findByVersion(final int version) {
        return Optional.ofNullable(X402_SUPPORTED_VERSIONS_BY_VERSION.get(version));
    }

}
