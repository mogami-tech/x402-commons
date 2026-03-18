package tech.mogami.commons.constant.asset;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static tech.mogami.commons.constant.asset.AssetType.STABLECOIN;

/**
 * Existing {@link Asset}.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused", "magicnumber"})
public class Assets {

    /** USDC. */
    public static final Asset USDC = Asset.builder()
            .name("USDC")
            .symbol("USDC")
            .type(STABLECOIN)
            .build();

    /** EURC. */
    public static final Asset EURC = Asset.builder()
            .name("EURC")
            .symbol("EURC")
            .type(STABLECOIN)
            .build();

    /** List of all assets. */
    public static final List<Asset> ALL_ASSETS = List.of(USDC, EURC);

    /** Map of assets by name. */
    private static final Map<String, Asset> ASSETS_BY_NAME = ALL_ASSETS.stream()
            .collect(Collectors.toUnmodifiableMap(
                    asset -> StringUtils.lowerCase(asset.name()),
                    Function.identity()
            ));

    /** Map of assets by symbol. */
    public static final Map<String, Asset> ASSETS_BY_SYMBOL = ALL_ASSETS.stream()
            .collect(Collectors.toUnmodifiableMap(
                    asset -> StringUtils.lowerCase(asset.symbol()),
                    Function.identity()
            ));

    /**
     * Find an asset by its name.
     *
     * @param name the name of the asset
     * @return an Optional containing the asset if found, or empty if not found
     */
    public static Optional<Asset> findByName(@Nullable final String name) {
        return Optional.ofNullable(name)
                .map(String::toLowerCase)
                .map(ASSETS_BY_NAME::get);
    }

    /**
     * Find an asset by its symbol.
     *
     * @param symbol the symbol of the asset
     * @return an Optional containing the asset if found, or empty if not found
     */
    public static Optional<Asset> findBySymbol(@Nullable final String symbol) {
        return Optional.ofNullable(symbol)
                .map(String::toLowerCase)
                .map(ASSETS_BY_SYMBOL::get);
    }

}
