package com.defiapp.service.impl;

import com.defiapp.contracts.Proxy;
import com.defiapp.exceptions.ExceptionConfig;
import com.defiapp.model.TokenOut;
import com.defiapp.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {
    private final Proxy proxyContract;

    private static final Logger LOGGER= LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    public TokenServiceImpl(Proxy proxyContract) {
        this.proxyContract = proxyContract;
    }

    @Override
    public Optional<TokenOut> getTokenData(String address) {
        try {
            Proxy.TokenData tokenData = proxyContract.loadTokenData(address).send();

            LOGGER.info("Successfully transaction for getting token: " + address);
            return Optional.ofNullable(TokenOut.builder()
                    .name(tokenData.name)
                    .symbol(tokenData.symbol)
                    .decimals(tokenData.decimals)
                    .build());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            ExceptionConfig.throwError(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public String addToken(String address) {
        try {
            TransactionReceipt send = proxyContract.addToken(address).send();
            Proxy.AddTokenEventResponse addTokenEventResponse = Proxy.getAddTokenEvents(send).get(0);

            LOGGER.info("Added token: " + address);
            return addTokenEventResponse.token;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            ExceptionConfig.throwError(ex.getMessage());
            return null;
        }
    }

}
