package tech.mogami.commons.constant.version;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Optional;

/**
 * X402 versions.
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

    /** Map of networks by name. */
    private static final Map<Integer, X402Version> X402_VERSIONS_SUPPORTED = Map.of(
            V1.version(), V1
    );

    /**
     * Find a X402 version by its version number.
     *
     * @param version the version number
     * @return an Optional containing the X402 version if found, or empty if not found
     */
    public static Optional<X402Version> findByVersion(final int version) {
        return Optional.ofNullable(X402_VERSIONS_SUPPORTED.get(version));
    }

}
