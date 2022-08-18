package com.tenth.nft.web3;

import com.tenth.nft.contract.TpulseV2Contract;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author shijie
 */
//@EVMTest
public class MockTest {

    @Test
    public void greeterDeploys(Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider ) throws Exception {

        String seller = "0xcc98Cf60b156a28625Ef3669f69Abc18F8cebcdc";

        TpulseV2Contract contract = TpulseV2Contract.deploy(web3j, transactionManager, gasProvider, BigInteger.valueOf(25)).send();
        TpulseV2Contract.Listing listing = new TpulseV2Contract.Listing(
                seller,
                BigInteger.valueOf(1),
                BigInteger.valueOf(1),
                Convert.toWei(BigDecimal.valueOf(0.1), Convert.Unit.ETHER).toBigInteger(),
                new TpulseV2Contract.Signature(
                        new byte[32],
                        BigInteger.valueOf(1),
                        new byte[32],
                        new byte[32]
                )
        );


        contract.mint(seller, BigInteger.valueOf(1), BigInteger.valueOf(10), new byte[0]).send();
        System.out.println("owns: " + contract.balanceOf(seller, BigInteger.valueOf(1)).send());

        contract.buy(listing).send();
//        String account = web3j.ethAccounts().send().getAccounts().get(0);
//        BigInteger balance = web3j.ethGetBalance(account, DefaultBlockParameter.valueOf(web3j.ethBlockNumber().send().getBlockNumber())).send().getBalance();
//        System.out.println("account: " + account);
//        System.out.println("balance: " + balance);

//        String encodeFunction = contract.buy(listing).encodeFunctionCall();
//        transactionManager.sendTransaction(
//                new DefaultGasProvider().getGasPrice(),
//                new DefaultGasProvider().getGasLimit().multiply(BigInteger.valueOf(2)),
//                contract.getContractAddress(),
//                encodeFunction,
//                Convert.toWei(BigDecimal.valueOf(0), Convert.Unit.ETHER).toBigInteger()
//        ).getTransactionHash();
        // Sign the transaction
//        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, transactionManager);
//        String hexValue = Numeric.toHexString(signedMessage);
//        web3j.ethSendRawTransaction(hexValue).sendAsync().get();

//        System.out.println(function);
    }

}
