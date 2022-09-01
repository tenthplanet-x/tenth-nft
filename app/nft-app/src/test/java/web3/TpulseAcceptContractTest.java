package web3;

import com.tenth.nft.app.NftApplicationTest;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.solidity.TpulseContract;
import com.tenth.nft.solidity.TpulseContractHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NftApplicationTest.class)
public class TpulseAcceptContractTest {

    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    private TpulseContractHelper tpulseContractHelper;

    @Test
    public void deploy() throws Exception{

        Web3j web3j = Web3j.build(new HttpService(web3Properties.getNetwork()));
        EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
        EthBlock ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber.getBlockNumber()), false).send();
        System.out.println("gasLimit: " + ethBlock.getBlock().getGasLimit());
        //30000000
        String owner = web3Properties.getContract().getOwnerAddress();
        String ownerPrivateKey = web3Properties.getContract().getOwnerPrivateKey();
        EthGetBalance ethGetBalance = web3j.ethGetBalance(owner, DefaultBlockParameter.valueOf(blockNumber.getBlockNumber())).send();
        System.out.println(ethGetBalance.getBalance());
        //300000000000000000
        EthGasPrice gasPrice = web3j.ethGasPrice().send();
        System.out.println("gasPrice: " + gasPrice.getGasPrice());
        Credentials credentials = Credentials.create(ownerPrivateKey);
        System.out.println("gasLimit: " + ethBlock.getBlock().getGasLimit());
        TpulseContract contract = TpulseContract.deploy(
                web3j,
                credentials,
                //new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit()),
                new DefaultGasProvider(),
                "",
                BigInteger.valueOf(2500),
                web3Properties.getWethAddress()
        ).send();
        System.out.println(contract.getContractAddress());
    }

    //0xb34b81609625ff36b98cf6606d9bdf88c90940b3

    public static final String COUNTER_CONTRACT_ADDRESS = "0x2ab93084da0c395d91e551ed03278e9ec92916fe";

    @Test
    public void wethPayFor() throws Exception{

        Credentials credentials = Credentials.create(web3Properties.getContract().getOwnerPrivateKey());
        TpulseContract tpulseAcceptContract = TpulseContract.load(
                COUNTER_CONTRACT_ADDRESS,
                tpulseContractHelper.getWeb3j(),
                credentials,
                new DefaultGasProvider()
        );

        //2000000000000000

        //String weth = "9223372036854775807"；
        //9 223 372 036 854 775 807
        String weth = "1000000000000000";
        //String wei = Convert.toWei(eth, Convert.Unit.ETHER).toBigInteger().toString();
//        TransactionReceipt receipt = tpulseAcceptContract.wethPayFor("0xcc98Cf60b156a28625Ef3669f69Abc18F8cebcdc", "0xab22314aa31e881070f3572313e88886af353DAA", new BigInteger(weth)).send();
//        System.out.println("txn:" + receipt.getTransactionHash());

//        counterContract.wethPayFor();

//        TransactionReceipt receipt = counterContract.inc().send();
//        System.out.println("counter inc: " + receipt.getTransactionHash());
    }

    @Test
    public void getEvents() throws Exception{
        //0x77a24f83ca2d9b877449d887e95e0dd5941956bf

//        Credentials credentials = Credentials.create(web3Properties.getContract().getOwnerPrivateKey());
//        CounterAgent counterContract = CounterAgent.load(
//                COUNTER_CONTRACT_ADDRESS,
//                tpulseContractHelper.getWeb3j(),
//                credentials,
//                new DefaultGasProvider()
//        );
    }


}
