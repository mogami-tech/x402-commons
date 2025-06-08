package tech.mogami.commons.constant.version;

import java.util.Map;
import java.util.Optional;

/**
 * X402 versions.
 */
public class X402Versions {

    /**
     * X402 version 1.
     */
    public static final X402Version V1 = X402Version.builder()
            .version(1)
            .build();

    /** Map of networks by name. */
    private static final Map<Integer, X402Version> STABLECOINS_BY_NAME = Map.of(
            V1.version(), V1
    );

    /**
     * Find a X402 version by its version number.
     *
     * @param version the version number
     * @return an Optional containing the X402 version if found, or empty if not found
     */
    public static Optional<X402Version> findByVersion(final int version) {
        return Optional.ofNullable(STABLECOINS_BY_NAME.get(version));
    }

}
