package tech.mogami.commons.constant.network.contract;

import lombok.experimental.UtilityClass;

/**
 * Solana contracts network.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused"})
public class SolanaContracts {

    /** Solana devnet USDC contract address. */
    public static final String SOLANA_DEVNET_USDC_CONTRACT = "4zMMC9srt5Ri5X14GAgXhaHii3GnPAEERYPJgZJDncDU";

    /** Solana mainnet USDC contract address. */
    public static final String SOLANA_MAINNET_USDC_CONTRACT = "EPjFWdd5AufqSSqeM2qN1xzybapC8G4wEGGkZwyTDt1v";

    /** Solana devnet EURC contract address. */
    public static final String SOLANA_DEVNET_EURC_CONTRACT = "HzwqbKZw8HxMN6bF2yFZNrht3c2iXXzpKcFu7uBEDKtr";

    /**
     * Solana mainnet EURC contract address.
     * TODO: verify this address — it is currently identical to the devnet address and may be a placeholder.
     */
    public static final String SOLANA_MAINNET_EURC_CONTRACT = "HzwqbKZw8HxMN6bF2yFZNrht3c2iXXzpKcFu7uBEDKtr";

}
