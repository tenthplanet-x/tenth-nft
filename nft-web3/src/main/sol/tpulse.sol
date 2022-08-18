// contracts/Tpulse.sol
// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "node_modules/@openzeppelin/contracts/token/ERC1155/presets/ERC1155PresetMinterPauser.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/security/PullPayment.sol";

contract Tpulse is ERC1155PresetMinterPauser, Ownable, PullPayment {

    uint256 _serviceFeeRate = .25;

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
        Signature signature;
    }

    constructor(uint256 contractFeeRate) ERC1155PresetMinterPauser("https://nft.ruixi-sh.com/openapi/nft/{id}.json") {
        _transferOwnership(msg.sender);
        setContractFeeRate(contractFeeRate);
    }

    function setContractFee(uint256 serviceFeeRate){
        _serviceFeeRate = serviceFeeRate;
    }

    //buy
    function buy(Listing listing) public payable{

        require(balanceOf(listing.seller, listing.assetsId) > listing.quality, "Insufficient assets");

        //transfer assets
        _safeTransferFrom(listing.seller, msg.sender, listing.assetsId, listing.quality);

        //service fee
        uint256 serviceFee = msg.value * _serviceFeeRate;
        _asyncTransfer(address(this), serviceFee);

        //pay
        uint256 amountForPay = msg.value - serviceFee;
        address payable payee = payable(listing.seller);
        payee.transfer(amountForPay);

    }
}