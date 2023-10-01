package com.defiapp.web;

import com.defiapp.model.TokenOut;
import com.defiapp.service.TokenService;
import com.defiapp.validation.EthereumAddress;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/token")
@Validated
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/{address}")
    public ResponseEntity<TokenOut> getTokenProperties(@EthereumAddress(message = "Invalid token address")
                                                           @PathVariable String address) {
        return ResponseEntity.ok(tokenService.getTokenData(address).get());
    }

    @PostMapping("/{address}")
    public ResponseEntity<String> addTokenToNetwork(@EthereumAddress(message = "Invalid token address")
                                                        @PathVariable String address) {
        return ResponseEntity.ok("Added token: " + tokenService.addToken(address));
    }
}
