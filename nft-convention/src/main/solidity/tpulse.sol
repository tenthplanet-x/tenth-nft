//contracts/Tpulse.sol
//SPDX-License-Identifier: MIT
//Version: 0.0.1
pragma solidity ^0.8.0;

import "node_modules/@openzeppelin/contracts/token/ERC1155/presets/ERC1155PresetMinterPauser.sol";
import "node_modules/@openzeppelin/contracts/access/Ownable.sol";

contract Tpulse is ERC1155PresetMinterPauser, Ownable {

    event Expense(address receiver, uint value);
    event Income(address receiver, uint value);
    event CreatorIncome(address receiver, uint value);
    event ServiceIncome(address receiver, uint value);

    struct Listing {
        address seller;
        uint256 assetsId;
        uint256 quality;
    }

    struct CreatorProfile{
        address creator;
        uint256 creatorFeeRate;
    }

    uint256 _serviceFeeRate = 2500;
    uint256 _ratePrecision = 4;
    mapping(uint256 => CreatorProfile) private _creatorProfiles;

    constructor(string memory uri, uint256 serviceFeeRate, uint256 ratePrecision) ERC1155PresetMinterPauser(uri) {
        _transferOwnership(msg.sender);
        _serviceFeeRate = serviceFeeRate;
        _ratePrecision = ratePrecision;
    }

    //mint
    function mintWithCreatorFeeRate(
        address to,
        uint256 id,
        uint256 amount,
        uint256 creatorFeeRate
    ) public virtual {

        mint(to, id, amount, "");
        _creatorProfiles[id] = CreatorProfile(to, creatorFeeRate);
    }

    //creatorProfile of token
    function creatorProfileOf(uint256 id) public view returns(address, uint256) {
        return (_creatorProfiles[id].creator, _creatorProfiles[id].creatorFeeRate);
    }

    //buy
    function buy(Listing calldata listing) public payable{

        //require(listing.amount == 0, "Illegal listing");

        //transfer assets
        require(isApprovedForAll(listing.seller, owner()), "Tpulse: seller is not approved");
        _safeTransferFrom(listing.seller, msg.sender, listing.assetsId, listing.quality, "");

        //payment split
        uint256 serviceFee = msg.value / (10**_ratePrecision) * _serviceFeeRate;
        CreatorProfile memory creatorProfile = _creatorProfiles[listing.assetsId];
        uint256 creatorFee = msg.value / (10**_ratePrecision) * creatorProfile.creatorFeeRate;
        uint256 profit = msg.value - serviceFee - creatorFee;

        //profit
        _payFor(listing.seller, profit);
        emit Income(listing.seller, profit);
        //service
        _payFor(owner(), serviceFee);
        emit ServiceIncome(owner(), serviceFee);
        //creatorFee
        _payFor(creatorProfile.creator, creatorFee);
        emit CreatorIncome(creatorProfile.creator, creatorFee);
        //expense
        emit Expense(msg.sender, msg.value);
    }

    function _payFor(address user, uint value) public payable{
        payable(user).transfer(value);
    }

    receive() external payable{

    }

}