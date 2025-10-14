package tech.mogami.commons.constant;

import lombok.experimental.UtilityClass;
import tech.mogami.commons.crypto.gas.GasFees;

import java.math.BigInteger;

/**
 * Blockchain constants.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused"})
public class BlockchainConstants {

    /** Blockchain address length. */
    public static final int BLOCKCHAIN_ADDRESS_LENGTH = 42;

    /** Blockchain address prefix. */
    public static final String BLOCKCHAIN_ADDRESS_PREFIX = "0x";

    /** Number of Wei in one Gwei. */
    public static final BigInteger GWEI_IN_WEI = BigInteger.valueOf(1_000_000_000L);

    /** Default gas limit for transactions. */
    public static final BigInteger DEFAULT_GAS_LIMIT = BigInteger.valueOf(120_000);

    /** Default maximum fee per gas. */
    public static final BigInteger DEFAULT_MAXIMUM_FEE_PER_GAS = BigInteger.valueOf(2_000_000_000L);

    /** Default maximum priority fee per gas. */
    public static final BigInteger DEFAULT_MAXIMUM_PRIORITY_FEE_PER_GAS = BigInteger.valueOf(1_000_000_000L);

    /** Default Gas fees. */
    public static final GasFees DEFAULT_GAS_FEES = GasFees.builder()
            .maximumFeePerGas(DEFAULT_MAXIMUM_FEE_PER_GAS)
            .maximumPriorityFeePerGas(DEFAULT_MAXIMUM_PRIORITY_FEE_PER_GAS)
            .build();

}
