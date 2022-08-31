//contracts/TpulseBase.sol
//SPDX-License-Identifier: MIT
//Version: 0.0.1
pragma solidity ^0.8.15;

import "./node_modules/@openzeppelin/contracts/token/ERC1155/presets/ERC1155PresetMinterPauser.sol";
import "./node_modules/@openzeppelin/contracts/access/Ownable.sol";
import "./node_modules/@openzeppelin/contracts/token/ERC20/ERC20.sol";

abstract contract TpulseBase is Context, Ownable, ERC1155PresetMinterPauser {

    event Expense(address receiver, uint value, bytes10 currency);
    event Income(address receiver, uint value, bytes10 currency);
    event CreatorIncome(address receiver, uint value, bytes10 currency);
    event ServiceIncome(address receiver, uint value, bytes10 currency);

    bytes8 constant CURRENCY_ETH = "ETH";
    bytes8 constant CURRENCY_WETH = "WETH";
    uint8 public constant RATE_DECIMALS = 4;

    struct Listing {
        address seller;
        uint256 assetsId;
        uint256 quality;
    }

    struct CreatorProfile{
        address creator;
        uint256 creatorFeeRate;
    }

    struct Offer {
        address from;
        uint256 assetsId;
        uint32 quantity;
        uint256 price;
        uint64 expireAt;
    }

    function _getServiceFeeRate() internal view virtual returns(uint256);

    function _getCreator(uint token) internal view virtual returns(address, uint);

    function _getWethAddress()internal view virtual returns(address);

    function _calRate(uint price, uint rate) internal view returns(uint){
        if(rate == 0){
            return 0;
        }else{
            return price / (10**RATE_DECIMALS) * rate;
        }
    }

}