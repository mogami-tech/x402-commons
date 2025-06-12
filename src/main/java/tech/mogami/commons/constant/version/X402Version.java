package tech.mogami.commons.constant.version;

import lombok.Builder;

/**
 * X402 version.
 *
 * @param version the version number
 */
@Builder
@SuppressWarnings("unused")
public record X402Version(
        int version
) {
}
