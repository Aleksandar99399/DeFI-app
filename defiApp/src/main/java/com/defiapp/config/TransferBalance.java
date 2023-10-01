package com.defiapp.config;


import com.defiapp.contracts.BaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Arrays;

@Component
public class TransferBalance implements CommandLineRunner {
    private final Environment environment;
    private final BaseToken baseToken;

    @Autowired
    public TransferBalance(Environment environment, BaseToken baseToken) {
        this.environment = environment;
        this.baseToken = baseToken;
    }

    @Override
    public void run(String... args) {
        System.out.println("In interact command line runner");
        try {
//            String myOwnerPublicKey = environment.getProperty("api.ownerPublicKey");
//            String myAccountPublicKey = environment.getProperty("api.0x879DeAce0FeF7a60858377b5b675e5Ebd896FCa1");
            String localOwnerPublicKey = environment.getProperty("api.localOwnerPublicKey");
            String localAccountPublicKey = environment.getProperty("api.localAccountPublicKey");

            BigInteger localOwnerBalance = baseToken.balanceOf(localOwnerPublicKey).send();
            BigInteger localUserBalance = baseToken.balanceOf(localAccountPublicKey).send();


            if (localUserBalance.compareTo(BigInteger.valueOf(0)) < 1) {
                baseToken.transfer(
                        localOwnerPublicKey,
                        localAccountPublicKey,
                        BigInteger.valueOf(200)
                ).send();
            }

        } catch (Exception ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }

    }

}
