package tech.mogami.commons.constant.network;

import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import tech.mogami.commons.constant.asset.Asset;

import java.math.BigDecimal;
import java.util.Optional;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.DOWN;

/**
 * Represents a network.
 * This is a marker record for network-related constants and configurations.
 *
 * @param name          the name of the network (example: "base-sepolia" or "ethereum-mainnet")
 * @param displayName   a user-friendly display name for the network
 * @param chainId       the unique identifier for the network
 * @param isTestnet     indicates whether the network is a testnet
 * @param defaultRpcUrl the default RPC URL for connecting to the network
 * @param usdc          the USDC asset deployed on the network
 */
@Builder
@SuppressWarnings("unused")
public record Network(
        String name,
        String displayName,
        int chainId,
        boolean isTestnet,
        String defaultRpcUrl,
        DeployedAsset usdc
) {

    /** Environment variable prefix for RPC URLs. */
    private static final String ENVIRONMENT_PREFIX = "RPC_URL_";

    /**
     * Represents an asset (token) deployed on a given network.
     *
     * @param asset           the asset information (e.g., USDC)
     * @param displayName     the display name of the USDC token on a specific (e.g., "USD Coin")
     * @param contractAddress the contract address of the USDC token on the network
     * @param decimals        the number of decimals used by the USDC token (e.g., 6)
     */
    @Builder
    public record DeployedAsset(
            Asset asset,
            String displayName,
            String contractAddress,
            int decimals
    ) {

        /**
         * Converts a human-readable amount to its atomic representation based on the asset's decimals.
         *
         * @param amount the human-readable amount (e.g., 0.10 USDC)
         * @return the atomic representation as a string (e.g., "100000" for 0.10 USDC with 6 decimals)
         */
        public BigDecimal toAtomic(final BigDecimal amount) {
            if (amount == null) {
                return ZERO;
            }
            return amount.multiply(TEN.pow(decimals)).setScale(0, DOWN);
        }

        /**
         * Converts a human-readable amount string to its atomic representation based on the asset's decimals.
         *
         * @param amountAsString the human-readable amount as a string (e.g., "0.10" for USDC)
         * @return the atomic representation as BigDecimal (e.g., 100000 for 0.10 USDC with 6 decimals)
         */
        public BigDecimal toAtomic(final String amountAsString) {
            if (StringUtils.isBlank(amountAsString)) {
                return ZERO;
            }
            try {
                return toAtomic(new BigDecimal(amountAsString));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid amount format: " + amountAsString, e);
            }
        }

        /**
         * Converts an atomic value string to its human-readable BigDecimal representation.
         *
         * @param atomicValue the atomic value as a string (e.g., "100000" for USDC with 6 decimals)
         * @return the human-readable amount as BigDecimal (e.g., 0.10 USDC)
         */
        public BigDecimal fromAtomic(final String atomicValue) {
            if (StringUtils.isBlank(atomicValue)) {
                return ZERO;
            }
            return new BigDecimal(atomicValue).divide(TEN.pow(decimals), decimals, DOWN);
        }

    }

    /**
     * Default constructor with validation.
     */
    public Network {
        if (name == null) {
            throw new IllegalArgumentException("Network name can't be null");
        }
        if (chainId == 0) {
            throw new IllegalArgumentException("Chain Id can't be zero");
        }
        if (StringUtils.isBlank(defaultRpcUrl)) {
            throw new IllegalArgumentException("Default rpc Url can't be blank");
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
        return Optional.ofNullable(System.getenv(ENVIRONMENT_PREFIX + name.toUpperCase().replace("-", "_")))
                .filter(StringUtils::isNotBlank)
                .orElse(defaultRpcUrl);
    }

}
