package com.defiapp.model;

import lombok.*;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WithdrawOut {
    private String user;
    private BigInteger tokens;
}
