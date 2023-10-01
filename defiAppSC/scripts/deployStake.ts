import { ethers, run } from "hardhat";

export async function main(proxy: any, liquidToken: any) {
  const wallet = new ethers.Wallet(`${process.env.LOCAL_PRIVATE_KEY}`, ethers.provider);
  const Stake_Factory = await ethers.getContractFactory("Stake");
  const stake = await Stake_Factory.connect(wallet).deploy(proxy, liquidToken);
  // await stake.waitForDeployment();
  console.log(`Stake contract is deployed to ${stake.target}`);
  // await stake.deploymentTransaction()?.wait(5)
  
  // try {
  //   await run("verify:verify", {
  //     address: stake.target,
  //     constructorArguments: [
  //       proxy,
  //       liquidToken
  //     ]
  //   });
  //   console.log("Verified");
  // } catch (e: any) {
  //   if (e.message.toLowerCase().includes("already verified")) {
  //     console.log("Already verified!");
  //   } else {
  //     console.log(e);
  //   }
  // }
}
