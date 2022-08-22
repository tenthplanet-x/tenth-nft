//contracts/Tpulse.sol
//SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "node_modules/@openzeppelin/contracts/token/ERC1155/presets/ERC1155PresetMinterPauser.sol";
import "node_modules/@openzeppelin/contracts/access/Ownable.sol";

contract Tpulse is ERC1155PresetMinterPauser, Ownable {

    event Expense(address receiver, uint value);
    event Income(address receiver, uint value);
    event CreatorIncome(address receiver, uint value);
    event ServiceIncome(address receiver, uint value);

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
        uint256 amount;
        Signature signature;
    }

    struct CreatorProfile{
        address creator;
        uint256 creatorFeeRate;
    }

    uint256 _serviceFeeRate = 25;
    mapping(uint256 => CreatorProfile) private _creatorProfiles;

    constructor(string memory uri, uint256 serviceFeeRate) ERC1155PresetMinterPauser(uri) {
        _transferOwnership(msg.sender);
        _serviceFeeRate = serviceFeeRate;
    }

    //mint
    function mintWithCreatorFeeRate(
        address to,
        uint256 id,
        uint256 amount,
        bytes memory data,
        uint256 creatorFeeRate
    ) public virtual {
        require(hasRole(MINTER_ROLE, _msgSender()), "ERC1155PresetMinterPauser: must have minter role to mint");

        _mint(to, id, amount, data);
        _creatorProfiles[id] = CreatorProfile(to, creatorFeeRate);
    }

    //creatorProfile of token
    function creatorProfileOf(uint256 id) public view returns(address, uint256) {
        return (_creatorProfiles[id].creator, _creatorProfiles[id].creatorFeeRate);
    }

    //buy
    function buy(Listing calldata listing) public payable{

        require(listing.amount == 0, "Illegal listing");

        //transfer assets
        bytes memory empty;
        _safeTransferFrom(listing.seller, msg.sender, listing.assetsId, listing.quality, empty);

        //payment split
        uint256 serviceFee = msg.value * _serviceFeeRate / 100;
        CreatorProfile memory creatorProfile = _creatorProfiles[listing.assetsId];
        uint256 creatorFee = msg.value * creatorProfile.creatorFeeRate / 100;
        uint256 profit = msg.value - serviceFee - creatorFee;

        //profit
        _payFor(listing.seller, profit);
        emit Income(listing.seller, profit);
        //service
        _payFor(owner(), serviceFee);
        emit ServiceIncome(owner(), serviceFee);
        //creatorFee
        if(creatorFee > 0){
            _payFor(creatorProfile.creator, creatorFee);
            emit CreatorIncome(creatorProfile.creator, creatorFee);
        }
        //expense
        emit Expense(msg.sender, msg.value);
    }

    function _payFor(address user, uint value) public payable{
        payable(user).transfer(value);
    }

    function _bytesToUint(bytes calldata b) internal pure returns (uint256){
        uint256 number;
        for(uint i = 0 ; i < b.length; i++){
            number = number + uint(uint8(b[i]))*(2**(8*(b.length-(i+1))));
        }
        return number;
    }

    receive() external payable{

    }

}