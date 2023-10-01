//package com.defiapp.service;
//
//import com.defiapp.contracts.Proxy;
//import com.defiapp.contracts.Stake;
//import com.defiapp.exceptions.customEx.TokenDoesntExist;
//import com.defiapp.exceptions.customEx.UserHasNotStakedAmount;
//import com.defiapp.model.stake.StakeContractFields;
//import com.defiapp.service.impl.StakeServiceImpl;
//import com.defiapp.service.impl.TokenServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.web3j.protocol.core.RemoteCall;
//import org.web3j.protocol.core.RemoteFunctionCall;
//import org.web3j.protocol.core.methods.response.TransactionReceipt;
//
//import java.math.BigInteger;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//
//@SpringBootTest
//public class StakeServiceImplTest {
//    private Stake stakeContract;
//
//    @Autowired
//    private StakeServiceImpl stakeService;
//
//
//    @BeforeEach
//    public void setUp() {
//        stakeContract = mock(Stake.class);
//        stakeService = new StakeServiceImpl(stakeContract);
//    }
//
//    @Test
//    public void getStakedTokenData_throwUserHasNotStakedAmount() {
//        String address = "0x5FbDB2315678afecb367f032d93F642f64180ca3";
//        when(stakeContract.getStakedTokenData(address)).thenThrow(new RuntimeException("UserHasNotStakedAmount"));
//        assertThrows(UserHasNotStakedAmount.class, () -> stakeService.getStakedTokenData(any(), any()));
//    }
//
//    @Test
//    public void getStakedTokenData() {
//        String address = "0x5FbDB2315678afecb367f032d93F642f64180ca3";
//        when(stakeContract.getStakedTokenData(address)).thenThrow(new RuntimeException("UserHasNotStakedAmount"));
//        assertThrows(UserHasNotStakedAmount.class, () -> stakeService.getStakedTokenData(any(), any()));
//    }
//
//
//    @Test
//    public void testGetStakedTokenData() throws Exception {
//        String user = "0x423ae6565ea5656a6e677";
//        Stake.StakeTokens mockedStakeTokens = new Stake.StakeTokens();
//        mockedStakeTokens.token = "0x423ae656590899a998e90d77";
//        mockedStakeTokens.countStakedTokens = BigInteger.valueOf(100);
//        RemoteFunctionCall remoteFunctionCall = mock(RemoteFunctionCall.class);
//        TransactionReceipt transactionReceipt = mock(TransactionReceipt.class);
//
//        // Mock the behavior of stakeContract methods
//        when(stakeContract.YIELD()).thenReturn(remoteFunctionCall);
//        when(stakeContract.YIELD().send()).thenReturn(BigInteger.valueOf(42));
//        when(stakeContract.limitTokensPerUser()).thenReturn(remoteFunctionCall);
//        when(stakeContract.limitTokensPerUser().send()).thenReturn(BigInteger.valueOf(10));
//        when(stakeContract.limitTokensPerContract()).thenReturn(remoteFunctionCall);
//        when(stakeContract.limitTokensPerContract().send()).thenReturn(BigInteger.valueOf(20));
//        when(stakeContract.collectedTokens()).thenReturn(remoteFunctionCall);
//        when(stakeContract.collectedTokens().send()).thenReturn(BigInteger.valueOf(30));
//        when(stakeContract.LOCK_TIME()).thenReturn(remoteFunctionCall);
//        when(stakeContract.LOCK_TIME().send()).thenReturn(BigInteger.valueOf(86400));
//        when(stakeContract.getStakedTokenData(user)).thenReturn(remoteFunctionCall);
//        when(stakeContract.getStakedTokenData(user).send()).thenReturn(new Stake.StakeTokens());
//
//        // Invoke the method under test
//        StakeContractFields result = stakeService.getStakedTokenData("yield", user);
//
//        // Assertions
//        assertNotNull(result);
//        assertEquals(BigInteger.valueOf(42), result.getYield());
//        assertEquals(BigInteger.valueOf(10), result.getLimitPerUser());
//        assertEquals(BigInteger.valueOf(20), result.getLimitPerContract());
//        assertEquals(BigInteger.valueOf(30), result.getCollectedTokens());
//        assertEquals(1, result.getLockPeriod());
//        assertNotNull(result.getUserData());
//        assertEquals("MockedTokenAddress", result.getUserData().getTokenAddress());
//        assertEquals(BigInteger.valueOf(100), result.getUserData().getCountStakedTokens());
//        // Add more assertions as needed
//    }
//}
//
