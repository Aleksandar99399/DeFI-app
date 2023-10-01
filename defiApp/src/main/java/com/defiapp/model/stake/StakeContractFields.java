package com.defiapp.model.stake;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigInteger;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StakeContractFields {

    private BigInteger yield;
    private BigInteger limitPerUser;
    private BigInteger limitPerContract;
    private BigInteger collectedTokens;
    private String lockPeriod;
    private StakeOut userData;
}
