package tech.mogami.commons.constant.stablecoin;

import lombok.Builder;

/**
 * Stablecoin.
 *
 * @param name        stablecoin name.
 * @param symbol      stablecoin symbol.
 * @param displayName stablecoin display name.
 */
@Builder
@SuppressWarnings("unused")
public record Stablecoin(
        String name,
        String symbol,
        String displayName
) {
}
