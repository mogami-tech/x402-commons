package tech.mogami.commons.constant.networks;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Optional;

/**
 * Existing networks.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused", "magicnumber"})
public class Networks {

    /** Base Sepolia network. */
    public static final Network BASE_SEPOLIA = Network.builder()
            .name("base-sepolia")
            .displayName("Base Sepolia Testnet")
            .chainId(84532)
            .isTestnet(true)
            .build();

    /** Base mainnet network. */
    public static final Network BASE_MAINNET = Network.builder()
            .name("base-mainnet")
            .displayName("Base Mainnet")
            .chainId(84533)
            .isTestnet(false)
            .build();

    /** Map of networks by name. */
    private static final Map<String, Network> NETWORKS_BY_NAME = Map.of(
            BASE_SEPOLIA.name(), BASE_SEPOLIA,
            BASE_MAINNET.name(), BASE_MAINNET
    );

    /**
     * Find a network by its name.
     * TODO Should the search of a network name be case sensitive ?
     *
     * @param name the name of the network
     * @return an Optional containing the Network if found, or empty if not found
     */
    public static Optional<Network> findByName(final String name) {
        return Optional.ofNullable(NETWORKS_BY_NAME.get(name));
    }

}
