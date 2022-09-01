//contracts/TpulseAccept.sol
//SPDX-License-Identifier: MIT
//Version: 0.0.1
pragma solidity ^0.8.15;

import "./node_modules/@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "./TpulseBase.sol";

abstract contract TpulseAccept is TpulseBase {

    function accept(
        address buyer,
        uint token,
        uint quantity,
        uint price
    ) public{

        //TODO Verify buyer signature

        //TODO
        //_safeTransferFrom();
        address seller = _msgSender();

        //transfer assets
        require(isApprovedForAll(seller, address(this)), "[buy]seller is not approved");
        _safeTransferFrom(seller, buyer, token, quantity, "");

        //fund split
        uint256 serviceFee = _calRate(price, _getServiceFeeRate());
        (address creator, uint creatorFeeRate) = _getCreator(token);
        uint256 creatorFee = _calRate(price, creatorFeeRate);
        uint256 profit = price - serviceFee - creatorFee;

        //service fee
        _wethPayFor(buyer, owner(), serviceFee);
        emit ServiceIncome(owner(), serviceFee, CURRENCY_WETH);
        //creator fee
        _wethPayFor(buyer, creator, creatorFee);
        emit CreatorIncome(creator, creatorFee, CURRENCY_WETH);
        //profit
        _wethPayFor(buyer, seller, profit);
        emit Income(seller, profit, CURRENCY_WETH);
        //expense
        emit Expense(buyer, price, CURRENCY_WETH);
    }

    function _wethPayFor(address src, address dst, uint value) internal {
        bool success = ERC20(payable(_getWethAddress())).transferFrom(src, dst, value);
        require(success, "Call of weth transferFrom return exception");
    }

    receive() external payable{

    }

}