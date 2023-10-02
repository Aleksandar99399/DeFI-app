import { ethers, run } from "hardhat";
import dotenv from "dotenv";
dotenv.config();

export async function main(name: any, symbol: any) {
  const wallet = new ethers.Wallet(`${process.env.PRIVATE_KEY}`, ethers.provider);
  console.log('Before Token Factory');
  
  const Token_Factory = await ethers.getContractFactory("BaseToken");
  const token = await Token_Factory.connect(wallet).deploy(name, symbol);
  // await token.waitForDeployment();
  console.log(`Token is deployed to ${token.target}`);
  await token.deploymentTransaction()?.wait(5);

  try {
    console.log('Before Verify');
    await run("verify:verify", {
      address: token.target,
      constructorArguments: [
        name,
        symbol
      ]
    });
    console.log("Verified");
  } catch (e: any) {
    if (e.message.toLowerCase().includes("already verified")) {
      console.log("Already verified!");
    } else {
      console.log('In else');
      
      console.log(e);
    }
  }
}


