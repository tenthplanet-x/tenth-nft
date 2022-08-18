package com.tenth.nft.web3;

import com.tenth.nft.contract.TpulseV2Contract;
import com.wallan.json.JsonUtil;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shijie
 */
public class TpulseV2ContractTest {

    public static final String NETWORK = "https://rinkeby.infura.io/v3/86b933b4f2754d4cb3bb906dbe9266d4";
    public static final String CONTRACT_OWNER = "0xfffcb195b4eb04F9E9676976b16c94aa12c31af3";
    public static final String CONTRACT_OWNER_PRIVATEKEY = "caa9c4fb931a136165784140361b093063ccc05fb3e9b9bd8e7a54f199ac159d";
    public static final Long ITEM_ID = 2L;
    public static final String CONTRACT_BUYER = "0xcc98Cf60b156a28625Ef3669f69Abc18F8cebcdc";
    public static final String CONTRACT_BUYER_PRIVATEKEY = "7df802b5f7181422facce5b25c0eef9f86edb1d6c0960dbedaae8cb682ce7046";
    public static final String CONTRACT_SELLER = "0xab22314aa31e881070f3572313e88886af353DAA";
    public static final String CONTRACT_SELLER_PRIVATEKEY = "fe0c03b7ad9d97e7bf9f684ffdbc9561f3e1dfa36afe979c2aa33afc536f072a";

    @Test
    public void deploy() throws Exception{
        Web3j web3j = Web3j.build(new HttpService(NETWORK));
        EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
        EthBlock ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber.getBlockNumber()), false).send();
        System.out.println("gasLimit: " + ethBlock.getBlock().getGasLimit());
        //30000000
        EthGetBalance ethGetBalance = web3j.ethGetBalance(CONTRACT_OWNER, DefaultBlockParameter.valueOf(blockNumber.getBlockNumber())).send();
        System.out.println(ethGetBalance.getBalance());
        //300000000000000000
        EthGasPrice gasPrice = web3j.ethGasPrice().send();
        System.out.println("gasPrice: " + gasPrice.getGasPrice());
        Credentials credentials = Credentials.create(CONTRACT_OWNER_PRIVATEKEY);
        System.out.println("gasLimit: " + ethBlock.getBlock().getGasLimit());
        TpulseV2Contract contract = TpulseV2Contract.deploy(
                web3j,
                credentials,
                new DefaultGasProvider(),
                BigInteger.valueOf(25)
        ).send();
        System.out.println(contract.getContractAddress());
        //0x34c8e08651025f7e2194b1e9d365fc9c750d19ca
    }

    public static final String CONTRACT_ADDRESS = "0x55ad059d00cb76f396720cadb9e915bc68271d5a";

    @Test
    public void mint() throws Exception{
        Web3j web3j = Web3j.build(new HttpService(NETWORK));
        Credentials credentials = Credentials.create(CONTRACT_OWNER_PRIVATEKEY);
        TpulseV2Contract contract = TpulseV2Contract.load(CONTRACT_ADDRESS,
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit().multiply(BigInteger.valueOf(2)))
                new DefaultGasProvider()
        );
        final Long ITEM_COUNT = 10L;
        TransactionReceipt receipt = contract.mint(CONTRACT_SELLER, BigInteger.valueOf(ITEM_ID), BigInteger.valueOf(ITEM_COUNT), new byte[0]).send();
        String hash = receipt.getTransactionHash();
        System.out.println("mint transaction: " + hash);

        BigInteger itemOwns = contract.balanceOf(CONTRACT_SELLER, BigInteger.valueOf(ITEM_ID)).send();
        System.out.println("item owns: " + itemOwns);

        EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
        BigInteger balance = web3j.ethGetBalance(CONTRACT_SELLER, DefaultBlockParameter.valueOf(blockNumber.getBlockNumber())).send().getBalance();
        System.out.println("current eth: " + balance);
        //763063165127125783

    }

    @Test
    public void balanceOf() throws Exception{

        //0xcc98Cf60b156a28625Ef3669f69Abc18F8cebcdc
        Web3j web3j = Web3j.build(new HttpService(NETWORK));
        Credentials credentials = Credentials.create(CONTRACT_OWNER_PRIVATEKEY);
        TpulseV2Contract contract = TpulseV2Contract.load(CONTRACT_ADDRESS,
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit().multiply(BigInteger.valueOf(2)))
                new DefaultGasProvider()
        );
        BigInteger itemOwns = contract.balanceOf(CONTRACT_BUYER, BigInteger.valueOf(ITEM_ID)).send();
        System.out.println("item owns: " + itemOwns);

    }

    @Test
    public void approve() throws Exception{
        Web3j web3j = Web3j.build(new HttpService(NETWORK));
        Credentials credentials = Credentials.create(CONTRACT_SELLER_PRIVATEKEY);
        TpulseV2Contract contract = TpulseV2Contract.load(CONTRACT_ADDRESS,
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit().multiply(BigInteger.valueOf(2)))
                new DefaultGasProvider()
        );
        contract.setApprovalForAll(CONTRACT_OWNER, true).send();

        boolean isApprove = contract.isApprovedForAll(CONTRACT_SELLER, CONTRACT_OWNER).send();
        System.out.println("isApprove: " + isApprove);
    }

    @Test
    public void buy() throws Exception{

        Web3j web3j = Web3j.build(new HttpService(NETWORK));
        Credentials credentials = Credentials.create(CONTRACT_BUYER_PRIVATEKEY);
        TpulseV2Contract contract = TpulseV2Contract.load(
                CONTRACT_ADDRESS,
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit().multiply(BigInteger.valueOf(2)))
                new DefaultGasProvider()
        );

        EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
        BigInteger balance = web3j.ethGetBalance(CONTRACT_BUYER, DefaultBlockParameter.valueOf(blockNumber.getBlockNumber())).send().getBalance();
        System.out.println("current eth: " + balance);

        TpulseV2Contract.Listing listing = new TpulseV2Contract.Listing(
                CONTRACT_SELLER,
                BigInteger.valueOf(ITEM_ID),
                BigInteger.valueOf(1),
                Convert.toWei(BigDecimal.valueOf(0.1), Convert.Unit.ETHER).toBigInteger(),
                new TpulseV2Contract.Signature(
                        new byte[32],
                        BigInteger.valueOf(1),
                        new byte[32],
                        new byte[32]
                )
        );
        String hash = contract.buy(listing).send().getTransactionHash();
        System.out.println("hash: " + hash);
        //0xd05cbdc69bd768c824d308efbdb6c325f34861d7ea2f5dbc857a1929af4d1299
    }

    @Test
    public void buyWithMoney() throws Exception{
        Web3j web3j = Web3j.build(new HttpService(NETWORK));
        Credentials credentials = Credentials.create(CONTRACT_OWNER_PRIVATEKEY);
        TpulseV2Contract contract = TpulseV2Contract.load(
                CONTRACT_ADDRESS,
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit().multiply(BigInteger.valueOf(2)))
                new DefaultGasProvider()
        );

        EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
        BigInteger balance = web3j.ethGetBalance(CONTRACT_BUYER, DefaultBlockParameter.valueOf(blockNumber.getBlockNumber())).send().getBalance();
        System.out.println("current eth: " + balance);

        TpulseV2Contract.Listing listing = new TpulseV2Contract.Listing(
                CONTRACT_SELLER,
                BigInteger.valueOf(ITEM_ID),
                BigInteger.valueOf(1),
                BigInteger.valueOf(0),
                new TpulseV2Contract.Signature(
                        new byte[32],
                        BigInteger.valueOf(1),
                        new byte[32],
                        new byte[32]
                )
        );


        //String encodeFunction = contract.buy(listing).encodeFunctionCall();
        Function function = new Function(
            "buy",  // function we're calling
            Arrays.asList(listing),  // Parameters to pass as Solidity Types
            Arrays.asList());
        String encodedFunction = FunctionEncoder.encode(function);
