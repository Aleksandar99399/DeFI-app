package com.defiapp.service;

import com.defiapp.model.TokenOut;

import java.util.Optional;

public interface TokenService {

    Optional<TokenOut> getTokenData(String address);

    String addToken(String address);
}
