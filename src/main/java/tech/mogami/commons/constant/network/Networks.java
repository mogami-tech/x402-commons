package tech.mogami.commons.constant.network;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static tech.mogami.commons.constant.asset.Assets.USDC;
import static tech.mogami.commons.constant.blockchain.Blockchains.BASE;
import static tech.mogami.commons.constant.network.base.BaseContracts.BASE_MAINNET_USDC_CONTRACT;
import static tech.mogami.commons.constant.network.base.BaseContracts.BASE_SEPOLIA_USDC_CONTRACT;

/**
 * Existing {@link Network}.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused", "magicnumber"})
public class Networks {

    /** Base sepolia network. */
    public static final Network BASE_SEPOLIA = Network.builder()
            .blockchain(BASE)
            .name("base-sepolia")
            .displayName("Base Sepolia Testnet")
            .chainId(84532)
            .isTestnet(true)
            .defaultRpcUrl("https://sepolia.base.org")
            .usdc(Network.DeployedAsset.builder()
                    .asset(USDC)
                    .displayName("USDC")
                    .contractAddress(BASE_SEPOLIA_USDC_CONTRACT)
                    .decimals(6)
                    .build())
            .build();

    /** Base mainnet network. */
    public static final Network BASE_MAINNET = Network.builder()
            .blockchain(BASE)
            .name("base")
            .displayName("Base Mainnet")
            .chainId(8453)
            .isTestnet(false)
            .defaultRpcUrl("https://mainnet.base.org")
            .usdc(Network.DeployedAsset.builder()
                    .asset(USDC)
                    .displayName("USD Coin")
                    .contractAddress(BASE_MAINNET_USDC_CONTRACT)
                    .decimals(6)
                    .build())
            .build();

    /** List of all networks. */
    public static final List<Network> ALL_NETWORKS = List.of(BASE_SEPOLIA, BASE_MAINNET);

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
     * @return an Optional containing the network if found, or empty if not found
     */
    public static Optional<Network> findByName(@Nullable final String name) {
        return Optional.ofNullable(name)
                .map(String::toLowerCase)
                .map(NETWORKS_BY_NAME::get);
    }

}
