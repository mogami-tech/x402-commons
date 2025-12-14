package tech.mogami.commons.constant.version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

/**
 * X402 version.
 *
 * @param version the version number
 */
@Builder
@SuppressWarnings("unused")
public record X402Version(
        int version) {

    /**
     * Get the canonical string representation of the version.
     *
     * @return the canonical string representation
     */
    @JsonIgnore
    public String canonical() {
        return Integer.toString(version);
    }

}
