package com.tenth.nft.web3.service;

import com.ruixi.tpulse.convention.TpulseHeaders;
import com.tenth.nft.web3.dao.Web3WalletDao;
import com.tenth.nft.web3.dao.expression.Web3WalletQuery;
import com.tenth.nft.web3.dao.expression.Web3WalletUpdate;
import com.tenth.nft.web3.dto.EthWeb3WalletBalance;
import com.tenth.nft.web3.dto.Web3WalletBalance;
import com.tenth.nft.web3.entity.Web3Wallet;
import com.tenth.nft.web3.vo.Web3WalletBindRequest;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.utils.Convert;

import java.math.BigInteger;

/**
 * @author shijie
 */
@Service
public class Web3WalletService {

    @Autowired
    private Web3j web3j;
    @Autowired
    private Web3WalletDao walletDao;
    @Autowired
    private RouteClient routeClient;
    @Value("${web3.blockchain}")
    private String blockchain;

    public void bind(Web3WalletBindRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        walletDao.findAndModify(
                Web3WalletQuery.newBuilder().uid(uid).build(),
                Web3WalletUpdate.newBuilder()
                        .setBlockchain(blockchain)
                        .wallet(request.getWallet())
                        .setWalletAccountId(request.getAccountId())
                        .setCreatedAtOnInsert()
                        .build(),
                UpdateOptions.options().upsert(true)
        );

    }

    public Web3WalletBalance balance() throws Exception {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        Web3Wallet web3Wallet = walletDao.findOne(Web3WalletQuery.newBuilder().uid(uid).build());
        if(null != web3Wallet){
            EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
            BigInteger balance = web3j.ethGetBalance(web3Wallet.getWalletAccountId(), DefaultBlockParameter.valueOf(blockNumber.getBlockNumber())).send().getBalance();
            EthWeb3WalletBalance output = new EthWeb3WalletBalance(Convert.fromWei(balance.toString(), Convert.Unit.ETHER).toString());
            output.setWallet(web3Wallet.getWallet());
            return output;
        }

        return null;

    }


}
