package com.defiapp.controller;

import com.defiapp.model.TokenOut;
import com.defiapp.service.impl.TokenServiceImpl;
import com.defiapp.web.TokenController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigInteger;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TokenControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TokenServiceImpl tokenService;

    private TokenController tokenController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenController = new TokenController(tokenService);
        mockMvc = MockMvcBuilders.standaloneSetup(tokenController).build();
    }

    @Test
    public void testGetTokenProperties() throws Exception {
        String address = "0xValidAddress";
        TokenOut tokenOut = new TokenOut("TokenName", "TokenSymbol", BigInteger.valueOf(18));

        when(tokenService.getTokenData(address)).thenReturn(Optional.of(tokenOut));

        mockMvc.perform(get("/api/token/{address}", address))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("TokenName")))
                .andExpect(jsonPath("$.symbol", is("TokenSymbol")))
                .andExpect(jsonPath("$.decimals", is(18)));
    }

    @Test
    public void testAddTokenToNetwork() throws Exception {
        String address = "0xValidAddress";

        when(tokenService.addToken(address)).thenReturn("AddedTokenAddress");

        mockMvc.perform(post("/api/token/{address}", address)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Added token: AddedTokenAddress")));
    }
}
