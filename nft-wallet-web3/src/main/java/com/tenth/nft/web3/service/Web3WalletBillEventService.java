package com.tenth.nft.web3.service;

import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.UnionIds;
import com.tenth.nft.convention.routes.marketplace.web3.Web3AssetsDetailBatchRouteRequest;
import com.tenth.nft.convention.templates.*;
import com.tenth.nft.convention.wallet.WalletProductCode;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.web3.dao.Web3WalletBillDao;
import com.tenth.nft.web3.dao.Web3WalletDao;
import com.tenth.nft.web3.dao.expression.Web3WalletBillQuery;
import com.tenth.nft.web3.dao.expression.Web3WalletQuery;
import com.tenth.nft.web3.dto.Web3WalletBillEventDTO;
import com.tenth.nft.web3.dto.Web3WalletBillEventListDTO;
import com.tenth.nft.web3.entity.Web3Wallet;
import com.tenth.nft.web3.entity.Web3WalletBill;
import com.tenth.nft.web3.vo.Web3WalletBillEventDetailRequest;
import com.tenth.nft.web3.vo.Web3WalletBillEventListRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class Web3WalletBillEventService {

    @Autowired
    private Web3WalletDao web3WalletDao;
    @Autowired
    private Web3WalletBillDao web3WalletBillDao;
    @Autowired
    private I18nGsTemplates i18nGsTemplates;
    @Autowired
    private RouteClient routeClient;
    @Value("${web3.rsa.public-key}")
    private String publicKey;


    public Page<Web3WalletBillEventListDTO> list(Web3WalletBillEventListRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        Web3Wallet web3Wallet = web3WalletDao.findOne(Web3WalletQuery.newBuilder().uid(uid).build());
        String blockchain = web3Wallet.getBlockchain();
        String accountId = web3Wallet.getWalletAccountId();

        Page<Web3WalletBill> eventPage = web3WalletBillDao.findPage(
                Web3WalletBillQuery.newBuilder()
                        .blockchain(blockchain)
                        .accountId(accountId)
                        .setReverse(true)
                        .setSortField("createdAt")
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .build()
        );

        if(!eventPage.getData().isEmpty()){
            return new Page<>(
                    eventPage.getTotal(),
                    eventPage.getData().stream()
                            .map(entity -> {
                                Web3WalletBillEventListDTO dto = Web3WalletBillEventListDTO.from(entity);
                                try{
                                    dto.setProductUnionId(UnionIds.wrap(UnionIds.CHANNEL_WEB3, Long.valueOf(entity.getProductId())));
                                }catch (Exception e){
                                    //TODO NO-OP 不是特别合理在这里关联产品id
                                }
                                String gasCurrency = i18nGsTemplates.get(NftTemplateTypes.wallet_currency, WalletCurrencyTemplate.class).findMainCurrency(entity.getBlockchain()).getCode();
                                dto.setGasCurrency(gasCurrency);

                                WalletActivityTemplate walletActivityTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_activity);
                                WalletActivityConfig walletActivityConfig = walletActivityTemplate.findOne(entity.getActivityCfgId());
                                if(null != walletActivityConfig){
                                    dto.setDisplayName(walletActivityConfig.getName());
                                    dto.setDisplayIcon(walletActivityConfig.getIcon());
                                    dto.setIncomeExpense(walletActivityConfig.getIncomeExpense());
                                }
                                return dto;
                            })
                            .collect(Collectors.toList())
            );
        }

        return new Page<>(0, Collections.EMPTY_LIST);

    }

    public Web3WalletBillEventDTO detail(Web3WalletBillEventDetailRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        Web3Wallet web3Wallet = web3WalletDao.findOne(Web3WalletQuery.newBuilder().uid(uid).build());
        String blockchain = web3Wallet.getBlockchain();
        String accountId = web3Wallet.getWalletAccountId();

        Web3WalletBill entity = web3WalletBillDao.findOne(
                Web3WalletBillQuery
                        .newBuilder()
                        .id(request.getBillId())
                        .blockchain(blockchain)
                        .accountId(accountId)
                        .build()
        );
        Web3WalletBillEventDTO eventDTO = Web3WalletBillEventDTO.from(entity);
        eventDTO.setProductUnionId(UnionIds.wrap(UnionIds.CHANNEL_WEB3, Long.valueOf(entity.getProductId())));
        String gasCurrency = i18nGsTemplates.get(NftTemplateTypes.wallet_currency, WalletCurrencyTemplate.class).findMainCurrency(entity.getBlockchain()).getCode();
        eventDTO.setGasCurrency(gasCurrency);

        WalletActivityTemplate walletActivityTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_activity);
        WalletActivityConfig walletActivityConfig = walletActivityTemplate.findOne(entity.getActivityCfgId());
        if(null != walletActivityConfig){
            eventDTO.setType(walletActivityConfig.getType());
            eventDTO.setDisplayType(walletActivityConfig.getType());
            eventDTO.setIncomeExpense(walletActivityConfig.getIncomeExpense());
        }

        if(
                WalletProductCode.NFT.name().equals(entity.getProductCode()) ||
                        WalletProductCode.NFT_OFFER.name().equals(entity.getProductCode())
        ){
            eventDTO.setProductName(
                    routeClient.send(
                            NftMarketplace.ASSETS_DETAIL_BATCH_IC.newBuilder()
                                    .addAssetsId(Long.valueOf(entity.getProductId()))
                                    .build(),
                            Web3AssetsDetailBatchRouteRequest.class
                    ).getAssets(0).getName()
            );
        }

        return eventDTO;

    }

}
