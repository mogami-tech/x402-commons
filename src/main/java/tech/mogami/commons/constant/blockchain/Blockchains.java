package tech.mogami.commons.constant.blockchain;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;

/**
 * Existing {@link Blockchain}.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused", "magicnumber"})
public class Blockchains {

    /** Ethereum blockchain. */
    public static final Blockchain ETHEREUM = Blockchain.builder()
            .name("ethereum")
            .displayName("Ethereum")
            .build();

    /** Base blockchain. */
    public static final Blockchain BASE = Blockchain.builder()
            .name("base")
            .displayName("Base")
            .build();

    /** List of all blockchains. */
    public static final List<Blockchain> ALL_BLOCKCHAINS = List.of(ETHEREUM, BASE);

    /** Map of blockchains by name. */
    private static final java.util.Map<String, Blockchain> BLOCKCHAINS_BY_NAME = ALL_BLOCKCHAINS.stream()
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
    public static java.util.Optional<Blockchain> findByName(final String name) {
        return Optional.ofNullable(name)
                .map(String::toLowerCase)
                .map(BLOCKCHAINS_BY_NAME::get);
    }

}
