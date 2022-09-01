//contracts/TpulseBuy.sol
//SPDX-License-Identifier: MIT
//Version: 0.0.1
pragma solidity ^0.8.15;

import "./TpulseBase.sol";

abstract contract TpulseBuy is TpulseBase{

    function _buy(
        address seller,
        uint token,
        uint quantity
    ) public payable{

        //TODO Verify seller signature

        address buyer = _msgSender();
        uint price = msg.value;

        //transfer assets
        require(isApprovedForAll(seller, address(this)), "[buy]seller is not approved");
        _safeTransferFrom(seller, buyer, token, quantity, "");

        //payment split
        uint256 serviceFee = _calRate(price, _getServiceFeeRate());
        (address creator, uint creatorFeeRate) = _getCreator(token);
        uint256 creatorFee = _calRate(price, creatorFeeRate);
        uint256 profit = price - serviceFee - creatorFee;

        //profit
        payable(seller).transfer(profit);
        emit Income(seller, profit, CURRENCY_ETH);
        //service fee
        payable(owner()).transfer(serviceFee);
        emit ServiceIncome(owner(), serviceFee, CURRENCY_ETH);
        //creator fee
        payable(creator).transfer(creatorFee);
        emit CreatorIncome(creator, creatorFee, CURRENCY_ETH);
        //expense
        emit Expense(buyer, price, CURRENCY_ETH);
    }

}