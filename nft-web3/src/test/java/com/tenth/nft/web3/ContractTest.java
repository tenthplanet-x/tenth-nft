package com.tenth.nft.web3;

import com.tenth.nft.contract.TpulseContract;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

/**
 * @author shijie
 */
public class ContractTest {

    public static final String NETWORK = "https://rinkeby.infura.io/v3/86b933b4f2754d4cb3bb906dbe9266d4";
    public static final String CONTRACT_OWNER = "0xfffcb195b4eb04F9E9676976b16c94aa12c31af3";
    public static final String CONTRACT_OWNER_PRIVATEKEY = "caa9c4fb931a136165784140361b093063ccc05fb3e9b9bd8e7a54f199ac159d";
    public static final Long ITEM_ID = 1L;
    //mint transactionHash 0x13ccc6f2fe4a4eafc36e0f3b8e5ae3accec72647cb221eef597f5db2d73ebe0c
    public static final String CHISHIJIE_ACCOUNT = "0xcc98Cf60b156a28625Ef3669f69Abc18F8cebcdc";

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
//                new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit())
                new DefaultGasProvider()
        ).send();
        System.out.println(contract.getContractAddress());
        //0x2177891550a354dd24cebbb9ecd2c1ca8448f545
    }

    public static final String CONTRACT_ADDRESS = "0x2177891550a354dd24cebbb9ecd2c1ca8448f545";

    @Test
    public void loadContract() throws Exception{

        Web3j web3j = Web3j.build(new HttpService(NETWORK));
        Credentials credentials = Credentials.create(CONTRACT_OWNER_PRIVATEKEY);
        TpulseContract contract = TpulseContract.load(CONTRACT_ADDRESS,
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit().multiply(BigInteger.valueOf(2)))
                new DefaultGasProvider()
        );
        System.out.println(contract.getContractAddress());
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
        final Long ITEM_COUNT = 1000L;
        TransactionReceipt receipt = contract.mint("0xcc98Cf60b156a28625Ef3669f69Abc18F8cebcdc", BigInteger.valueOf(ITEM_ID), BigInteger.valueOf(ITEM_COUNT), new byte[0]).send();
        String hash = receipt.getTransactionHash();
        System.out.println(hash);
        //web3j.ethCall()
        //0x13ccc6f2fe4a4eafc36e0f3b8e5ae3accec72647cb221eef597f5db2d73ebe0c
    }

    @Test
    public void getBalance() throws Exception{
        Web3j web3j = Web3j.build(new HttpService(NETWORK));
        Credentials credentials = Credentials.create(CONTRACT_OWNER_PRIVATEKEY);
        TpulseContract contract = TpulseContract.load(CONTRACT_ADDRESS,
                web3j,
                credentials,
                new DefaultGasProvider()
        );
        BigInteger account = contract.balanceOf(CHISHIJIE_ACCOUNT, BigInteger.valueOf(ITEM_ID)).send();
        System.out.println("account: " + account);
    }

    @Test
    public void transferFrom(){
        Web3j web3j = Web3j.build(new HttpService(NETWORK));
        Credentials credentials = Credentials.create(CONTRACT_OWNER_PRIVATEKEY);
        TpulseContract contract = TpulseContract.load(CONTRACT_ADDRESS,
                web3j,
                credentials,
                new DefaultGasProvider()
        );
        //web3j.ethSendTransaction()
        //contract.safeTransferFrom().encodeFunctionCall();
        //contract.transfer
        //contract.safeTransferFrom()
    }

    @Test
    public void uri() throws Exception{

//        Web3j web3j = Web3j.build(new HttpService("https://goerli.infura.io/v3/86b933b4f2754d4cb3bb906dbe9266d4"));
//        Credentials credentials = Credentials.create("7df802b5f7181422facce5b25c0eef9f86edb1d6c0960dbedaae8cb682ce7046");
//        TpulseContract contract = TpulseContract.load(ADDRESS,
//                web3j,
//                credentials,
//                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit().multiply(BigInteger.valueOf(2)))
//                new DefaultGasProvider()
//        );
        //System.out.println(contract.uri(2).send());

    }

    @Test
    public void subscribeTransaction(){

        Web3j web3j = Web3j.build(new HttpService(NETWORK));
//        web3j.transactionFlowable().subscribe(tx -> {
//            tx.
//        });
        Credentials credentials = Credentials.create(CONTRACT_OWNER_PRIVATEKEY);
        TpulseContract contract = TpulseContract.load(CONTRACT_ADDRESS,
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit().multiply(BigInteger.valueOf(2)))
                new DefaultGasProvider()
        );



        //contract.

    }
}
