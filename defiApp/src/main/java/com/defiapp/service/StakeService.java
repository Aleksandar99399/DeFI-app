package com.defiapp.service;

import com.defiapp.model.stake.StakeContractFields;
import com.defiapp.model.stake.StakeOut;
import com.defiapp.model.WithdrawOut;
import com.defiapp.model.stake.StakeRequest;

import java.util.Optional;

public interface StakeService {
    Optional<StakeOut> stakeToken(StakeRequest stakeRequest);

    Optional<StakeContractFields> getStakedTokenData(String property, String user);

    Optional<WithdrawOut> withdraw(String tokenAddress);
}
