package tech.mogami.commons.constant.network;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Existing {@link Network}.
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

    /** List of all networks. */
    private static final List<Network> ALL_NETWORKS = List.of(BASE_SEPOLIA, BASE_MAINNET);

    /** Map of networks by name. */
    private static final Map<String, Network> NETWORKS_BY_NAME = ALL_NETWORKS.stream()
            .collect(Collectors.toUnmodifiableMap(
                    network -> StringUtils.lowerCase(network.name()),
                    Function.identity()
            ));

    /**
     * Find a network by its name.
     *
     * @param name the name of the network
     * @return an Optional containing the Network if found, or empty if not found
     */
    public static Optional<Network> findByName(final String name) {
        if (StringUtils.isEmpty(name)) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(NETWORKS_BY_NAME.get(name.toLowerCase()));
        }
    }

}
