package tech.mogami.commons.test.crypto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.mogami.commons.constant.network.Networks.BASE_MAINNET;
import static tech.mogami.commons.constant.network.Networks.BASE_SEPOLIA;

@DisplayName("Network Tests")
public class NetworkTest {

    @Test
    @DisplayName("Testing defaultRpcUrl and rpcUrls")
    void testDefaultRpcUrlAndRpcUrls() {
        // Base sepolia
        assertThat(BASE_SEPOLIA.defaultRpcUrl()).isEqualTo("https://sepolia.base.org");
        assertThat(BASE_SEPOLIA.rpcUrl()).isEqualTo("https://sepolia.base.org");
        // Base mainnet
        assertThat(BASE_MAINNET.defaultRpcUrl()).isEqualTo("https://mainnet.base.org");
        assertThat(BASE_MAINNET.rpcUrl()).isEqualTo("https://mainnet.base.org");
    }

    @Test
    @DisplayName("Testing defaultRpcUrl and rpcUrls with environment variables")
    @SetEnvironmentVariable(key = "RPC_URL_BASE", value = "https://custom-url-base.org")
    @SetEnvironmentVariable(key = "RPC_URL_BASE_SEPOLIA", value = "https://custom-url-base-sepolia.org")
    void testDefaultRpcUrlAndRpcUrlsWithEnvironmentVariables() {
        // Base sepolia
        assertThat(BASE_SEPOLIA.defaultRpcUrl()).isEqualTo("https://sepolia.base.org");
        assertThat(BASE_SEPOLIA.rpcUrl()).isEqualTo("https://custom-url-base-sepolia.org");
        // Base mainnet
        assertThat(BASE_MAINNET.defaultRpcUrl()).isEqualTo("https://mainnet.base.org");
        assertThat(BASE_MAINNET.rpcUrl()).isEqualTo("https://custom-url-base.org");
    }

}
