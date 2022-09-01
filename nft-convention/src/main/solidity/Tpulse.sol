//contracts/Tpulse.sol
//SPDX-License-Identifier: MIT
//Version: 0.0.1
pragma solidity ^0.8.15;

import "./TpulseBase.sol";
import "./TpulseBuy.sol";
import "./TpulseAccept.sol";

contract Tpulse is TpulseBase, TpulseBuy, TpulseAccept{

    uint public _serviceFeeRate = 2500;
    mapping(uint => CreatorProfile) public _creatorProfiles;
    address public _wethContractAddress;

    constructor(string memory uri, uint serviceFeeRate, address wethContractAddress) ERC1155PresetMinterPauser(uri) {
        _transferOwnership(msg.sender);
        _serviceFeeRate = serviceFeeRate;
        _wethContractAddress = wethContractAddress;
    }

    //mint
    function mintWithCreatorFeeRate(
        address to,
        uint token,
        uint amount,
        uint creatorFeeRate
    ) public virtual {

        mint(to, token, amount, "");
        _creatorProfiles[token] = CreatorProfile(to, creatorFeeRate);
    }

    function creatorProfileOf(uint token) public view returns(address, uint) {
        return (_creatorProfiles[token].creator, _creatorProfiles[token].creatorFeeRate);
    }

    function _getServiceFeeRate() override internal view returns(uint){
        return _serviceFeeRate;
    }

    function _getCreator(uint token) override internal view returns(address, uint){
        return (_creatorProfiles[token].creator, _creatorProfiles[token].creatorFeeRate);
    }

    function _getWethAddress() override internal view returns(address){
        return _wethContractAddress;
    }

}