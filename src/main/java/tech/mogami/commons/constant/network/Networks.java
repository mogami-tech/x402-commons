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
import static tech.mogami.commons.constant.blockchain.Blockchains.SOLANA;
import static tech.mogami.commons.constant.network.contract.BaseContracts.BASE_MAINNET_USDC_CONTRACT;
import static tech.mogami.commons.constant.network.contract.BaseContracts.BASE_SEPOLIA_USDC_CONTRACT;

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
            .networkReference("84532")
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
            .networkReference("8453")
            .isTestnet(false)
            .defaultRpcUrl("https://mainnet.base.org")
            .usdc(Network.DeployedAsset.builder()
                    .asset(USDC)
                    .displayName("USD Coin")
                    .contractAddress(BASE_MAINNET_USDC_CONTRACT)
                    .decimals(6)
                    .build())
            .build();

    /** Solana devnet network. */
    public static final Network SOLANA_DEVNET = Network.builder()
            .blockchain(SOLANA)
            .name("solana-devnet")
            .displayName("Solana Devnet")
            .networkReference("4uhcVJyU9pJkvQyS88uRDiswHXSCkY3zQawwpjk2NsNY")
            .isTestnet(true)
            .defaultRpcUrl("https://api.devnet.solana.com")
            .build();

    /** Solana testnet network. */
    public static final Network SOLANA_TESTNET = Network.builder()
            .blockchain(SOLANA)
            .name("solana-testnet")
            .displayName("Solana Testnet")
            .networkReference("8E9rvCKLFQia2Y35HXjjpWzj8weVo44K")
            .isTestnet(true)
            .defaultRpcUrl("https://api.testnet.solana.com")
            .build();

    /** Solana mainnet network. */
    public static final Network SOLANA_MAINNET = Network.builder()
            .blockchain(SOLANA)
            .name("solana")
            .displayName("Solana Mainnet")
            .networkReference("EtWTRABZaYq6iMfeYKouRu166VU2xqa1")
            .isTestnet(false)
            .defaultRpcUrl("https://api.mainnet-beta.solana.com")
            .build();

    /** List of all networks. */
    public static final List<Network> ALL_NETWORKS = List.of(
            BASE_SEPOLIA, BASE_MAINNET,
            SOLANA_DEVNET, SOLANA_TESTNET, SOLANA_MAINNET
    );

    /** Map of networks by networkId (CAIP-2). */
    private static final Map<String, Network> NETWORKS_BY_ID = ALL_NETWORKS.stream()
            .collect(Collectors.toUnmodifiableMap(
                    network -> StringUtils.lowerCase(network.networkId()),
                    Function.identity()
            ));

    /** Map of networks by name. */
    private static final Map<String, Network> NETWORKS_BY_NAME = ALL_NETWORKS.stream()
            .collect(Collectors.toUnmodifiableMap(
                    network -> StringUtils.lowerCase(network.name()),
                    Function.identity()
            ));

    /**
     * Find a network by its networkId (CAIP-2, e.g. "eip155:8453").
     *
     * @param networkId the network id
     * @return an Optional containing the network if found, or empty if not found
     */
    public static Optional<Network> findByNetworkId(@Nullable final String networkId) {
        return Optional.ofNullable(networkId)
                .map(StringUtils::lowerCase)
                .map(NETWORKS_BY_ID::get);
    }

    /**
     * Find a network by its name (e.g. "base-sepolia").
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
