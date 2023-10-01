package com.defiapp.config;

import com.defiapp.contracts.BaseToken;
import com.defiapp.contracts.Proxy;
import com.defiapp.contracts.Stake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

@Configuration
public class Web3Config {
    private final Environment environment;

    @Autowired
    public Web3Config(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public Web3j connectToEthereum() {
        System.out.println("Connecting to Ethereum ...");
        Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8545/"));
//        Web3j web3j = Web3j.build(new HttpService("https://goerli.infura.io/v3/" + environment.getProperty("api.infuraKey")));
//        Web3j web3j = Web3j.build(new HttpService("https://sepolia.infura.io/v3/" + environment.getProperty("api.infuraKey")));
        System.out.println("Successfully connected to Ethereum");
        return web3j;
    }

    @Bean
    public BaseToken loadBaseToken(Web3j web3j){
        try {
            // Load the wallet
            Credentials credentials = createCredentials();
            // Load the contract
            String contractAddress = environment.getProperty("contract.token");;
            return BaseToken.load(contractAddress,web3j, credentials, new DefaultGasProvider());
        } catch (Exception ex) {
            throw new RuntimeException("Error whilst sending json-rpc requests", ex);
        }
    }

    @Bean
    public Proxy loadProxy(Web3j web3j){
        try {
            // Load the wallet
            Credentials credentials = createCredentials();
            // Load the contract
            String contractAddress = environment.getProperty("contract.proxy");;
            return Proxy.load(contractAddress,web3j, credentials, new DefaultGasProvider());
        } catch (Exception ex) {
            throw new RuntimeException("Error whilst sending json-rpc requests", ex);
        }
    }

    @Bean
    public Stake loadStakeContract(Web3j web3j){
        try {
            // Load the wallet
            Credentials credentials = createCredentials();
            // Load the contract
            String contractAddress = environment.getProperty("contract.stake");
            return Stake.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        } catch (Exception ex) {
            throw new RuntimeException("Error whilst sending json-rpc requests", ex);
        }
    }

    private Credentials createCredentials(){
//        return Credentials.create(environment.getProperty("api.ownerPrivateKey"));
        return Credentials.create(environment.getProperty("api.localAccountPrivateKey"));
    }
}