//        RawTransaction rawTransaction = RawTransaction.createTransaction(
//                BigInteger.valueOf(System.currentTimeMillis()),
//                new DefaultGasProvider().getGasPrice(),
//                new DefaultGasProvider().getGasLimit().multiply(BigInteger.valueOf(2)),
//                CONTRACT_ADDRESS,
//                Convert.toWei(BigDecimal.valueOf(0.1), Convert.Unit.ETHER).toBigInteger(),
//                encodeFunction
//        );
        //TransactionEncoder.encode();

        Map<String, Object> transactionRequest = new HashMap<>();
        transactionRequest.put("data", encodedFunction);
        transactionRequest.put("value", Convert.toWei("0.001", Convert.Unit.ETHER).toBigInteger().toString());
        transactionRequest.put("to", CONTRACT_ADDRESS);

        System.out.println(JsonUtil.toJson(transactionRequest));


//        RawTransaction rawTransaction = RawTransaction.createTransaction(
//                BigInteger.valueOf(System.currentTimeMillis()),
//                new DefaultGasProvider().getGasPrice(),
//                new DefaultGasProvider().getGasLimit().multiply(BigInteger.valueOf(2)),
//                CONTRACT_ADDRESS,
//                Convert.toWei(BigDecimal.valueOf(0.1), Convert.Unit.ETHER).toBigInteger(),
//                encodeFunction
//        );
//        // Sign the transaction
//        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
//        String hexValue = Numeric.toHexString(signedMessage);
//        web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        //0x1f4c5641aafd1ce0881c4ae4e00e84382e185429fbcb932557a6997a47e43c28

//        Function function = new Function(
//                "buy",  // function we're calling
//                Arrays.asList(listing),  // Parameters to pass as Solidity Types
//                Arrays.asList());
//        String encodedFunction = FunctionEncoder.encode(function);
//        RawTransaction transaction = new RawTransaction(
//                CONTRACT_BUYER,
//                BigInteger.valueOf(System.currentTimeMillis()),
//                new DefaultGasProvider().getGasPrice(),
//                new DefaultGasProvider().getGasLimit(),
//                CONTRACT_ADDRESS,
//                Convert.toWei(BigDecimal.valueOf(0.1), Convert.Unit.ETHER).toBigInteger(),
//                encodedFunction
//        );
//
//        byte[] signedMessage = TransactionEncoder.signMessage(transaction, credentials);
//
//        org.web3j.protocol.core.methods.response.EthSendTransaction transactionResponse =
//                web3j.ethSendRawTransaction(transaction).sendAsync().get();
//        String transactionHash = transactionResponse.getTransactionHash();
//        System.out.println(transactionHash);

// wait for response using EthGetTransactionReceipt...
    }

    @Test
    public void unitTest(){
        System.out.println(BigDecimal.valueOf(0.3).multiply(BigDecimal.valueOf(10).pow(18)).toBigInteger());
        //300 000 000 000 000 000
    }

    @Test
    public void own() throws Exception{

        String owner = CONTRACT_BUYER;

        Web3j web3j = Web3j.build(new HttpService(NETWORK));
        Credentials credentials = Credentials.create(CONTRACT_OWNER_PRIVATEKEY);
        TpulseV2Contract contract = TpulseV2Contract.load(
                CONTRACT_ADDRESS,
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit().multiply(BigInteger.valueOf(2)))
                new DefaultGasProvider()
        );
        BigInteger owns = contract.balanceOf(owner, BigInteger.valueOf(ITEM_ID)).send();
        System.out.println(owns);

    }
}
