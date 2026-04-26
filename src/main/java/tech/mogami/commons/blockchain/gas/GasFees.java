package tech.mogami.commons.blockchain.gas;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigInteger;

/**
 * Gas fees for an EIP-1559 transaction, expressed in wei.
 *
 * @param maximumFeePerGas         the maximum total fee per gas unit the sender is willing to pay (base fee + priority fee), in wei.
 * @param maximumPriorityFeePerGas the maximum tip per gas unit paid directly to the validator, in wei.
 */
@Builder
@SuppressWarnings("unused")
public record GasFees(
        @NotNull BigInteger maximumFeePerGas,
        @NotNull BigInteger maximumPriorityFeePerGas
) {
}
