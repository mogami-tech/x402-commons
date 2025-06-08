package tech.mogami.commons.constant.stablecoin;

import lombok.Builder;

/**
 * Stablecoin.
 *
 * @param name stablecoin name.
 */
@Builder
@SuppressWarnings("unused")
public record Stablecoin(
        String name) {
}
