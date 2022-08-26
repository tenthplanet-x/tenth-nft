package com.tenth.nft.web3;

import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.solidity.ContractTransactionReceipt;
import com.tenth.nft.solidity.TpulseContract;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.wallan.json.JsonUtil;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shijie
 */
public class TpulseContractTest {

    public static final String NETWORK = "https://rinkeby.infura.io/v3/86b933b4f2754d4cb3bb906dbe9266d4";
    public static final String CONTRACT_OWNER = "0xfffcb195b4eb04F9E9676976b16c94aa12c31af3";
    public static final String CONTRACT_OWNER_PRIVATEKEY = "caa9c4fb931a136165784140361b093063ccc05fb3e9b9bd8e7a54f199ac159d";
    public static final Long ITEM_ID = 1l;
    public static final String CONTRACT_BUYER = "0xab22314aa31e881070f3572313e88886af353DAA";
    public static final String CONTRACT_BUYER_PRIVATEKEY = "fe0c03b7ad9d97e7bf9f684ffdbc9561f3e1dfa36afe979c2aa33afc536f072a";
    public static final String CONTRACT_SELLER = "0xcc98Cf60b156a28625Ef3669f69Abc18F8cebcdc";
    public static final String CONTRACT_SELLER_PRIVATEKEY = "7df802b5f7181422facce5b25c0eef9f86edb1d6c0960dbedaae8cb682ce7046";

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
        TpulseContract contract = TpulseContract.deploy(
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit()),
                new DefaultGasProvider(),
                "https://nft.ruixi-sh.com/token/{id}",
                BigInteger.valueOf(2500),
                BigInteger.valueOf(4)
        ).send();
        System.out.println(contract.getContractAddress());
        //0x34c8e08651025f7e2194b1e9d365fc9c750d19ca
    }

    public static final String CONTRACT_ADDRESS = "0xac9d1c6d8c333eaac32ca8c2c25be51a338cde44";

    @Test
    public void load() throws Exception{
        Web3Properties web3Properties = new Web3Properties();
        web3Properties.setChainId(4);
        web3Properties.setNetwork("https://rinkeby.infura.io/v3/86b933b4f2754d4cb3bb906dbe9266d4");
        web3Properties.setBlockchain("Ethereum");
        Web3Properties.Contract contract = new Web3Properties.Contract();
        contract.setAddress(CONTRACT_ADDRESS);
        contract.setOwnerAddress("0xfffcb195b4eb04F9E9676976b16c94aa12c31af3");
        contract.setOwnerPrivateKey("caa9c4fb931a136165784140361b093063ccc05fb3e9b9bd8e7a54f199ac159d");
        web3Properties.setContract(contract);
        TpulseContractHelper helper = new TpulseContractHelper(web3Properties);
        System.out.println();
    }

    @Test
    public void mint() throws Exception{
        Web3j web3j = Web3j.build(new HttpService(NETWORK));
        Credentials credentials = Credentials.create(CONTRACT_OWNER_PRIVATEKEY);
        TpulseContract contract = TpulseContract.load(CONTRACT_ADDRESS,
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit().multiply(BigInteger.valueOf(2)))
                new DefaultGasProvider()
        );
        final Long ITEM_COUNT = 1000l;
        TransactionReceipt receipt = contract.mintWithCreatorFeeRate(CONTRACT_SELLER, BigInteger.valueOf(ITEM_ID), BigInteger.valueOf(ITEM_COUNT), BigInteger.valueOf(2500)).send();
        String hash = receipt.getTransactionHash();
        System.out.println("mint transaction: " + hash);

        BigInteger itemOwns = contract.balanceOf(CONTRACT_SELLER, BigInteger.valueOf(ITEM_ID)).send();
        System.out.println("item owns: " + itemOwns);

        Tuple2<String, BigInteger> profile = contract.creatorProfileOf(BigInteger.valueOf(ITEM_ID)).send();
        System.out.println("creator: " + profile.component1());
        System.out.println("creatorFeeRate: " + profile.component2());

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
        TpulseContract contract = TpulseContract.load(CONTRACT_ADDRESS,
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit().multiply(BigInteger.valueOf(2)))
                new DefaultGasProvider()
        );
        BigInteger itemOwns = contract.balanceOf(CONTRACT_SELLER, BigInteger.valueOf(ITEM_ID)).send();
        System.out.println("item owns: " + itemOwns);

    }

    @Test
    public void approve() throws Exception{
        Web3j web3j = Web3j.build(new HttpService(NETWORK));
        Credentials credentials = Credentials.create(CONTRACT_SELLER_PRIVATEKEY);
        TpulseContract contract = TpulseContract.load(CONTRACT_ADDRESS,
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
        TpulseContract contract = TpulseContract.load(
                CONTRACT_ADDRESS,
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit().multiply(BigInteger.valueOf(2)))
                new DefaultGasProvider()
        );

        EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
        BigInteger balance = web3j.ethGetBalance(CONTRACT_BUYER, DefaultBlockParameter.valueOf(blockNumber.getBlockNumber())).send().getBalance();
        System.out.println("current eth: " + balance);

        TpulseContract.Listing listing = new TpulseContract.Listing(
                CONTRACT_SELLER,
                BigInteger.valueOf(ITEM_ID),
                BigInteger.valueOf(1),
                BigInteger.valueOf(1)
        );
        TpulseContract.Signature signature = new TpulseContract.Signature(
                new byte[32],
                BigInteger.valueOf(1),
                new byte[32],
                new byte[32]
        );
        String hash = contract.buy(listing, signature).send().getTransactionHash();
        System.out.println("hash: " + hash);
        //0xd05cbdc69bd768c824d308efbdb6c325f34861d7ea2f5dbc857a1929af4d1299
        BigInteger itemOwns = contract.balanceOf(CONTRACT_BUYER, BigInteger.valueOf(ITEM_ID)).send();
        System.out.println("item owns: " + itemOwns);
    }

    @Test
    public void buyResult() throws Exception{

        Web3Properties web3Properties = new Web3Properties();
        web3Properties.setChainId(4);
        web3Properties.setNetwork("https://rinkeby.infura.io/v3/86b933b4f2754d4cb3bb906dbe9266d4");
        web3Properties.setBlockchain("Ethereum");
        Web3Properties.Contract contract = new Web3Properties.Contract();
        contract.setAddress("0x0b3df5a5ece71b7ec023b499d4f76524fbc8b73b");
        contract.setOwnerAddress("0xfffcb195b4eb04F9E9676976b16c94aa12c31af3");
        contract.setOwnerPrivateKey("caa9c4fb931a136165784140361b093063ccc05fb3e9b9bd8e7a54f199ac159d");
        web3Properties.setContract(contract);
        TpulseContractHelper helper = new TpulseContractHelper(web3Properties);
        ContractTransactionReceipt receipt = helper.getTxn("0x439e9c0eb41847a8ff4624e1ccb9b7d19058f1eef61154aa2e378e0284778ca5");
        System.out.println("usedGasValue: " + receipt.getUsedGasValue());
        System.out.println("isSuccess: " + receipt.isSuccess());
        System.out.println("isFail: " + receipt.isFail());
        //
    }

    @Test
    public void buyWithMoney() throws Exception{
        Web3j web3j = Web3j.build(new HttpService(NETWORK));
        Credentials credentials = Credentials.create(CONTRACT_OWNER_PRIVATEKEY);
        TpulseContract contract = TpulseContract.load(
                CONTRACT_ADDRESS,
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit().multiply(BigInteger.valueOf(2)))
                new DefaultGasProvider()
        );

        EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
        BigInteger balance = web3j.ethGetBalance(CONTRACT_BUYER, DefaultBlockParameter.valueOf(blockNumber.getBlockNumber())).send().getBalance();
        System.out.println("current eth: " + balance);

        TpulseContract.Listing listing = new TpulseContract.Listing(
                CONTRACT_SELLER,
                BigInteger.valueOf(ITEM_ID),
                BigInteger.valueOf(1),
                BigInteger.valueOf(1)
        );
        TpulseContract.Signature signature = new TpulseContract.Signature(
                new byte[32],
                BigInteger.valueOf(1),
                new byte[32],
                new byte[32]
        );

        //String encodeFunction = contract.buy(listing).encodeFunctionCall();
        Function function = new Function(
            "buy",  // function we're calling
            Arrays.asList(listing, signature),  // Parameters to pass as Solidity Types
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
        TpulseContract contract = TpulseContract.load(
                CONTRACT_ADDRESS,
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit().multiply(BigInteger.valueOf(2)))
                new DefaultGasProvider()
        );
        BigInteger owns = contract.balanceOf(owner, BigInteger.valueOf(ITEM_ID)).send();
        System.out.println(owns);

    }

    @Test
    public void receipt() throws Exception{

        Web3j web3j = Web3j.build(new HttpService(NETWORK));
        Credentials credentials = Credentials.create(CONTRACT_BUYER_PRIVATEKEY);
        TpulseContract contract = TpulseContract.load(
                CONTRACT_ADDRESS,
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit().multiply(BigInteger.valueOf(2)))
                new DefaultGasProvider()
        );
        //BigInteger integer = contract.getGasPrice();
        //System.out.println(integer);

        String txn = "0x77021fcf0e65a674f868ee2322393230310f27231b22ac4de33ca2d5d8cad640";

        BigInteger gasPrice = web3j.ethGetTransactionByHash(txn).send().getResult().getGasPrice();
        System.out.println(gasPrice);

        EthGetTransactionReceipt receipt = web3j.ethGetTransactionReceipt(txn).send();
        System.out.println(receipt);
        BigInteger bigInteger = receipt.getResult().getGasUsed();
        BigInteger usedGasValue = bigInteger.multiply(gasPrice);
        List<Log> logs = receipt.getResult().getLogs();
        for(Log log: logs){
            System.out.println(log.getType());
        }

