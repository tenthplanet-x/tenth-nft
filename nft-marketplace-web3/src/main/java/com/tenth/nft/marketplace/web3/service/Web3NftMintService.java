package com.tenth.nft.marketplace.web3.service;

import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.routes.marketplace.AssetsMintRouteRequest;
import com.tenth.nft.convention.web3.utils.TokenMintStatus;
import com.tenth.nft.marketplace.web3.dto.NftAssetsMintCheckResponse;
import com.tenth.nft.marketplace.web3.entity.Web3NftAssets;
import com.tenth.nft.marketplace.web3.vo.NftAssetsMintCheckRequest;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.solidity.ContractTransactionReceipt;
import com.tenth.nft.solidity.TpulseContract;
import com.tenth.nft.solidity.TpulseContractHelper;
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
public class Web3NftMintService {

    private Logger LOGGER = LoggerFactory.getLogger(Web3NftMintService.class);

    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    private TpulseContractHelper tpulseContractHelper;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private Web3NftAssetsService nftAssetsService;

    /**
     * Trigger minting using async mode if it haven't minted
     * @param request
     * @return
     */
    public NftAssetsMintCheckResponse checkMinting(NftAssetsMintCheckRequest request) {

        Web3NftAssets nftAssets = nftAssetsService.findById(request.getAssetsId());
        TokenMintStatus mintStatus = nftAssets.getMintStatus();
        if(null == mintStatus){
            if(web3Properties.getBlockchain().equals(nftAssets.getBlockchain())){
                routeClient.send(
                        NftMarketplace.ASSETS_MINT_IC.newBuilder().setId(nftAssets.getId()).build(),
                        AssetsMintRouteRequest.class
                );
                nftAssetsService.updateMintStatus(request.getAssetsId(), TokenMintStatus.MINTING);
                return new NftAssetsMintCheckResponse(TokenMintStatus.MINTING);
            }
        }

        return new NftAssetsMintCheckResponse(nftAssets.getMintStatus());

    }

    public void mint(NftMarketplace.ASSETS_MINT_IC request){

        Web3NftAssets nftAssets = nftAssetsService.findById(request.getId());
        String creatorAddress = nftAssets.getCreator();
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
                nftAssetsService.updateMintStatus(request.getId(), TokenMintStatus.SUCCESS);
            }else if(ContractTransactionReceipt.isFail(receipt.getStatus())){
                nftAssetsService.updateMintStatus(request.getId(), TokenMintStatus.FAIL);
            }
        }catch (Exception e){
            LOGGER.error("", e);
            nftAssetsService.updateMintStatus(request.getId(), TokenMintStatus.FAIL);
        }

    }
}
