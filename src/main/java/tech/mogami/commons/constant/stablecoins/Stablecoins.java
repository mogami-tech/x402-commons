package tech.mogami.commons.constant.stablecoins;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Optional;

/**
 * Existing stablecoins.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused", "magicnumber"})
public class Stablecoins {

    /** USDC. */
    public static final Stablecoin USDC = Stablecoin.builder()
            .name("USDC")
            .build();

    /** Map of networks by name. */
    private static final Map<String, Stablecoin> STABLECOINS_BY_NAME = Map.of(
            USDC.name(), USDC
    );

    /**
     * Find a stablecoin by its name.
     * TODO Should the search of a stablecoin name be case sensitive ?
     *
     * @param name the name of the stablecoin
     * @return an Optional containing the stablecoin if found, or empty if not found
     */
    public static Optional<Stablecoin> findByName(final String name) {
        if (StringUtils.isEmpty(name)) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(STABLECOINS_BY_NAME.get(name));
        }
    }

}
