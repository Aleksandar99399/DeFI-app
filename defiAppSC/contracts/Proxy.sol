// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "node_modules/@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "node_modules/@openzeppelin/contracts/access/Ownable.sol";
import "./GlobalErrorHandler.sol";

contract Proxy is Ownable, GlobalErrorHandler {
    mapping(address => bool) public doesTokenExist;

    event AddToken(address token);

    struct TokenData {
        string name;
        string symbol;
        uint decimals;
    }

    modifier isERC20(address _token) {
        ERC20 ercToken = ERC20(_token);
        if (ercToken.totalSupply() == 0) {
            revert TokenDoesntExist();
        }
        _;
    }

    function addToken(address _token) external onlyOwner isERC20(_token) {
        if (doesTokenExist[_token]) {
            revert TokenAlreadyExists();
        }
        doesTokenExist[_token] = true;
        emit AddToken(_token);
    }

    function loadTokenData(
        address _token
    ) external view isERC20(_token) returns (TokenData memory) {
        if (!doesTokenExist[_token]) {
            revert TokenDoesntExist();
        }

        ERC20 ercToken = ERC20(_token);
        TokenData memory tokenData = TokenData(
            ercToken.name(),
            ercToken.symbol(),
            ercToken.decimals()
        );

        return tokenData;
    }
}
