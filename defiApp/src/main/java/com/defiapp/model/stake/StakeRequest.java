package com.defiapp.model.stake;

import com.defiapp.validation.EthereumAddress;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class StakeRequest {
    @EthereumAddress
    private String token;
    @Min(value = 1, message = "Incorrect amount tokens")
    @NotNull(message = "Amount cannot be null")
    private BigInteger amount;
}
