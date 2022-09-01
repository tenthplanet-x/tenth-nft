package com.tenth.nft.marketplace.service;

import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.routes.marketplace.AssetsMintRouteRequest;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.convention.web3.utils.TokenMintStatus;
import com.tenth.nft.marketplace.vo.NftAssetsMintCheckRequest;
import com.tenth.nft.marketplace.vo.NftAssetsMintCheckResponse;
import com.tenth.nft.orm.marketplace.dao.NftAssetsDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsUpdate;
import com.tenth.nft.orm.marketplace.entity.NftAssets;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tenth.nft.solidity.ContractTransactionReceipt;
import com.tenth.nft.solidity.TpulseContract;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.tpulse.gs.convention.dao.SimpleQuery;
import com.tpulse.gs.router.client.RouteClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author shijie
 */
@Service
public class NftAssetsMintService {

    private Logger LOGGER = LoggerFactory.getLogger(NftAssetsMintService.class);

    @Autowired
    private NftAssetsDao nftAssetsDao;
    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    private TpulseContractHelper tpulseContractHelper;
    @Autowired
    private RouteClient routeClient;

    public NftAssetsMintCheckResponse checkMinting(NftAssetsMintCheckRequest request) {

        SimpleQuery query = NftAssetsQuery.newBuilder().id(request.getAssetsId()).build();
        NftAssets nftAssets = nftAssetsDao.findOne(query);
        TokenMintStatus mintStatus = nftAssets.getMintStatus();
        if(null == mintStatus){
            if(web3Properties.getBlockchain().equals(nftAssets.getBlockchain())){

                routeClient.send(
                        NftMarketplace.ASSETS_MINT_IC.newBuilder().setId(nftAssets.getId()).build(),
                        AssetsMintRouteRequest.class
                );
                nftAssetsDao.update(
                        query,
                        NftAssetsUpdate.newBuilder()
                                .mintStatus(TokenMintStatus.MINTING)
                                .build()
                );
                return new NftAssetsMintCheckResponse(TokenMintStatus.MINTING);
            }
        }

        return new NftAssetsMintCheckResponse(nftAssets.getMintStatus());

    }

    public void mint(NftMarketplace.ASSETS_MINT_IC request){

        SimpleQuery query = NftAssetsQuery.newBuilder().id(request.getId()).build();
        NftAssets nftAssets = nftAssetsDao.findOne(query);
        //nftAssets.getCreator()

        String creatorAddress = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(nftAssets.getCreator())
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();
        TpulseContract tpulseContract = tpulseContractHelper.getTpulseContract();
        try{
            TransactionReceipt receipt = tpulseContract.mintWithCreatorFeeRate(
                    creatorAddress,
                    BigInteger.valueOf(nftAssets.getId()),
                    BigInteger.valueOf(nftAssets.getSupply()),
                    //Use rate decimals 4
                    new BigDecimal(nftAssets.getCreatorFeeRate()).multiply(BigDecimal.valueOf(100)).toBigInteger()
            ).send();


            if(receipt.isStatusOK()){
                nftAssetsDao.update(
                        query,
                        NftAssetsUpdate.newBuilder()
                                .mintStatus(TokenMintStatus.SUCCESS)
                                .build()
                );
            }else if(ContractTransactionReceipt.isFail(receipt.getStatus())){
                nftAssetsDao.update(
                        query,
                        NftAssetsUpdate.newBuilder()
                                .mintStatus(TokenMintStatus.FAIL)
                                .build()
                );
            }
        }catch (Exception e){
            LOGGER.error("", e);
            nftAssetsDao.update(
                    query,
                    NftAssetsUpdate.newBuilder()
                            .mintStatus(TokenMintStatus.FAIL)
                            .build()
            );
        }

    }
}
