// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./BaseToken.sol";
import "./RoboToken.sol";
import "./Proxy.sol";
import "./GlobalErrorHandler.sol";

import "node_modules/@openzeppelin/contracts/token/ERC20/ERC20.sol";

contract Stake is GlobalErrorHandler {
    address public owner;
    uint8 public constant YIELD = 3;
    uint16 public constant limitTokensPerUser = 100;
    uint16 public constant limitTokensPerContract = 400;
    uint public collectedTokens;
    uint public constant LOCK_TIME = 10 days;
    mapping(address => StakeTokens) public stakedTokens;
    Proxy public proxy;
    RoboToken public roboToken;

    struct StakeTokens {
        address token;
        uint8 countStakedTokens;
        uint stakeCreatedTime;
        uint endDate;
    }

    constructor(address _proxy, address _roboToken) {
        roboToken = RoboToken(_roboToken);
        proxy = Proxy(_proxy);
        owner = msg.sender;
    }

    modifier tokenExist(address _token) {
        if (!proxy.doesTokenExist(_token)) {
            revert TokenDoesntExist();
        }
        _;
    }

    modifier senderTokenData() {
        if (stakedTokens[msg.sender].countStakedTokens <= 0) {
            revert UserHasNotStakedAmount();
        }
        _;
    }

    event Withdraw(address user, uint tokens);
    event StakeAmount(
        address user,
        address token,
        uint8 amount,
        uint createdDate,
        uint endDate
    );

    function stake(address _token, uint8 _amount) external tokenExist(_token) {
        if (limitTokensPerUser < _amount) {
            revert ExceededLimitPerUser("The maximum tokens per user: 100");
        }
        if (limitTokensPerContract < (_amount + collectedTokens)) {
            revert ExceededLimitPerContract(
                "The maximum tokens per contract: 400"
            );
        }
        StakeTokens memory userTokens = stakedTokens[msg.sender];
        if (userTokens.countStakedTokens > 0) {
            revert UserAlreadyStakedAmount();
        }

        userTokens = StakeTokens(
            _token,
            _amount,
            block.timestamp,
            block.timestamp + LOCK_TIME
        );

        BaseToken(_token).transfer(msg.sender, owner, _amount);
        roboToken.mint(msg.sender, _amount);
        stakedTokens[msg.sender] = userTokens;
        collectedTokens += _amount;

        emit StakeAmount(
            msg.sender,
            _token,
            _amount,
            block.timestamp,
            block.timestamp + LOCK_TIME
        );
    }

    function withdraw(
        address _token
    ) external tokenExist(_token) senderTokenData {
        StakeTokens storage userTokens = stakedTokens[msg.sender];

        if (block.timestamp <= userTokens.endDate) {
            revert DeterminedPeriodIsNotExpired();
        }

        roboToken.burn(msg.sender, userTokens.countStakedTokens);
        uint acumulatedAmount = userTokens.countStakedTokens + YIELD;
        BaseToken(_token).transfer(owner, msg.sender, acumulatedAmount);
        delete stakedTokens[msg.sender];

        emit Withdraw(msg.sender, acumulatedAmount);
    }

    function getStakedTokenData(
        address _user
    ) external view returns (StakeTokens memory) {
        if (stakedTokens[_user].countStakedTokens <= 0) {
            revert UserHasNotStakedAmount();
        }
        return stakedTokens[_user];
    }
}
