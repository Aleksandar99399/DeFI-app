package com.defiapp.model;

import lombok.*;

import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TokenOut {
    private String name;
    private String symbol;
    private BigInteger decimals;
}
