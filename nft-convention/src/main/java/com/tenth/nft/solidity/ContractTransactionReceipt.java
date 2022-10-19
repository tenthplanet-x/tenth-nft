package com.tenth.nft.solidity;

import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author shijie
 */
public class ContractTransactionReceipt {

    private static final BigInteger SUCCESS = new BigInteger("1", 16);
    private static final BigInteger FAIL = new BigInteger("0", 16);

    private Transaction transaction;
    private TransactionReceipt receipt;

    public ContractTransactionReceipt(Transaction transaction, TransactionReceipt receipt) {
        this.transaction = transaction;
        this.receipt = receipt;
    }


    public boolean isFail() {
        return null != receipt && FAIL.equals(new BigInteger(receipt.getStatus().substring(2), 16));
    }

    public boolean isSuccess() {
        return null != receipt && SUCCESS.equals(new BigInteger(receipt.getStatus().substring(2), 16));
    }

    public String getUsedGasValue() {
        BigInteger usedGasValue = transaction.getGasPrice().multiply(receipt.getGasUsed());
        return Convert.fromWei(new BigDecimal(usedGasValue), Convert.Unit.ETHER).toString();
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public TransactionReceipt getReceipt() {
        return receipt;
    }

    public static boolean isFail(String status) {
        return FAIL.equals(new BigInteger(status.substring(2), 16));
    }
}
