import { ethers, run } from "hardhat";
import dotenv from "dotenv";
dotenv.config();

export async function main() {
  const wallet = new ethers.Wallet(`${process.env.PRIVATE_KEY}`, ethers.provider);
  const Proxy_Factory = await ethers.getContractFactory("Proxy");
  const proxy = await Proxy_Factory.connect(wallet).deploy();
  // await proxy.waitForDeployment();
  console.log(`Proxy contract is deployed to ${proxy.target}`); 
  
  await proxy.deploymentTransaction()?.wait(5);

  try {
    await run("verify:verify", {
      address: proxy.target,
    });
    console.log("Verified");
  } catch (e: any) {
    if (e.message.toLowerCase().includes("already verified")) {
      console.log("Already verified!");
    } else {
      console.log(e);
    }
  }
}


