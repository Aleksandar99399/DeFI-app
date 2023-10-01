import { expect } from "chai";
import { ethers } from "hardhat";
import { Signer } from "ethers";
import { Proxy, BaseToken } from "../typechain-types";
 

describe('Proxy', function() {
    let proxy: Proxy ;
    let baseToken: BaseToken ;

    let realOwner: Signer, realAddr: Signer;

    this.beforeEach(async () => {
        proxy = await ethers.deployContract("Proxy");
        await proxy.waitForDeployment(); 

        baseToken = await ethers.deployContract("BaseToken",["Beta", "BTK"]);
        await baseToken.waitForDeployment();

        const [owner, addr1] = await ethers.getSigners();
        realOwner = owner;
        realAddr = addr1;
    })

    describe('', function () {
        it("Only owner can add token", async () => {
            await expect(proxy.connect(realAddr).addToken(baseToken.target)).to.be.reverted;
        });
        it("Return false for non existing token", async () => {
            expect((await proxy.doesTokenExist(baseToken.target))).to.be.equal(false);
        });
        it("Add token", async () => {
            await addTokenInProxy();
            expect((await proxy.doesTokenExist(baseToken.target))).to.be.equal(true);
        });
        it("Throw token already exists", async () => {
            await addTokenInProxy();
            await expect(proxy.addToken(baseToken.target))
                .to.be.revertedWithCustomError(proxy, "TokenAlreadyExists");
        });
        it("Throw exception TokenDoesntExist", async () => {

            await expect(proxy.loadTokenData(baseToken.target)).to.be.revertedWithCustomError(proxy, "TokenDoesntExist");
        });
        it("Load token data", async () => {
            await addTokenInProxy();
            const structData = await proxy.loadTokenData(baseToken.target);
            expect(structData.name).to.equal(await baseToken.name());
        });

    });

    async function addTokenInProxy() {
        await proxy.addToken(baseToken.target);
    }
})


  