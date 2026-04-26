package tech.mogami.commons.test.blockchain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.blockchain.gas.GasFees;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Gas fees tests")
public class GasFeesTest {

    @Test
    @DisplayName("Test GasFees equality")
    void testEquals() {
        assertThat(new GasFees(BigInteger.ONE, BigInteger.TEN))
                .isEqualTo(new GasFees(BigInteger.valueOf(1L), BigInteger.TEN))
                .isNotEqualTo(new GasFees(BigInteger.valueOf(2L), BigInteger.TEN))
                .isNotEqualTo(new GasFees(BigInteger.ONE, BigInteger.valueOf(2L)));
    }

}
