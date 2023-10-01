package com.defiapp.service.impl;

import com.defiapp.exceptions.ExceptionConfig;
import com.defiapp.contracts.Stake;
import com.defiapp.model.stake.StakeContractFields;
import com.defiapp.model.stake.StakeOut;
import com.defiapp.model.WithdrawOut;
import com.defiapp.model.stake.StakeRequest;
import com.defiapp.service.StakeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class StakeServiceImpl implements StakeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StakeServiceImpl.class);
    private final Stake stakeContract;

    @Autowired
    public StakeServiceImpl(Stake stakeContract) {
        this.stakeContract = stakeContract;
    }

    @Override
    public Optional<StakeOut> stakeToken(StakeRequest stakeRequest) {

        try {
            TransactionReceipt send = stakeContract.stake(stakeRequest.getToken(), stakeRequest.getAmount()).send();
            List<Stake.StakeAmountEventResponse> stakeAmountEvents = Stake.getStakeAmountEvents(send);
            Stake.StakeAmountEventResponse eventResponse = stakeAmountEvents.get(0);

            LOGGER.info("Successfully stake token");
            return Optional.of(StakeOut.builder()
                    .user(eventResponse.user)
                    .tokenAddress(eventResponse.token)
                    .countStakedTokens(eventResponse.amount)
                    .stakeCreateTime(uintToJavaDate(eventResponse.createdDate.longValue()))
                    .endDate(uintToJavaDate(eventResponse.endDate.longValue()))
                    .build());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            ExceptionConfig.throwError(ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<StakeContractFields> getStakedTokenData(String property, String user) {
        try {
            StakeContractFields stakeFields = new StakeContractFields();
            String data = property == null ? "" : property.trim().replace(" ", "");;

            if (!data.isEmpty()) {
                if (data.equalsIgnoreCase("yield")) {
                    stakeFields.setYield(stakeContract.YIELD().send());
                } else if (data.equalsIgnoreCase("limitPerUser")) {
                    stakeFields.setLimitPerUser(stakeContract.limitTokensPerUser().send());
                } else if (data.equalsIgnoreCase("limitPerContract")) {
                    stakeFields.setLimitPerContract(stakeContract.limitTokensPerContract().send());
                } else if (data.equalsIgnoreCase("collectedTokens")) {
                    stakeFields.setCollectedTokens(stakeContract.collectedTokens().send());
                } else if (data.equalsIgnoreCase("lockPeriod")) {
                    stakeFields.setLockPeriod(getDaysFromSeconds(stakeContract.LOCK_TIME().send()));
                }
            } else {
                stakeFields.setYield(stakeContract.YIELD().send());
                stakeFields.setLimitPerUser(stakeContract.limitTokensPerUser().send());
                stakeFields.setLimitPerContract(stakeContract.limitTokensPerContract().send());
                stakeFields.setCollectedTokens(stakeContract.collectedTokens().send());
                stakeFields.setLockPeriod(getDaysFromSeconds(stakeContract.LOCK_TIME().send()));
            }

            if (user != null && !user.isEmpty()) {
                Stake.StakeTokens stakedTokensPerUser = stakeContract.getStakedTokenData(user).send();
                StakeOut stakeOut = StakeOut.builder()
                        .tokenAddress(stakedTokensPerUser.token)
                        .countStakedTokens(stakedTokensPerUser.countStakedTokens)
                        .stakeCreateTime(uintToJavaDate(stakedTokensPerUser.stakeCreatedTime.longValue()))
                        .endDate(uintToJavaDate(stakedTokensPerUser.endDate.longValue()))
                        .build();
                stakeFields.setUserData(stakeOut);
            }

            LOGGER.info("Successfully loaded data for stake contract");
            return Optional.of(stakeFields);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            ExceptionConfig.throwError(ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<WithdrawOut> withdraw(String tokenAddress) {
        try {
            TransactionReceipt send = stakeContract.withdraw(tokenAddress).send();
            List<Stake.WithdrawEventResponse> withdrawEvents = Stake.getWithdrawEvents(send);
            Stake.WithdrawEventResponse eventResponse = withdrawEvents.get(0);

            LOGGER.info("Successfully withdraw");
            return Optional.of(WithdrawOut.builder()
                    .user(eventResponse.user)
                    .tokens(eventResponse.tokens)
                    .build());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            ExceptionConfig.throwError(ex.getMessage());
            return Optional.empty();
        }
    }

    private Timestamp uintToJavaDate(long unixTimestamp) {
        // Convert the Unix timestamp (seconds) to milliseconds
        long unixTimestampMillis = unixTimestamp * 1000L;
        // Create a Date object from the milliseconds
        return new Timestamp(unixTimestampMillis);
    }

    /**
     * Convert seconds to days
     * @param seconds - seconds returned from Ethereum time_lock
     * @return count days as string
     */
    private String getDaysFromSeconds(BigInteger seconds) {
        int days = (int) (seconds.longValue() / (60 * 60 * 24));
        return days + " days";
    }

}
