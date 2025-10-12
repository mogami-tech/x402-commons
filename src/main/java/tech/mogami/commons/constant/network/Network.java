package tech.mogami.commons.constant.network;

import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Represents a network.
 * This is a marker record for network-related constants and configurations.
 *
 * @param name          the name of the network (example: "base-sepolia" or "ethereum-mainnet")
 * @param displayName   a user-friendly display name for the network
 * @param chainId       the unique identifier for the network
 * @param isTestnet     indicates whether the network is a testnet
 * @param defaultRpcUrl the default RPC URL for connecting to the network
 */
@Builder
@SuppressWarnings("unused")
public record Network(
        String name,
        String displayName,
        int chainId,
        boolean isTestnet,
        String defaultRpcUrl
) {

    /** Environment variable prefix for RPC URLs. */
    private static final String ENV_PREFIX = "RPC_URL_";

    /**
     * Default constructor with validation.
     */
    public Network {
        if (name == null) {
            throw new IllegalArgumentException("Network name can't be null");
        }
        if (chainId == 0) {
            throw new IllegalArgumentException("chainId can't be zero");
        }
    }

    /**
     * Retrieves the RPC URL for the network.
     * It first checks for an environment variable specific to the network's name.
     * If not found, it falls back to the default RPC URL.
     *
     * @return the RPC URL as a string
     */
    public String rpcUrl() {
        return Optional.ofNullable(System.getenv(ENV_PREFIX + name.toUpperCase().replace("-", "_")))
                .filter(StringUtils::isNotBlank)
                .orElse(defaultRpcUrl);
    }

}