//        List<TpulseContract.ReceivedEventResponse> responses = contract.getReceivedEvents(receipt.getResult());
//        System.out.println(responses);

        List<TpulseContract.ExpenseEventResponse> events = contract.getExpenseEvents(receipt.getResult());
        System.out.println(events);

        List<TpulseContract.IncomeEventResponse> incomeEvents = contract.getIncomeEvents(receipt.getResult());
        System.out.println(incomeEvents);

        List<TpulseContract.CreatorIncomeEventResponse> creatorEvent = contract.getCreatorIncomeEvents(receipt.getResult());
        System.out.println(creatorEvent);
    }

    @Test
    public void convert() throws Exception{
        //0x26b19d74b847ccd35309ae583f914d3e355ab5961ca30b20c9850c299a607055
        //0x9ed37f
        //0x138d2
//        BigInteger val = new BigInteger("138d2", 16);
//        System.out.println(val);

        BigInteger gasUsed = new BigInteger("138d2", 16);
        System.out.println(gasUsed);
        //Long val = Long.valueOf("0x138d2", 16);

        //80082
        //10408831

        BigDecimal gasPrice = Convert.toWei("0.000000001881796271", Convert.Unit.ETHER);
        String price = gasPrice.multiply(new BigDecimal(gasUsed)).toString();
        String ether = Convert.fromWei(price, Convert.Unit.ETHER).toString();
        System.out.println(ether);
//        BigDecimal decimal = new BigDecimal("0.000000001881796271");
//        decimal.multiply(val2);
    }
}
