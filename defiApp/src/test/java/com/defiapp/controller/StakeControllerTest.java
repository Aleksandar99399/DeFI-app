package com.defiapp.controller;

import com.defiapp.model.WithdrawOut;
import com.defiapp.model.stake.StakeContractFields;
import com.defiapp.model.stake.StakeOut;
import com.defiapp.model.stake.StakeRequest;
import com.defiapp.service.StakeService;
import com.defiapp.web.StakeController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StakeController.class)
public class StakeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StakeService stakeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetStakedTokenData_OnlyOneProperty() throws Exception {

        StakeContractFields mockContractFields = new StakeContractFields();
        mockContractFields.setYield(BigInteger.valueOf(25));
        when(stakeService.getStakedTokenData(any(), any())).thenReturn(mockContractFields);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contract")
                        .param("property", "yield"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yield").value(mockContractFields.getYield()));
    }
    @Test
    public void testGetStakedTokenData_WithouthProperty() throws Exception {

        StakeContractFields mockContractFields = new StakeContractFields();
        mockContractFields.setYield(BigInteger.valueOf(25));
        when(stakeService.getStakedTokenData(any(), any())).thenReturn(mockContractFields);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contract"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yield").value(mockContractFields.getYield()));
    }
    @Test
    public void testGetStakedTokenData_WithInvalidProperty() throws Exception {

        StakeContractFields mockContractFields = new StakeContractFields();
        mockContractFields.setYield(BigInteger.valueOf(25));
        when(stakeService.getStakedTokenData(any(), any())).thenReturn(mockContractFields);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contract")
                        .param("property", "dsad"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetStakedTokenData() throws Exception {

        StakeContractFields mockContractFields = new StakeContractFields();
        mockContractFields.setYield(BigInteger.valueOf(25));
        mockContractFields.setLimitPerContract(BigInteger.valueOf(200));
        mockContractFields.setLimitPerUser(BigInteger.valueOf(15));
        when(stakeService.getStakedTokenData(any(), any())).thenReturn(mockContractFields);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contract")
                        .param("property", "yield")
                        .param("user", "0x70997970C51812dc3A010C7d01b22e0d17dc79C8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.yield").value(mockContractFields.getYield()))
                .andExpect(jsonPath("$.limitPerContract").value(mockContractFields.getLimitPerContract()))
                .andExpect(jsonPath("$.limitPerUser").value(mockContractFields.getLimitPerUser()));
    }
    @Test
    public void testGetStakedTokenData_WithValidUser() throws Exception {

        StakeContractFields mockContractFields = new StakeContractFields();
        mockContractFields.setYield(BigInteger.valueOf(25));
        StakeOut stakeOut = new StakeOut();
        stakeOut.setCountStakedTokens(BigInteger.valueOf(20));
        stakeOut.setTokenAddress("0x33411231432");
        mockContractFields.setUserData(stakeOut);
        when(stakeService.getStakedTokenData(any(), any())).thenReturn(mockContractFields);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contract")
                        .param("property", "yield")
                        .param("user", "0x70997970C51812dc3A010C7d01b22e0d17dc79C8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.yield").value(mockContractFields.getYield()))
                .andExpect(jsonPath("$.userData.countStakedTokens").value(mockContractFields.getUserData().getCountStakedTokens()))
                .andExpect(jsonPath("$.userData.tokenAddress").value(mockContractFields.getUserData().getTokenAddress()));

    }

    @Test
    public void testDoStake() throws Exception {

        StakeRequest request = new StakeRequest();
        request.setAmount(BigInteger.valueOf(123));
        request.setToken("0x70997970C51812dc3A010C7d01b22e0d17dc79C8");
        StakeOut stakeOut = new StakeOut();
        stakeOut.setTokenAddress("0x70997970C51812dc3A010C7d01b22e0d17dc79C8");
        stakeOut.setCountStakedTokens(BigInteger.valueOf(123));

        ObjectMapper mapper = new ObjectMapper();
        String jsonData = mapper.writeValueAsString(request);
        ObjectMapper mapperOut = new ObjectMapper();
        String jsonDataOut = mapperOut.writeValueAsString(stakeOut);

        when(stakeService.stakeToken(request)).thenReturn(stakeOut);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/stake")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonDataOut));
    }

    @Test
    public void testDoStake_InvalidAmount() throws Exception {

        StakeRequest request = new StakeRequest();
        request.setAmount(BigInteger.valueOf(-123));
        request.setToken("0x70997970C51812dc3A010C7d01b22e0d17dc79C8");

        ObjectMapper mapper = new ObjectMapper();
        String jsonData = mapper.writeValueAsString(request);

        when(stakeService.stakeToken(request)).thenReturn(new StakeOut());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/stake")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.amount").value("Incorrect amount tokens"));
    }
    @Test
    public void testDoStake_InvalidAddress() throws Exception {

        StakeRequest request = new StakeRequest();
        request.setAmount(BigInteger.valueOf(123));
        request.setToken("0x70997970C5181201b22e0d17dc79C8");

        ObjectMapper mapper = new ObjectMapper();
        String jsonData = mapper.writeValueAsString(request);

        when(stakeService.stakeToken(request)).thenReturn(new StakeOut());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/stake")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.token").value("Invalid address"));
    }

    @Test
    public void testDoWithdraw_WithNULLuser() throws Exception {
        // Mock the service response
        WithdrawOut mockWithdrawOut = new WithdrawOut();
        mockWithdrawOut.setUser("0x70997970C51812dc3A010C7d01b22e0d17dc79C8");
        mockWithdrawOut.setTokens(BigInteger.valueOf(123));
        when(stakeService.withdraw("0x70997970C51812dc3A010C7d01a72e0d17dc79C8")).thenReturn(mockWithdrawOut);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/withdraw")
//                        .param("token", null)
                        )
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testDoWithdraw() throws Exception {
        // Mock the service response
        WithdrawOut mockWithdrawOut = new WithdrawOut();
        mockWithdrawOut.setUser("0x70997970C51812dc3A010C7d01b22e0d17dc79C8");
        mockWithdrawOut.setTokens(BigInteger.valueOf(123));
        when(stakeService.withdraw("0x70997970C51812dc3A010C7d01a72e0d17dc79C8")).thenReturn(mockWithdrawOut);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/withdraw")
                        .param("token", "0x70997970C51812dc3A010C7d01a72e0d17dc79C8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokens").value(BigInteger.valueOf(123)))
                .andExpect(jsonPath("$.user").value("0x70997970C51812dc3A010C7d01b22e0d17dc79C8"));


    }
}
