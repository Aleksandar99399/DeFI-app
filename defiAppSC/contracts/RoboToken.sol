// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "node_modules/@openzeppelin/contracts/token/ERC20/ERC20.sol";

contract RoboToken is ERC20 {
    constructor() ERC20("Robo", "RTK") {}

    function mint(address _miner, uint _amount) external {
        _mint(_miner, _amount);
    }

    function transfer(address _from, address _to, uint _amount) external {
        _transfer(_from, _to, _amount);
    }

    function burn(address _from, uint amount) external {
        _burn(_from, amount);
    }
}
