package com.defiapp.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.5.0.
 */
@SuppressWarnings("rawtypes")
public class Stake extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_LOCK_TIME = "LOCK_TIME";

    public static final String FUNC_YIELD = "YIELD";

    public static final String FUNC_COLLECTEDTOKENS = "collectedTokens";

    public static final String FUNC_GETSTAKEDTOKENDATA = "getStakedTokenData";

    public static final String FUNC_LIMITTOKENSPERCONTRACT = "limitTokensPerContract";

    public static final String FUNC_LIMITTOKENSPERUSER = "limitTokensPerUser";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PROXY = "proxy";

    public static final String FUNC_ROBOTOKEN = "roboToken";

    public static final String FUNC_STAKE = "stake";

    public static final String FUNC_STAKEDTOKENS = "stakedTokens";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final Event STAKEAMOUNT_EVENT = new Event("StakeAmount", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAW_EVENT = new Event("Withdraw", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Stake(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Stake(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Stake(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Stake(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<StakeAmountEventResponse> getStakeAmountEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(STAKEAMOUNT_EVENT, transactionReceipt);
        ArrayList<StakeAmountEventResponse> responses = new ArrayList<StakeAmountEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StakeAmountEventResponse typedResponse = new StakeAmountEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.token = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.createdDate = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.endDate = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static StakeAmountEventResponse getStakeAmountEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(STAKEAMOUNT_EVENT, log);
        StakeAmountEventResponse typedResponse = new StakeAmountEventResponse();
        typedResponse.log = log;
        typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.token = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.createdDate = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
        typedResponse.endDate = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
        return typedResponse;
    }

    public Flowable<StakeAmountEventResponse> stakeAmountEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getStakeAmountEventFromLog(log));
    }

    public Flowable<StakeAmountEventResponse> stakeAmountEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(STAKEAMOUNT_EVENT));
        return stakeAmountEventFlowable(filter);
    }

    public static List<WithdrawEventResponse> getWithdrawEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(WITHDRAW_EVENT, transactionReceipt);
        ArrayList<WithdrawEventResponse> responses = new ArrayList<WithdrawEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawEventResponse typedResponse = new WithdrawEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.tokens = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static WithdrawEventResponse getWithdrawEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(WITHDRAW_EVENT, log);
        WithdrawEventResponse typedResponse = new WithdrawEventResponse();
        typedResponse.log = log;
        typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.tokens = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<WithdrawEventResponse> withdrawEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getWithdrawEventFromLog(log));
    }

    public Flowable<WithdrawEventResponse> withdrawEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAW_EVENT));
        return withdrawEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> LOCK_TIME() {
        final Function function = new Function(FUNC_LOCK_TIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> YIELD() {
        final Function function = new Function(FUNC_YIELD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> collectedTokens() {
        final Function function = new Function(FUNC_COLLECTEDTOKENS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<StakeTokens> getStakedTokenData(String _user) {
        final Function function = new Function(FUNC_GETSTAKEDTOKENDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StakeTokens>() {}));
        return executeRemoteCallSingleValueReturn(function, StakeTokens.class);
    }

    public RemoteFunctionCall<BigInteger> limitTokensPerContract() {
        final Function function = new Function(FUNC_LIMITTOKENSPERCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint16>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> limitTokensPerUser() {
        final Function function = new Function(FUNC_LIMITTOKENSPERUSER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint16>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> proxy() {
        final Function function = new Function(FUNC_PROXY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> roboToken() {
        final Function function = new Function(FUNC_ROBOTOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> stake(String _token, BigInteger _amount) {
        final Function function = new Function(
                FUNC_STAKE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.generated.Uint8(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple4<String, BigInteger, BigInteger, BigInteger>> stakedTokens(String param0) {
        final Function function = new Function(FUNC_STAKEDTOKENS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<String, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple4<String, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple4<String, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(String _token) {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Stake load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Stake(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Stake load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Stake(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Stake load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Stake(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Stake load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Stake(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class StakeTokens extends StaticStruct {
        public String token;

        public BigInteger countStakedTokens;

        public BigInteger stakeCreatedTime;

        public BigInteger endDate;

        public StakeTokens(String token, BigInteger countStakedTokens, BigInteger stakeCreatedTime, BigInteger endDate) {
            super(new org.web3j.abi.datatypes.Address(160, token), 
                    new org.web3j.abi.datatypes.generated.Uint8(countStakedTokens), 
                    new org.web3j.abi.datatypes.generated.Uint256(stakeCreatedTime), 
                    new org.web3j.abi.datatypes.generated.Uint256(endDate));
            this.token = token;
            this.countStakedTokens = countStakedTokens;
            this.stakeCreatedTime = stakeCreatedTime;
            this.endDate = endDate;
        }

        public StakeTokens(Address token, Uint8 countStakedTokens, Uint256 stakeCreatedTime, Uint256 endDate) {
            super(token, countStakedTokens, stakeCreatedTime, endDate);
            this.token = token.getValue();
            this.countStakedTokens = countStakedTokens.getValue();
            this.stakeCreatedTime = stakeCreatedTime.getValue();
            this.endDate = endDate.getValue();
        }
    }

    public static class StakeAmountEventResponse extends BaseEventResponse {
        public String user;

        public String token;

        public BigInteger amount;

        public BigInteger createdDate;

        public BigInteger endDate;
    }

    public static class WithdrawEventResponse extends BaseEventResponse {
        public String user;

        public BigInteger tokens;
    }
}
