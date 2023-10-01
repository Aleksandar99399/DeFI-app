import { expect, assert } from "chai";
import { ethers } from "hardhat";
import { Signer } from "ethers";
import {BaseToken, Proxy, Stake, RoboToken } from "../typechain-types";
 
describe('Stake contract', function() {
    // let baseToken: any ;
    let stakeContract: Stake ;
    let baseToken: BaseToken;
    let proxy: Proxy ;
    let roboToken: RoboToken;
    let realOwner: Signer, realAddr: Signer, realAddr2: Signer, realAddr3: Signer,realAddr4: Signer,realAddr5: Signer;

    this.beforeEach(async () => {
        proxy = await ethers.deployContract("Proxy");
        await proxy.waitForDeployment(); 
        baseToken = await ethers.deployContract("BaseToken",["Alpha", "ATK"]);
        await baseToken.waitForDeployment(); 
        roboToken = await ethers.deployContract("RoboToken");
        await roboToken.waitForDeployment(); 
        stakeContract = await ethers.deployContract("Stake",[proxy.target, roboToken.target]);
        await stakeContract.waitForDeployment(); 



        const [owner, addr1, addr2, addr3, addr4, addr5] = await ethers.getSigners();
        realOwner = owner;
        realAddr = addr1;
        realAddr2 = addr2;
        realAddr3 = addr3;
        realAddr4 = addr4;
        realAddr5 = addr5;
    })

    describe('Stake', function () {
        it("Throw exception non existing token", async () => {
            await expect(stakeContract.stake(baseToken.target, 50))
                .to.be.revertedWithCustomError(stakeContract, "TokenDoesntExist");

        });
        it("Throw exception exceeded limit per user", async () => {
            await addTokenInProxy();
            await expect(stakeContract.stake(baseToken.target, 150))
                .to.be.revertedWithCustomError(stakeContract,'ExceededLimitPerUser')
                .withArgs("The maximum tokens per user: 100");
        });
        it("Throw exception exceeded limit per contract", async () => {
            await addTokenInProxy();
            await baseToken["transfer(address,address,uint256)"](realOwner.getAddress(), realAddr.getAddress(), 200);
            await baseToken["transfer(address,address,uint256)"](realOwner.getAddress(), realAddr2.getAddress(), 200);
            await baseToken["transfer(address,address,uint256)"](realOwner.getAddress(), realAddr3.getAddress(), 200);
            await baseToken["transfer(address,address,uint256)"](realOwner.getAddress(), realAddr4.getAddress(), 200);
            await baseToken["transfer(address,address,uint256)"](realOwner.getAddress(), realAddr5.getAddress(), 200);
            
            await stakeContract.connect(realAddr).stake(baseToken.target, 100);
            await stakeContract.connect(realAddr2).stake(baseToken.target, 100);
            await stakeContract.connect(realAddr3).stake(baseToken.target, 100);
            await stakeContract.connect(realAddr4).stake(baseToken.target, 100);
            await expect(stakeContract.connect(realAddr5).stake(baseToken.target, 100))
            .to.be.revertedWithCustomError(stakeContract,'ExceededLimitPerContract')
            .withArgs("The maximum tokens per contract: 400");
        });
        it("Throw exception user has already staked tokens", async () => {
            await addTokenInProxy();
            await baseToken["transfer(address,address,uint256)"](realOwner.getAddress(), realAddr3.getAddress(), 200);
            await stakeContract.connect(realAddr3).stake(baseToken.target, 100);
            await expect(stakeContract.connect(realAddr3).stake(baseToken.target, 100))
            .to.be.revertedWithCustomError(stakeContract,'UserAlreadyStakedAmount')
        });
        it("Correct stake tokens", async () => {
            await addTokenInProxy();
            await baseToken["transfer(address,address,uint256)"](realOwner.getAddress(), realAddr.getAddress(), 200);
            await stakeContract.connect(realAddr).stake(baseToken.target, 10); 
            expect((await stakeContract.connect(realAddr2).getStakedTokenData(realAddr.getAddress())).countStakedTokens).to.be.equal(10)
            expect(await baseToken.balanceOf(realAddr.getAddress())).to.be.equal(190);
            expect(await roboToken.balanceOf(realAddr.getAddress())).to.be.equal(10);
            expect(await baseToken.balanceOf(realOwner.getAddress())).to.be.equal(810);

        });
        
    });
    describe('Withdraw', function () {
        it("Throw exception non existing token", async () => {
            await expect(stakeContract.withdraw(baseToken.target))
            .to.be.revertedWithCustomError(stakeContract, "TokenDoesntExist")
        });
        it("Throw exception user has not staked tokens", async () => {
            await addTokenInProxy();
            await expect(stakeContract.connect(realAddr).withdraw(baseToken.target))
            .to.be.revertedWithCustomError(stakeContract, "UserHasNotStakedAmount")
        });
        it("Throw exception determined period is not expired", async () => {
            await addTokenInProxy();
            await baseToken["transfer(address,address,uint256)"](realOwner.getAddress(), realAddr.getAddress(), 200);

            await stakeContract.connect(realAddr).stake(baseToken.target, 15);
            await expect(stakeContract.connect(realAddr).withdraw(baseToken.target))
            .to.be.revertedWithCustomError(stakeContract, "DeterminedPeriodIsNotExpired")
        });
        it("Success withdraw", async () => {
            await addTokenInProxy();
            await baseToken["transfer(address,address,uint256)"](realOwner.getAddress(), realAddr.getAddress(), 200);
            await stakeContract.connect(realAddr).stake(baseToken.target, 15);
            await ethers.provider.send("evm_increaseTime",[864000]);
            await ethers.provider.send("evm_mine",[]);

            await expect(stakeContract.connect(realAddr).withdraw(baseToken.target))
                .to.emit(stakeContract, "Withdraw");
            expect(await baseToken.balanceOf(realOwner.getAddress())).to.be.equal(797);

        });    
    });
    it("Throw exception UserHasNotStakedAmount when getStakeTokenData", async () => {
        await expect(stakeContract.getStakedTokenData(realAddr.getAddress()))
        .to.be.revertedWithCustomError(stakeContract, "UserHasNotStakedAmount")
    });
    it("Get staked token data", async () => {
        await addTokenInProxy();
        await baseToken["transfer(address,address,uint256)"](realOwner.getAddress(), realAddr.getAddress(), 20);
        await stakeContract.connect(realAddr).stake(baseToken.target, 15);
        const tokenData = await stakeContract.getStakedTokenData(realAddr.getAddress());
        expect(tokenData.countStakedTokens).to.be.equal(15);

    });

    async function addTokenInProxy() {
        await proxy.addToken(baseToken.target);
    }
})




  