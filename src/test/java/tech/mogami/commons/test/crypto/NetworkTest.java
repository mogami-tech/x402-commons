package tech.mogami.commons.test.crypto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import tech.mogami.commons.constant.network.Networks;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.mogami.commons.constant.network.Networks.BASE_MAINNET;
import static tech.mogami.commons.constant.network.Networks.BASE_SEPOLIA;
import static tech.mogami.commons.constant.network.contract.BaseContracts.BASE_MAINNET_USDC_CONTRACT;
import static tech.mogami.commons.constant.network.contract.BaseContracts.BASE_SEPOLIA_USDC_CONTRACT;

@DisplayName("Network Tests")
public class NetworkTest {

    @Test
    @DisplayName("Networks count")
    void testNetworksCount() {
        // There should be exactly 2 networks defined
        assertThat(Networks.ALL_NETWORKS.size()).isEqualTo(5);
    }

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
    @DisplayName("NetworkId")
    void testNetworkId() {
        assertThat(BASE_SEPOLIA.networkId()).isEqualTo("eip155:84532");
        assertThat(BASE_MAINNET.networkId()).isEqualTo("eip155:8453");

        // Search
        assertThat(Networks.findByNetworkId("eip155:84532")).isPresent().get().isEqualTo(BASE_SEPOLIA);
        assertThat(Networks.findByNetworkId("EIP155:84532")).isPresent().get().isEqualTo(BASE_SEPOLIA);
        assertThat(Networks.findByNetworkId("eip155:8453")).isPresent().get().isEqualTo(BASE_MAINNET);
        assertThat(Networks.findByNetworkId("eip155:845")).isEmpty();
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

    @Test
    @DisplayName("Testing deployed asset conversion")
    void testDeployedAssetConversion() {
        assertThat(BASE_SEPOLIA.findDeployedAsset(BASE_SEPOLIA_USDC_CONTRACT))
                .isPresent()
                .get()
                .satisfies(deployedAsset -> {
                    assertThat(deployedAsset.asset().symbol()).isEqualTo("USDC");
                    assertThat(deployedAsset.contractAddress()).isEqualTo(BASE_SEPOLIA_USDC_CONTRACT);
                });
        assertThat(BASE_SEPOLIA.findDeployedAsset(BASE_MAINNET_USDC_CONTRACT)).isEmpty();

        assertThat(BASE_MAINNET.findDeployedAsset(BASE_MAINNET_USDC_CONTRACT))
                .isPresent()
                .get()
                .satisfies(deployedAsset -> {
                    assertThat(deployedAsset.asset().symbol()).isEqualTo("USDC");
                    assertThat(deployedAsset.contractAddress()).isEqualTo(BASE_MAINNET_USDC_CONTRACT);
                });
        assertThat(BASE_MAINNET.findDeployedAsset(BASE_SEPOLIA_USDC_CONTRACT)).isEmpty();
    }

}
