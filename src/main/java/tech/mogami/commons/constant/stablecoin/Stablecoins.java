package tech.mogami.commons.constant.stablecoin;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Existing {@link Stablecoin}.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused", "magicnumber"})
public class Stablecoins {

    /** USDC. */
    public static final Stablecoin USDC = Stablecoin.builder()
            .name("USDC")
            .build();


    /** List of all stablecoins. */
    public static final List<Stablecoin> ALL_STABLECOINS = List.of(USDC);

    /** Map of stablecoins by name. */
    private static final Map<String, Stablecoin> STABLECOINS_BY_NAME = ALL_STABLECOINS.stream()
            .collect(Collectors.toUnmodifiableMap(
                    stablecoin -> StringUtils.upperCase(stablecoin.toString()),
                    Function.identity()
            ));

    /**
     * Find a stablecoin by its name.
     *
     * @param name the name of the stablecoin
     * @return an Optional containing the stablecoin if found, or empty if not found
     */
    public static Optional<Stablecoin> findByName(final String name) {
        if (StringUtils.isEmpty(name)) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(STABLECOINS_BY_NAME.get(name.toLowerCase()));
        }
    }

}
