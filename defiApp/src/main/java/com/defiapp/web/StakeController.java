package com.defiapp.web;

import com.defiapp.model.stake.StakeContractFields;
import com.defiapp.model.stake.StakeOut;
import com.defiapp.model.WithdrawOut;
import com.defiapp.model.stake.StakeRequest;
import com.defiapp.service.StakeService;
import com.defiapp.validation.PropertyParam;
import com.defiapp.validation.EthereumAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Validated
public class StakeController {

    private final StakeService stakeService;

    @Autowired
    public StakeController(StakeService stakeService) {
        this.stakeService = stakeService;
    }

    @GetMapping("/contract")
    public ResponseEntity<StakeContractFields> getStakedTokenData(
            @RequestParam(required = false, name = "property") @PropertyParam String property,
            @RequestParam(required = false, defaultValue = "")
            @EthereumAddress(message = "Invalid user address")
            String user) {
        return ResponseEntity.ok(stakeService.getStakedTokenData(property, user).get());
    }

    @PostMapping("/stake")
    public ResponseEntity<StakeOut> doStake(@Valid @RequestBody StakeRequest stakeRequest) {
        return ResponseEntity.ok(stakeService.stakeToken(stakeRequest).get());
    }

    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawOut> doWithdraw(@EthereumAddress @RequestParam String token) {
        return ResponseEntity.ok(stakeService.withdraw(token).get());
    }
}
