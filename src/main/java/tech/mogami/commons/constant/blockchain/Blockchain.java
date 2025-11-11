package tech.mogami.commons.constant.blockchain;


import lombok.Builder;

/**
 * Represents a blockchain.
 *
 * @param name        the name of the blockchain (example: "ethereum" or "bitcoin")
 * @param displayName a user-friendly display name for the blockchain
 */
@Builder
@SuppressWarnings("unused")
public record Blockchain(
        String name,
        String displayName
) {
}
