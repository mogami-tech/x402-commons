package tech.mogami.commons.constant.blockchain;

import lombok.experimental.UtilityClass;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * List of all the {@link Blockchain}.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused", "magicnumber"})
public class Blockchains {

    /** Ethereum blockchain. */
    public static final Blockchain ETHEREUM = Blockchain.builder()
            .namespace("eip155")
            .name("ethereum")
            .displayName("Ethereum")
            .build();

    /** Base blockchain. */
    public static final Blockchain BASE = Blockchain.builder()
            .namespace("eip155")
            .name("base")
            .displayName("Base")
            .build();

    /** Solana blockchain. */
    public static final Blockchain SOLANA = Blockchain.builder()
            .namespace("solana")
            .name("solana")
            .displayName("Solana")
            .build();

    /** List of all blockchains. */
    public static final List<Blockchain> ALL_BLOCKCHAINS = List.of(ETHEREUM, BASE, SOLANA);

    /** Map of blockchains by name. */
    private static final Map<String, Blockchain> BLOCKCHAINS_BY_NAME = ALL_BLOCKCHAINS.stream()
            .collect(java.util.stream.Collectors.toUnmodifiableMap(
                    blockchain -> blockchain.name().toLowerCase(),
                    java.util.function.Function.identity()
            ));

    /**
     * Finds a blockchain by its name.
     *
     * @param name the name of the blockchain
     * @return an optional containing the blockchain if found, or empty if not found
     */
    public static Optional<Blockchain> findByName(@Nullable final String name) {
        return Optional.ofNullable(name)
                .map(String::toLowerCase)
                .map(BLOCKCHAINS_BY_NAME::get);
    }

}
