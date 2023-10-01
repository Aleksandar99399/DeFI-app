import { expect } from "chai";
import { ethers } from "hardhat";
import { Signer } from "ethers";
import { BaseToken } from "../typechain-types";
 
describe('Tokens', function() {
    let baseToken: BaseToken ;

    let realOwner: Signer, realAddr: Signer;

    this.beforeEach(async () => {
        baseToken = await ethers.deployContract("BaseToken", ["Alpha", "ATK"]);
        await baseToken.waitForDeployment(); 
 
        const [owner, addr1] = await ethers.getSigners();
        realOwner = owner;
        realAddr = addr1;
    })

    describe('', function () {
        it("Deploy contract", async () => {
            expect((await baseToken.totalSupply())).to.be.equal(1000);
        });
        it("Transfer tokens", async () => {
           await baseToken["transfer(address,address,uint256)"](realOwner.getAddress(), realAddr.getAddress(), 15);
           expect(await baseToken.balanceOf(realAddr.getAddress())).to.be.equal(15)
           expect(await baseToken.balanceOf(realOwner.getAddress())).to.be.equal(985)
        });

        it("Test", async () => {
            expect((await baseToken.balanceOf(realOwner))).to.be.equal(1000);
        });

    });
})


  