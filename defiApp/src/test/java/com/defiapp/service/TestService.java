//package com.defiapp.service;
//
//import com.defiapp.contracts.Proxy;
//import com.defiapp.exceptions.customEx.TokenAlreadyExist;
//import com.defiapp.service.impl.TokenServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.web3j.protocol.core.RemoteFunctionCall;
//import org.web3j.protocol.core.methods.response.Log;
//import org.web3j.protocol.core.methods.response.TransactionReceipt;
//import org.web3j.tx.Contract;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//
//@SpringBootTest
//public class TestService {
//
//    @Autowired
//    private TokenServiceImpl tokenService;
//
//    @MockBean
//    private Proxy proxyContract;
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this); // Initialize mock objects
//    }
//
//    @Test
//    public void testAddToken() throws Exception {
//        // Arrange
//        String address = "your_address";
//        Proxy mock = mock(Proxy.class);
//        TransactionReceipt transactionReceipt = Mockito.mock(TransactionReceipt.class);
//        Proxy.AddTokenEventResponse addTokenEventResponse = Mockito.mock(Proxy.AddTokenEventResponse.class);
//        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = mock(RemoteFunctionCall.class);
//        Mockito.when(mock.addToken(Mockito.eq(address))).thenReturn(remoteFunctionCall);
//        Mockito.when(mock.addToken(Mockito.eq(address)).send()).thenReturn(transactionReceipt);
//        Mockito.when(Proxy.getAddTokenEvents(Mockito.eq(transactionReceipt))).thenReturn(List.of(addTokenEventResponse));
////        Mockito.when(addTokenEventResponse.token()).thenReturn("your_token");
//
//        // Act
//        String result = tokenService.addToken(address);
//
//        // Assert
////        Mockito.verify(proxyContract).addToken(Mockito.eq(address));
////        Mockito.verify(Proxy).getAddTokenEvents(Mockito.eq(transactionReceipt));
////        Mockito.verify(addTokenEventResponse).token();
////        Mockito.verifyNoMoreInteractions(proxyContract, Proxy, addTokenEventResponse);
//        Assertions.assertEquals("your_token", result);
//    }
//
//    @Test
//    public void addTokenSuccessTest() throws Exception {
//        String address = "0x9A676e781A523b5d0C0e43731313A708CB607508";
//        Proxy mock = mock(Proxy.class);
//        RemoteFunctionCall remoteFunctionCall = mock(RemoteFunctionCall.class);
//        TransactionReceipt transactionReceipt = mock(TransactionReceipt.class);
//        Proxy.AddTokenEventResponse eventResponse = new Proxy.AddTokenEventResponse();
//        eventResponse.token = address;
//
//        when(mock.addToken(address)).thenReturn(remoteFunctionCall);
//        when(mock.addToken(address).send()).thenReturn(createTransactionReceipt());
//        when(mock.getAddTokenEvents(createTransactionReceipt()).get(0)).thenReturn(eventResponse);
//        String addedToken = tokenService.addToken(address);
////        when(mock.addToken(addedToken)).thenReturn(remoteFunctionCall)
//        assertEquals(addedToken, address);
//
//    }
//
//
//    private Log createLog() {
//        Log log = new Log();
//        log.setRemoved(true);
//        log.setLogIndex("0x123");
//        log.setTransactionIndex("0x456");
//        log.setTransactionHash("0xabc123");
//        log.setBlockHash("0xdef456");
//        log.setBlockNumber("0x789");
//        log.setAddress("0xAddress123");
//        log.setData("0xData123");
//        log.setType("LogType");
//
//        List<String> topics = new ArrayList<>();
//        topics.add("0xTopic1");
//        topics.add("0xTopic2");
//        log.setTopics(topics);
//        return log;
//    }
//
//    private TransactionReceipt createTransactionReceipt() {
//        TransactionReceipt receipt = new TransactionReceipt();
//        receipt.setTransactionHash("0x123456789abcdef0");
//        receipt.setTransactionIndex("0x1");
//        receipt.setBlockHash("0xabcdef0123456789");
//        receipt.setBlockNumber("0x1000");
//        receipt.setCumulativeGasUsed("0x2000");
//        receipt.setGasUsed("0x1000");
//        receipt.setContractAddress("0xContractAddress");
//        receipt.setRoot("0xRootHash");
//        receipt.setStatus("0x1"); // Status OK
//        receipt.setFrom("0xFromAddress");
//        receipt.setTo("0xToAddress");
//
//        // Create a list of logs
//        List<Log> logs = new ArrayList<>();
//        Log log1 = new Log();
//        log1.setRemoved(true);
//        log1.setLogIndex("0x1");
//        log1.setTransactionIndex("0x1");
//        log1.setTransactionHash("0xabcdef1234567890");
//        log1.setBlockHash("0xabcdef0987654321");
//        log1.setBlockNumber("0x1000");
//        log1.setAddress("0xLogAddress1");
//        log1.setData("0xLogData1");
//        log1.setType("LogType1");
//        List<String> topics1 = new ArrayList<>();
//        topics1.add("0xe473c74f34be27c1464d6624f14a0d7fd4e301cbfa29c3eba425d378c8a7ebe0");
//        topics1.add("0xTopic2");
//        log1.setTopics(topics1);
//
//        Log log2 = new Log();
//        log2.setRemoved(true);
//        log2.setLogIndex("0x2");
//        log2.setTransactionIndex("0x2");
//        log2.setTransactionHash("0x123456abcdef0987");
//        log2.setBlockHash("0x987654abcdef3210");
//        log2.setBlockNumber("0x1001");
//        log2.setAddress("0xLogAddress2");
//        log2.setData("0xLogData2");
//        log2.setType("LogType2");
//        List<String> topics2 = new ArrayList<>();
//        topics2.add("0xTopic3");
//        topics2.add("0xTopic4");
//        log2.setTopics(topics2);
//
//        logs.add(log1);
//        logs.add(log2);
//        receipt.setLogs(logs);
//        return receipt;
//    }
//
//
//}
