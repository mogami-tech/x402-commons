package tech.mogami.commons.test.crypto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.crypto.gas.GasFees;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Gas fees tests")
public class GasFeesTest {

    @Test
    @DisplayName("Test GasFees equality")
    void testEquals() {
        GasFees fees1 = new GasFees(BigInteger.ONE, BigInteger.TEN);
        GasFees fees2 = new GasFees(BigInteger.valueOf(1L), BigInteger.TEN);
        assertEquals(fees1, fees2);
        GasFees fees3 = new GasFees(BigInteger.ONE, BigInteger.TEN);
        GasFees fees4 = new GasFees(BigInteger.valueOf(2L), BigInteger.TEN);
        assertNotEquals(fees3, fees4);
        GasFees fees5 = new GasFees(BigInteger.ONE, BigInteger.TEN);
        GasFees fees6 = new GasFees(BigInteger.ONE, BigInteger.valueOf(2L));
        assertNotEquals(fees5, fees6);
    }

}
