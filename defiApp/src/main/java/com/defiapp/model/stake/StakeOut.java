package com.defiapp.model.stake;

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
public class StakeOut {
    private String user;
    private String tokenAddress;
    private BigInteger countStakedTokens;
    private Timestamp stakeCreateTime;
    private Timestamp endDate;
}
