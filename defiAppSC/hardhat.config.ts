import { HardhatUserConfig, task } from "hardhat/config";
import "@nomicfoundation/hardhat-toolbox";
import dotenv from "dotenv";
dotenv.config();

const config: HardhatUserConfig = {
  solidity: "0.8.19",
  networks: {
    goerli: {
      url: `https://goerli.infura.io/v3/${process.env.INFURA_PROJECT_ID}`,
      chainId: 5,
      accounts: [
        `${process.env.PRIVATE_KEY}`
      ],
    },
    sepolia: {
      url: `https://sepolia.infura.io/v3/${process.env.INFURA_PROJECT_ID}`,
      chainId: 11155111,
      accounts: [
        `${process.env.PRIVATE_KEY}`
      ],
    },
  },
  etherscan: {
    apiKey: `${process.env.ETHERSCAN_API_KEY}`
  }

};

const lazyImport = async (module: any) => {
  return await import(module);
};

task("deploy-token", "Deploy Token")
  .addParam("name", "Token's name")
  .addParam("symbol", "Token's symbol")
  .setAction(async ({name, symbol}) => {
    const { main } = await lazyImport("./scripts/deployToken");
    await main(name, symbol);
});

task("deploy-liquid-token", "Deploy liquid Token")
  .setAction(async () => {
    const { main } = await lazyImport("./scripts/deployLiquidToken");
    await main();
});

task("deploy-proxy", "Deploy Proxy")
  .setAction(async () => {
    const { main } = await lazyImport("./scripts/deployProxy");
    await main();
});

task("deploy-stake", "Deploy Stake")
  .addParam("proxy", "Deployed proxy address")
  .addParam("liquidtoken", "Deployed liquid token")
  .setAction(async ({proxy, liquidtoken}) => {
    const { main } = await lazyImport("./scripts/deployStake");
    await main(proxy, liquidtoken);
});


export default config;
