//contracts/Tpulse.sol
//SPDX-License-Identifier: MIT
//Version: 0.0.1
pragma solidity ^0.8.15;

contract CounterAgent{

    event AgentInc(address user, bool value);
    event AgentGet(address user, bool value);

    address _counterAgent;

    constructor(address counterAgent){
        _counterAgent = counterAgent;
    }

    function inc() public{
        (bool success, ) = _counterAgent.call(abi.encodeWithSignature("inc()"));
        emit AgentInc(msg.sender, success);
    }

    function current() public returns (uint){
        (bool success, ) = _counterAgent.call(abi.encodeWithSignature("current()"));
        emit AgentGet(msg.sender, success);
        return 1;
    }

}
