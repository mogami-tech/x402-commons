package tech.mogami.commons.test;

/**
 * Known production wallet addresses used as reference data in integration and cross-repository tests.
 *
 * <p>These addresses are real addresses on production networks. They must never be used to hold
 * private keys or sign transactions in tests.</p>
 */
@SuppressWarnings("unused")
public final class KnownProductionAddresses {

    private KnownProductionAddresses() {
    }

    /** Production: facilitator address. */
    public static final String PRODUCTION_FACILITATOR_WALLET_ADDRESS = "0xFE0920A0a7f0f8a1Ec689146c30C3BBef439bF8A";

    /** Production: reference address n°1. */
    public static final String PRODUCTION_ADDRESS_1_WALLET_ADDRESS = "0x375605671ddA2d461E333Cfa51F5b53763E8C585";

    /** Production: reference address n°2. */
    public static final String PRODUCTION_ADDRESS_2_WALLET_ADDRESS = "0x2306e12F56e45E698bFAfa9c5E7D4e77cDEb4d06";

}
