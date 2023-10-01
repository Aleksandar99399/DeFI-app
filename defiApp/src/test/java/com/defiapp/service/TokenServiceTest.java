package com.defiapp.service;

import com.defiapp.contracts.Proxy;
import com.defiapp.exceptions.customEx.TokenAlreadyExist;
import com.defiapp.exceptions.customEx.TokenDoesntExist;
import com.defiapp.service.impl.TokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TokenServiceTest {


    @InjectMocks
    private TokenServiceImpl tokenService;

    @MockBean
    private Proxy proxy;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenService = new TokenServiceImpl(proxy);
    }

    @Test
    public void getTokenData_throwTokenDoesntExist() {
        String address = "0x5FbDB2315678afecb367f032d93F642f64180ca3";
        Mockito.when(proxy.loadTokenData(address)).thenThrow(new RuntimeException("TokenDoesntExist"));
        assertThrows(TokenDoesntExist.class, () -> tokenService.getTokenData(address));
    }

    @Test
    public void testAddTokenException() {
        String address = "0xTokenAddress";
        when(proxy.addToken(address)).thenThrow(new RuntimeException("TokenAlreadyExists"));
        assertThrows(TokenAlreadyExist.class, () -> tokenService.addToken(address));
    }

    @Test
    public void addTokenSuccessTest() {
        String address = "0x9A676e781A523b5d0C0e43731313A708CB607508";

        assertThatCode(() -> proxy.addToken(address)).doesNotThrowAnyException();
    }

}