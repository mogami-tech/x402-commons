package tech.mogami.commons.constant.blockchain;


import lombok.Builder;

/**
 * Represents a blockchain.
 *
 * @param namespace   the namespace of the blockchain (example: "eip155" for Ethereum)
 * @param name        the name of the blockchain (example: "ethereum" or "bitcoin")
 * @param displayName a user-friendly display name for the blockchain
 */
@Builder
@SuppressWarnings("unused")
public record Blockchain(
        String namespace,
        String name,
        String displayName
) {

    /**
     * Constructs a Blockchain instance ensuring that namespace and name are not null or blank.
     *
     * @param namespace   the namespace of the blockchain
     * @param name        the name of the blockchain
     * @param displayName a user-friendly display name for the blockchain
     * @throws IllegalArgumentException if namespace or name is null or blank
     */
    public Blockchain {
        if (namespace == null || namespace.isBlank()) {
            throw new IllegalArgumentException("Blockchain namespace can't be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Blockchain name can't be null or blank");
        }
    }

    /**
     * Checks if the blockchain is an EVM-compatible blockchain.
     *
     * @return true if the blockchain is EVM-compatible, false otherwise
     */
    public boolean isEvm() {
        return "eip155".equalsIgnoreCase(namespace);
    }

}
