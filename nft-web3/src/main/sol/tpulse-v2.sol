// contracts/Tpulse.sol
// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "node_modules/@openzeppelin/contracts/token/ERC1155/presets/ERC1155PresetMinterPauser.sol";
import "node_modules/@openzeppelin/contracts/access/Ownable.sol";

contract TpulseV2 is ERC1155PresetMinterPauser, Ownable {

    event Received(address receiver, uint value);

    uint256 _serviceFeeRate = 25;

    struct Signature{
        bytes32 hash;
        uint8 v;
        bytes32 r;
        bytes32 s;
    }

    struct Listing {
        address seller;
        uint256 assetsId;
        uint256 quality;
        uint256 price;
        Signature signature;
    }

    constructor(uint256 serviceFeeRate) ERC1155PresetMinterPauser("https://nft.ruixi-sh.com/openapi/nft/{id}.json") {
        _transferOwnership(msg.sender);
        _serviceFeeRate = serviceFeeRate;
    }

    //buy
    function buy(Listing calldata listing) public payable{

//        require(balanceOf(listing.seller, listing.assetsId) > listing.quality, "Insufficient assets");
//        uint256 amount = listing.price * listing.quality;
//        require(msg.sender.balance >= amount, "Insufficient money");

        //transfer assets
        bytes memory empty;
        _safeTransferFrom(listing.seller, msg.sender, listing.assetsId, listing.quality, empty);

        //payment split
        uint256 serviceFee = msg.value * _serviceFeeRate / 100;
        uint256 profit = msg.value - serviceFee;
        _payFor(owner(), serviceFee);
        _payFor(listing.seller, profit);
    }

    function _payFor(address user, uint value) public payable{
        payable(user).transfer(value);
        emit Received(user, value);
    }

    receive() external payable{

    }

}