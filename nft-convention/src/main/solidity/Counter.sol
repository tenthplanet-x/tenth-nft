//contracts/Tpulse.sol
//SPDX-License-Identifier: MIT
//Version: 0.0.1
pragma solidity ^0.8.15;

contract Counter{

    event INC(address user, uint value);
    event GET(address user, uint value);

    uint counter;

    function inc() public returns(bool){
        counter += 1;
        emit INC(msg.sender, counter);
        return true;
    }

    function current() public returns (uint){
        emit GET(msg.sender, counter);
        return counter;
    }

}
