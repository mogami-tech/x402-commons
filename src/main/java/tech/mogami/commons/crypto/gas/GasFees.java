package tech.mogami.commons.crypto.gas;

import lombok.Builder;

import java.math.BigInteger;

/**
 * Gas fees.
 *
 * @param maximumFeePerGas         maximum fee per gas.
 * @param maximumPriorityFeePerGas maximum priority fee per gas.
 */
@Builder
@SuppressWarnings("unused")
public record GasFees(
        BigInteger maximumFeePerGas,
        BigInteger maximumPriorityFeePerGas
) {
}
