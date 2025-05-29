package tech.mogami.commons.constant.networks;

import lombok.Builder;

/**
 * Represents a network in the Mogami Commons library.
 * This is a marker record for network-related constants and configurations.
 *
 * @param name        the name of the network (example: "base-sepolia" or "ethereum-mainnet")
 * @param displayName a user-friendly display name for the network
 * @param chainId     the unique identifier for the network
 * @param isTestnet   indicates whether the network is a testnet
 */
@Builder
@SuppressWarnings("unused")
public record Network(
        String name,
        String displayName,
        int chainId,
        boolean isTestnet) {
}
