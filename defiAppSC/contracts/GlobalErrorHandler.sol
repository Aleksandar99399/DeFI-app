// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

interface GlobalErrorHandler {
    error ExceededLimitPerUser(string message);
    error ExceededLimitPerContract(string message);
    error UserAlreadyStakedAmount();
    error UserHasNotStakedAmount();
    error DeterminedPeriodIsNotExpired();
    error TokenDoesntExist();
    error TokenAlreadyExists();
    error NotStakedToken(address token);
}
