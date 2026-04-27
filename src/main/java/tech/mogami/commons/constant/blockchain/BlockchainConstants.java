package tech.mogami.commons.constant.blockchain;

import lombok.experimental.UtilityClass;
import tech.mogami.commons.blockchain.gas.GasFees;

import java.math.BigInteger;

/**
 * Blockchain constants.
 */
@UtilityClass
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "unused"})
public class BlockchainConstants {

    /** EVM address length (0x prefix + 40 hex characters = 42 total). */
    public static final int EVM_ADDRESS_LENGTH = 42;

    /** EVM address prefix. */
    public static final String EVM_ADDRESS_PREFIX = "0x";

    /** EVM address shortened display prefix length (characters shown at start). */
    public static final int EVM_ADDRESS_SHORTEN_PREFIX_LENGTH = 5;

    /** EVM address shortened display suffix length (characters shown at end). */
    public static final int EVM_ADDRESS_SHORTEN_SUFFIX_LENGTH = 3;

    /** @deprecated Use {@link #EVM_ADDRESS_LENGTH} instead. */
    @Deprecated
    public static final int BLOCKCHAIN_ADDRESS_LENGTH = EVM_ADDRESS_LENGTH;

    /** @deprecated Use {@link #EVM_ADDRESS_PREFIX} instead. */
    @Deprecated
    public static final String BLOCKCHAIN_ADDRESS_PREFIX = EVM_ADDRESS_PREFIX;

    /** @deprecated Use {@link #EVM_ADDRESS_SHORTEN_PREFIX_LENGTH} instead. */
    @Deprecated
    public static final int BLOCKCHAIN_ADDRESS_SHORTEN_PREFIX_LENGTH = EVM_ADDRESS_SHORTEN_PREFIX_LENGTH;

    /** @deprecated Use {@link #EVM_ADDRESS_SHORTEN_SUFFIX_LENGTH} instead. */
    @Deprecated
    public static final int BLOCKCHAIN_ADDRESS_SHORTEN_SUFFIX_LENGTH = EVM_ADDRESS_SHORTEN_SUFFIX_LENGTH;

    /** Number of Wei in one Gwei. */
    public static final BigInteger GWEI_IN_WEI = BigInteger.valueOf(1_000_000_000L);

    /** Default EVM gas limit for transactions. */
    public static final BigInteger DEFAULT_GAS_LIMIT = BigInteger.valueOf(120_000L);

    /** Default EVM maximum fee per gas. */
    public static final BigInteger DEFAULT_MAXIMUM_FEE_PER_GAS = BigInteger.valueOf(2_000_000_000L);

    /** Default EVM maximum priority fee per gas. */
    public static final BigInteger DEFAULT_MAXIMUM_PRIORITY_FEE_PER_GAS = BigInteger.valueOf(1_000_000_000L);

    /** Default EVM Gas fees. */
    public static final GasFees DEFAULT_GAS_FEES = GasFees.builder()
            .maximumFeePerGas(DEFAULT_MAXIMUM_FEE_PER_GAS)
            .maximumPriorityFeePerGas(DEFAULT_MAXIMUM_PRIORITY_FEE_PER_GAS)
            .build();

    /** EIP-712 signature length (0x + 65 bytes = 130 hex chars + 2 prefix chars). */
    public static final int EIP712_SIGNATURE_LENGTH = 132;

    /** Atomic amount type precision. */
    public static final int ATOMIC_AMOUNT_TYPE_PRECISION = 78;

    /** Atomic amount type scale. */
    public static final int ATOMIC_AMOUNT_TYPE_SCALE = 0;

}
