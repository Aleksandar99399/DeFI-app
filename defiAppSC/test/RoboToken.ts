import { expect } from "chai";
import { ethers } from "hardhat";
import { Signer } from "ethers";
import { RoboToken } from "../typechain-types";
 
describe('RoboToken', function() {
    let roboToken: RoboToken ;

    let realOwner: Signer, realAddr: Signer;

    this.beforeEach(async () => {
        roboToken = await ethers.deployContract("RoboToken");
        await roboToken.waitForDeployment(); 
 
        const [owner, addr1] = await ethers.getSigners();
        realOwner = owner;
        realAddr = addr1;
    })

    describe('', function () {
        it("Mint tokens", async () => {
            await roboToken.mint(realAddr.getAddress(), 15);
            expect(await roboToken.balanceOf(realAddr.getAddress())).to.be.equal(15)
            expect(await roboToken.totalSupply()).to.be.equal(15)
        });
        it("Transfer tokens", async () => {
           await roboToken.mint(realOwner.getAddress(), 20);
           await roboToken["transfer(address,address,uint256)"](realOwner.getAddress(), realAddr.getAddress(), 15);
           expect(await roboToken.balanceOf(realAddr.getAddress())).to.be.equal(15)
           expect(await roboToken.balanceOf(realOwner.getAddress())).to.be.equal(5)
        });
        it("Burn tokens", async () => {
           await roboToken.mint(realOwner.getAddress(), 22);
           await roboToken.burn(realOwner.getAddress(), 15);
           expect(await roboToken.balanceOf(realOwner.getAddress())).to.be.equal(7)
        });
        
    });
})


  