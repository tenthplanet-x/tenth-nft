package com.tenth.nft.web3.service;

import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.routes.marketplace.AssetsDetailRouteRequest;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.WalletActivityConfig;
import com.tenth.nft.convention.templates.WalletActivityTemplate;
import com.tenth.nft.convention.wallet.WalletProductCode;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.web3.dao.Web3WalletBillDao;
import com.tenth.nft.web3.dao.Web3WalletDao;
import com.tenth.nft.web3.dao.Web3WalletEventDao;
import com.tenth.nft.web3.dao.expression.Web3WalletEventQuery;
import com.tenth.nft.web3.dao.expression.Web3WalletQuery;
import com.tenth.nft.web3.dto.Web3WalletBillEventDTO;
import com.tenth.nft.web3.dto.Web3WalletBillEventListDTO;
import com.tenth.nft.web3.entity.Web3Wallet;
import com.tenth.nft.web3.entity.Web3WalletEvent;
import com.tenth.nft.web3.vo.Web3WalletBillEventDetailRequest;
import com.tenth.nft.web3.vo.Web3WalletBillEventListRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
public class Web3WalletBillEventService {

    @Autowired
    private Web3WalletDao web3WalletDao;
    @Autowired
    private Web3WalletBillDao web3WalletBillDao;
    @Autowired
    private Web3WalletEventDao web3WalletEventDao;
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

        Page<Web3WalletEvent> eventPage = web3WalletEventDao.findPage(
                Web3WalletEventQuery.newBuilder()
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

        Web3WalletEvent entity = web3WalletEventDao.findOne(
                Web3WalletEventQuery
                        .newBuilder()
                        .id(request.getId())
                        .blockchain(blockchain)
                        .accountId(accountId)
                        .build()
        );
        Web3WalletBillEventDTO eventDTO = Web3WalletBillEventDTO.from(entity);

        WalletActivityTemplate walletActivityTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_activity);
        WalletActivityConfig walletActivityConfig = walletActivityTemplate.findOne(entity.getActivityCfgId());
        if(null != walletActivityConfig){
            eventDTO.setDisplayType(walletActivityConfig.getType());
            eventDTO.setIncomeExpense(walletActivityConfig.getIncomeExpense());
        }

        if(WalletProductCode.NFT.name().equals(entity.getProductCode())){
            eventDTO.setProductName(
                    routeClient.send(
                            NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                                    .setId(Long.valueOf(entity.getProductId()))
                                    .build(),
                            AssetsDetailRouteRequest.class
                    ).getAssets().getName()
            );
        }

        return eventDTO;

    }

}
