package web3;

import com.tenth.nft.app.NftApplicationTest;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.tenth.nft.solidity.WETHContract;
import com.tenth.nft.web3.service.Web3WETHService;
import com.tenth.nft.web3.vo.WETHWithDrawRequest;
import com.tenth.nft.web3.vo.WETHWithDrawResponse;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.wallan.json.JsonUtil;
import net.minidev.json.JSONUtil;
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
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;

import java.math.BigInteger;

/**
 * @author shijie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NftApplicationTest.class)
public class WETHContractTest {

    @Autowired
    private TpulseContractHelper tpulseContractHelper;
    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    private Web3WETHService web3WETHService;

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
        WETHContract contract = WETHContract.deploy(
                web3j,
                credentials,
                new StaticGasProvider(gasPrice.getGasPrice(), ethBlock.getBlock().getGasLimit())
        ).send();
        System.out.println(contract.getContractAddress());
    }

    @Test
    public void deposit() throws Exception{
        Credentials credentials = Credentials.create("7df802b5f7181422facce5b25c0eef9f86edb1d6c0960dbedaae8cb682ce7046");//Credentials.create(web3Properties.getContract().getOwnerPrivateKey());
        WETHContract wethContract = WETHContract.load(
                CONTRACT_ADDRESS,
                tpulseContractHelper.getWeb3j(),
                credentials,
                new DefaultGasProvider()
        );

        String eth = "0.001";
        String wei = Convert.toWei(eth, Convert.Unit.ETHER).toBigInteger().toString();
        String txData = wethContract.deposit().encodeFunctionCall();
        System.out.println("txValue: " + wei);
        System.out.println("txData: " + txData);
        //TransactionReceipt receipt = wethContract.deposit().encodeFunctionCall();
//        System.out.println(receipt.getTransactionHash());
    }

    @Test
    public void balanceOf() throws Exception{

        Credentials credentials = Credentials.create(web3Properties.getContract().getOwnerPrivateKey());//Credentials.create(web3Properties.getContract().getOwnerPrivateKey());
        WETHContract wethContract = WETHContract.load(
                CONTRACT_ADDRESS,
                tpulseContractHelper.getWeb3j(),
                credentials,
                new DefaultGasProvider()
        );
        Object balance = wethContract.balanceOf("0xcc98Cf60b156a28625Ef3669f69Abc18F8cebcdc").send();
        System.out.println("balance: " + balance);
    }

    public static final String CONTRACT_ADDRESS = "0xc778417E063141139Fce010982780140Aa0cD5Ab";

    @Test
    public void approve() throws Exception{

        Credentials credentials = Credentials.create("7df802b5f7181422facce5b25c0eef9f86edb1d6c0960dbedaae8cb682ce7046");//Credentials.create(web3Properties.getContract().getOwnerPrivateKey());
        WETHContract wethContract = WETHContract.load(
                CONTRACT_ADDRESS,
                tpulseContractHelper.getWeb3j(),
                credentials,
                new DefaultGasProvider()
        );
        TransactionReceipt receipt = wethContract.approve("0x2ab93084da0c395d91e551ed03278e9ec92916fe", BigInteger.valueOf(Long.MAX_VALUE)).send();
        System.out.println("txn: " + receipt.getTransactionHash());

    }

    @Test
    public void allowance() throws Exception{
        Credentials credentials = Credentials.create(web3Properties.getContract().getOwnerPrivateKey());//Credentials.create(web3Properties.getContract().getOwnerPrivateKey());
        WETHContract wethContract = WETHContract.load(
                CONTRACT_ADDRESS,
                tpulseContractHelper.getWeb3j(),
                credentials,
                new DefaultGasProvider()
        );
        BigInteger val = wethContract.allowance("0xcc98Cf60b156a28625Ef3669f69Abc18F8cebcdc", "0x2ab93084da0c395d91e551ed03278e9ec92916fe").send();
        System.out.println(val);

    }

    @Test
    public void transferFrom() throws Exception{
        Credentials credentials = Credentials.create(web3Properties.getContract().getOwnerPrivateKey());//Credentials.create(web3Properties.getContract().getOwnerPrivateKey());
        WETHContract wethContract = WETHContract.load(
                CONTRACT_ADDRESS,
                tpulseContractHelper.getWeb3j(),
                credentials,
                new DefaultGasProvider()
        );
        BigInteger weth = new BigInteger("1000000000000000");
        TransactionReceipt transactionReceipt = wethContract.transferFrom("0xcc98Cf60b156a28625Ef3669f69Abc18F8cebcdc", "0xab22314aa31e881070f3572313e88886af353DAA", weth).send();
        System.out.println(transactionReceipt.getTransactionHash());
    }

    @Test
    public void withDraw() throws Exception{

//        String csjPrivateKey = "7df802b5f7181422facce5b25c0eef9f86edb1d6c0960dbedaae8cb682ce7046";
//
//        Credentials credentials = Credentials.create(csjPrivateKey);//Credentials.create(web3Properties.getContract().getOwnerPrivateKey());
//        WETHContract wethContract = WETHContract.load(
//                CONTRACT_ADDRESS,
//                tpulseContractHelper.getWeb3j(),
//                credentials,
//                new DefaultGasProvider()
//        );

        WETHWithDrawRequest request = new WETHWithDrawRequest();
        request.setValue("0.1");
        GameUserContext.get().getBuilder().setAttr(TpulseHeaders.UID, "18000");
        WETHWithDrawResponse response = web3WETHService.createWithDraw(request);
        System.out.println(JsonUtil.toJson(response));

    }

    @Test
    public void getEvents() throws Exception{
        //0x77a24f83ca2d9b877449d887e95e0dd5941956bf

        Credentials credentials = Credentials.create(web3Properties.getContract().getOwnerPrivateKey());
        WETHContract counterContract = WETHContract.load(
                CONTRACT_ADDRESS,
                tpulseContractHelper.getWeb3j(),
                credentials,
                new DefaultGasProvider()
        );

        TransactionReceipt receipt = tpulseContractHelper.getTxn("0xce3a8d634c0d4bf9427b2e3ae33ff8fb04878b34b311b215c532ee79ecdba0e7").getReceipt();
        counterContract.getTransferEvents(receipt).stream().forEach(transferEvent -> {
            System.out.println("src: " + transferEvent.src);
            System.out.println("dst: " + transferEvent.dst);
            System.out.println("wad: " + transferEvent.wad);
        });

    }

}
