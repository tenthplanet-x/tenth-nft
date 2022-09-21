package com.tenth.nft.marketplace.web3.service;

import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.convention.utils.Precisions;
import com.tenth.nft.convention.web3.sign.MintDataForSign;
import com.tenth.nft.convention.web3.sign.StructContentHash;
import com.tenth.nft.convention.web3.utils.TokenMintStatus;
import com.tenth.nft.convention.web3.utils.WalletBridgeUrl;
import com.tenth.nft.marketplace.common.dto.NftAssetsDTO;
import com.tenth.nft.marketplace.common.service.AbsNftAssetsService;
import com.tenth.nft.marketplace.common.vo.NftAssetsCreateRequest;
import com.tenth.nft.marketplace.web3.dao.Web3NftAssetsDao;
import com.tenth.nft.marketplace.web3.dto.CreateWeb3NftSignRequest;
import com.tenth.nft.marketplace.web3.entity.Web3NftAssets;
import com.tenth.nft.marketplace.web3.entity.Web3NftCollection;
import com.tenth.nft.marketplace.web3.vo.NftAssetsCreateConfirmRequest;
import com.tenth.nft.marketplace.web3.vo.Web3NftAssetsCreateRequest;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.tpulse.gs.convention.cypher.rsa.RSAUtils;
import com.tpulse.gs.convention.cypher.utils.Base64Utils;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.json.JsonUtil;
import com.wallan.router.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author shijie
 */
@Service
public class Web3NftAssetsService extends AbsNftAssetsService<Web3NftAssets> {

    @Autowired
    private RouteClient routeClient;
    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    private TpulseContractHelper tpulseContractHelper;

    private Web3NftCollectionService nftCollectionService;

    public Web3NftAssetsService(
            Web3NftAssetsDao nftAssetsDao,
            Web3NftCollectionService nftCollectionService,
            Web3NftBelongService nftBelongService,
            Web3NftUbtLogService nftUbtLogService) {
        super(nftAssetsDao, nftCollectionService, nftBelongService, nftUbtLogService);
        this.nftCollectionService = nftCollectionService;
    }

    @Override
    protected Web3NftAssets newNftAssets() {
        return null;
    }

    @Override
    protected void afterCreate(Web3NftAssets nftAssets) {

    }

    public CreateWeb3NftSignRequest create(NftAssetsCreateRequest request) throws Exception {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        String creator = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(uid)
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();

        Web3NftCollection collection = nftCollectionService.findById(request.getCollectionId());
        if(null == collection || !collection.getCreator().equals(creator)){
            throw BizException.newInstance(NftExchangeErrorCodes.MINT_EXCEPTION_INVALID_PARAMS);
        }

        if(!request.getBlockchain().equals(web3Properties.getBlockchain())){
            throw BizException.newInstance(NftExchangeErrorCodes.MINT_EXCEPTION_INVALID_PARAMS);
        }

        MintDataForSign mintDataForSign = new MintDataForSign();
        mintDataForSign.setDomain(tpulseContractHelper.getDomain());
        mintDataForSign.setName(request.getName());
        mintDataForSign.setDesc(request.getDesc());
        mintDataForSign.setSupply(request.getSupply());
        mintDataForSign.setCreatorFeeRate(Precisions.toCreatorFeeRate(collection.getCreatorFeeRate()));
        mintDataForSign.setCreatorFeeRatePrecision(Precisions.getCreatorFeeRatePrecision());
        String dataForSign = mintDataForSign.toDataForSignBase64();

        CreateWeb3NftSignRequest response = new CreateWeb3NftSignRequest();
        response.setDataForSign(dataForSign);
        response.setContent(StructContentHash.wrap(JsonUtil.toJson(request), web3Properties.getRsa().getPrivateKey()));
        response.setFrom(creator);

        String walletBridgeUrl = WalletBridgeUrl.newBuilder(web3Properties)
                .sign()
                .put("from", response.getFrom())
                .put("dataForSign", dataForSign)
                .put("content", response.getContent())
                .build();
        response.setWalletBridgeUrl(walletBridgeUrl);

        return response;
    }

    public NftAssetsDTO createConfirm(NftAssetsCreateConfirmRequest _request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        String creator = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(uid)
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();

        Web3NftAssetsCreateRequest createRequest = JsonUtil.fromJson(
                StructContentHash.unwrap(_request.getContent()),
                Web3NftAssetsCreateRequest.class
        );
        createRequest.setSignature(_request.getSignature());
        NftAssetsDTO nftAssetsDTO = create(creator, createRequest);
        return nftAssetsDTO;
    }

    @Override
    protected Web3NftAssets buildEntity(String creator, NftAssetsCreateRequest request) {
        Web3NftAssets web3NftAssets = super.buildEntity(creator, request);
        web3NftAssets.setSignature(((Web3NftAssetsCreateRequest)request).getSignature());
        return web3NftAssets;
    }
}
