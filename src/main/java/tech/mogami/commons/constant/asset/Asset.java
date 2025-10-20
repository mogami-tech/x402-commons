package tech.mogami.commons.constant.asset;

import lombok.Builder;

/**
 * Asset.
 *
 * @param name   asset name.
 * @param symbol asset symbol.
 * @param type   type of the asset.
 */
@Builder
@SuppressWarnings("unused")
public record Asset(
        String name,
        String symbol,
        AssetType type
) {
}
