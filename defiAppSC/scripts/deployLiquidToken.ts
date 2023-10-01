import { ethers, run } from "hardhat";
import dotenv from "dotenv";
dotenv.config();

export async function main() {
  const wallet = new ethers.Wallet(`${process.env.LOCAL_PRIVATE_KEY}`, ethers.provider);
  const Token_Factory = await ethers.getContractFactory("RoboToken");
  const token = await Token_Factory.connect(wallet).deploy();
  await token.waitForDeployment();

  console.log(`Liquid token is deployed to ${token.target}`);

  // await token.deploymentTransaction()?.wait(5)
  
  // try {
  //   await run("verify:verify", {
  //     address: token.target,
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


