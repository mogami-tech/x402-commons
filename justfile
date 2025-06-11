# Development ==========================================================================================================
run_install:
    mvn install -DskipTests

run_tests:
    mvn clean install

# Release ==============================================================================================================
run_deploy_snapshot:
    mvn -B -Prelease -DskipTests clean deploy

start_release:
    git remote set-url origin git@github.com:mogami-tech/x402-commons.git
    git checkout development
    git pull
    git status
    mvn gitflow:release-start

finish_release:
    mvn gitflow:release-finish -DskipTests

run_deploy_release:
    mvn -B -Prelease -DskipTests clean deploy

# Contract generation ==================================================================================================
build_contracts:
    cd src/main/solidity && rm -rdf lib
    cd src/main/solidity && forge install OpenZeppelin/openzeppelin-contracts@v3.4.2 --no-git
    cd src/main/solidity && forge build --contracts lib/openzeppelin-contracts/contracts/token/ERC20/ERC20.sol --extra-output-files abi bin
    mv src/main/solidity/out/ERC20.sol/ERC20.abi.json src/main/solidity/out/ERC20.sol/ERC20.abi
    web3j generate solidity \
              -b src/main/solidity/out/ERC20.sol/ERC20.bin \
              -a src/main/solidity/out/ERC20.sol/ERC20.abi \
              -o src/main/java \
              -p tech.mogami.commons.crypto.contract
    cd src/main/solidity && forge install circlefin/stablecoin-evm --no-git
    cd src/main/solidity && forge build --contracts lib/stablecoin-evm/contracts/v2/FiatTokenV2_2.sol --extra-output-files abi bin
    mv src/main/solidity/out/FiatTokenV2_2.sol/FiatTokenV2_2.abi.json src/main/solidity/out/FiatTokenV2_2.sol/FiatTokenV2_2.abi
    web3j generate solidity \
              -b src/main/solidity/out/FiatTokenV2_2.sol/FiatTokenV2_2.bin \
              -a src/main/solidity/out/FiatTokenV2_2.sol/FiatTokenV2_2.abi \
              -o src/main/java \
              -p tech.mogami.commons.crypto.contract
