package com.tenth.nft.web3;

import org.junit.Test;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

/**
 * @author shijie
 */
public class Web3SdkTest {

    @Test
    public void getBalance() throws Exception{

        Web3j web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/86b933b4f2754d4cb3bb906dbe9266d4"));
        EthBlockNumber ethBlockNumber = web3.ethBlockNumber().send();
        EthGetBalance getBalance = web3.ethGetBalance("0xcc98Cf60b156a28625Ef3669f69Abc18F8cebcdc", DefaultBlockParameter.valueOf(ethBlockNumber.getBlockNumber())).send();
        System.out.println(getBalance.getBalance().toString());
        //200 000 000 000 000 000

    }


}
