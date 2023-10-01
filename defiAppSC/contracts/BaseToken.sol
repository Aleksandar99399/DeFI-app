// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "node_modules/@openzeppelin/contracts/token/ERC20/ERC20.sol";

contract BaseToken is ERC20 {
    address public owner;

    constructor(
        string memory _name,
        string memory _symbol
    ) ERC20(_name, _symbol) {
        owner = msg.sender;
        _mint(owner, 1000);
    }

    function transfer(address _from, address _to, uint _amount) external {
        _transfer(_from, _to, _amount);
    }
}
