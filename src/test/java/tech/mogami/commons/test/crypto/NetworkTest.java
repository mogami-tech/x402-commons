package tech.mogami.commons.test.crypto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import tech.mogami.commons.constant.network.Network;
import tech.mogami.commons.constant.network.Networks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.mogami.commons.constant.network.Networks.ALL_NETWORKS;
import static tech.mogami.commons.constant.network.Networks.BASE_MAINNET;
import static tech.mogami.commons.constant.network.Networks.BASE_SEPOLIA;
import static tech.mogami.commons.constant.network.contract.BaseContracts.BASE_MAINNET_EURC_CONTRACT;
import static tech.mogami.commons.constant.network.contract.BaseContracts.BASE_MAINNET_USDC_CONTRACT;
import static tech.mogami.commons.constant.network.contract.BaseContracts.BASE_SEPOLIA_EURC_CONTRACT;
import static tech.mogami.commons.constant.network.contract.BaseContracts.BASE_SEPOLIA_USDC_CONTRACT;

@DisplayName("Network tests")
public class NetworkTest {

    @Test
    @DisplayName("Networks count")
    void networksCount() {
        assertThat(ALL_NETWORKS.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("Should return defaultRpcUrl and rpcUrls")
    void defaultRpcUrlAndRpcUrls() {
        // Base sepolia
        assertThat(BASE_SEPOLIA)
                .returns("https://sepolia.base.org", Network::defaultRpcUrl)
                .returns("https://sepolia.base.org", Network::rpcUrl);
        // Base mainnet
        assertThat(BASE_MAINNET)
                .returns("https://mainnet.base.org", Network::defaultRpcUrl)
                .returns("https://mainnet.base.org", Network::rpcUrl);
    }

    @Test
    @DisplayName("NetworkId and findByNetworkId()")
    void networkId() {
        assertThat(BASE_SEPOLIA).returns("eip155:84532", Network::networkId);
        assertThat(BASE_MAINNET).returns("eip155:8453", Network::networkId);

        // Search with findByNetworkId
        assertThat(Networks.findByNetworkId("eip155:84532")).hasValue(BASE_SEPOLIA);
        assertThat(Networks.findByNetworkId("EIP155:84532")).hasValue(BASE_SEPOLIA);
        assertThat(Networks.findByNetworkId("eip155:8453")).hasValue(BASE_MAINNET);
        assertThat(Networks.findByNetworkId("eip155:845")).isEmpty();
    }

    @Test
    @DisplayName("Should return defaultRpcUrl and rpcUrls with environment variables")
    @SetEnvironmentVariable(key = "RPC_URL_BASE", value = "https://custom-url-base.org")
    @SetEnvironmentVariable(key = "RPC_URL_BASE_SEPOLIA", value = "https://custom-url-base-sepolia.org")
    void defaultRpcUrlAndRpcUrlsWithEnvironmentVariables() {
        // Base sepolia
        assertThat(BASE_SEPOLIA)
                .returns("https://sepolia.base.org", Network::defaultRpcUrl)
                .returns("https://custom-url-base-sepolia.org", Network::rpcUrl);
        // Base mainnet
        assertThat(BASE_MAINNET)
                .returns("https://mainnet.base.org", Network::defaultRpcUrl)
                .returns("https://custom-url-base.org", Network::rpcUrl);
    }

    @Test
    @DisplayName("findDeployedAsset()")
    void findDeployedAsset() {
        // USDC on Base Sepolia
        assertThat(BASE_SEPOLIA.findDeployedAsset(BASE_SEPOLIA_USDC_CONTRACT)).isPresent().get()
                .returns("USDC", deployedAsset -> deployedAsset.asset().symbol())
                .returns(BASE_SEPOLIA_USDC_CONTRACT, Network.DeployedAsset::contractAddress);
        assertThat(BASE_SEPOLIA.findDeployedAsset(BASE_MAINNET_USDC_CONTRACT)).isEmpty();

        // USDC on Base Mainnet
        assertThat(BASE_MAINNET.findDeployedAsset(BASE_MAINNET_USDC_CONTRACT)).isPresent().get()
                .returns("USDC", d -> d.asset().symbol())
                .returns(BASE_MAINNET_USDC_CONTRACT, Network.DeployedAsset::contractAddress);
        assertThat(BASE_MAINNET.findDeployedAsset(BASE_SEPOLIA_USDC_CONTRACT)).isEmpty();

        // EURC on Base Sepolia
        assertThat(BASE_SEPOLIA.findDeployedAsset(BASE_SEPOLIA_EURC_CONTRACT)).isPresent().get()
                .returns("EURC", d -> d.asset().symbol())
                .returns(BASE_SEPOLIA_EURC_CONTRACT, Network.DeployedAsset::contractAddress);
        assertThat(BASE_SEPOLIA.findDeployedAsset(BASE_MAINNET_EURC_CONTRACT)).isEmpty();

        // EURC on Base Mainnet
        assertThat(BASE_MAINNET.findDeployedAsset(BASE_MAINNET_EURC_CONTRACT)).isPresent().get()
                .returns("EURC", d -> d.asset().symbol())
                .returns(BASE_MAINNET_EURC_CONTRACT, Network.DeployedAsset::contractAddress);
        assertThat(BASE_SEPOLIA.findDeployedAsset(BASE_MAINNET_EURC_CONTRACT)).isEmpty();
    }

    @Test
    @DisplayName("isEvm()")
    void isEvm() {
        assertThat(BASE_SEPOLIA)
                .returns(true, Network::isEvm)
                .returns(84532L, Network::chainId);

        assertThat(BASE_MAINNET)
                .returns(true, Network::isEvm)
                .returns(8453L, Network::chainId);

        // Test that chain id is not available on Solana (not evm).
        assertThat(Networks.SOLANA_MAINNET.isEvm()).isFalse();
        assertThatThrownBy(Networks.SOLANA_MAINNET::chainId)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("chainId is only available for EVM-compatible blockchains");
    }

}
