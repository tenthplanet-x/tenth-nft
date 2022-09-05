package com.tenth.nft.exchange.web3.service;

import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.routes.exchange.ExchangeEventRouteRequest;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.wallet.WalletBillState;
import com.tenth.nft.exchange.common.service.NftBelongService;
import com.tenth.nft.exchange.common.service.NftActivityService;
import com.tenth.nft.exchange.common.service.NftListingService;
import com.tenth.nft.exchange.common.service.NftOfferFlowService;
import com.tenth.nft.exchange.web3.wallet.Web3WalletProvider;
import com.tenth.nft.orm.marketplace.dao.NftOrderDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftOrderQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftOrderUpdate;
import com.tenth.nft.orm.marketplace.entity.NftOrder;
import com.tenth.nft.orm.marketplace.entity.NftOrderStatus;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftWeb3Exchange;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.tpulse.gs.convention.dao.SimpleQuery;
import com.tpulse.gs.convention.dao.id.service.GsCollectionIdService;
import com.tpulse.gs.router.client.RouteClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class Web3OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Web3ListingService.class);

    @Autowired
    @Lazy
    private NftListingService nftListingService;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftActivityService nftExchangeEventService;
    @Autowired
    private I18nGsTemplates i18nGsTemplates;
    @Autowired
    private GsCollectionIdService gsCollectionIdService;
    @Autowired
    private NftOrderDao nftOrderDao;
    @Autowired
    private TpulseContractHelper tpulseContractHelper;
    @Autowired
    private Web3WalletProvider web3WalletProvider;
    @Autowired
    private NftBelongService nftBelongService;
    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    @Lazy
    private NftOfferFlowService nftOfferFlowService;

    /**
     * Accept and handle the response from web3 wallet
     * @param request
     * @return
     */
    public NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IS confirmPayment(NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IC request) {

        NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IS.Builder builder = NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IS.newBuilder();

        Long orderId = Long.valueOf(request.getOrderId());
        SimpleQuery orderQuery = NftOrderQuery.newBuilder().assetsId(request.getAssetsId()).id(orderId).build();
        NftOrder nftOrder = nftOrderDao.findOne(orderQuery);
        if(null != nftOrder && NftOrderStatus.CREATE.equals(nftOrder.getStatus())){
            WalletBillState state = WalletBillState.valueOf(request.getState());
            switch (state){
                case PAYED:
                    //Change(inc) the quantity of assets belongs to buyer
                    nftBelongService.inc(nftOrder.getAssetsId(), nftOrder.getBuyer(), nftOrder.getQuantity());
                    //Change(dec) the quantity of assets belongs to buyer
                    nftBelongService.dec(nftOrder.getAssetsId(), nftOrder.getOwner(), nftOrder.getQuantity());
                    //Create events
                    nftExchangeEventService.sendTransferEvent(nftOrder);
                    nftExchangeEventService.sendSaleEvent(nftOrder);
                    //Send events to stats
                    sendExchangeRouteEventToStats(nftOrder.getAssetsId());
                    nftOrderDao.update(
                            NftOrderQuery.newBuilder().assetsId(nftOrder.getAssetsId()).id(nftOrder.getId()).build(),
                            NftOrderUpdate.newBuilder().setStatus(NftOrderStatus.COMPLETE).txn(request.getTxn()).build()
                    );

                    if(null != nftOrder.getListingId()){
                        nftListingService.remove(nftOrder.getAssetsId(), nftOrder.getListingId());
                        //owner listing refresh
                        nftListingService.refreshListingsBelongTo(nftOrder.getOwner(), nftOrder.getAssetsId());
                    }
                    if(null != nftOrder.getOfferId()){
                        nftOfferFlowService.remove(nftOrder.getAssetsId(), nftOrder.getOfferId());
                    }
                    break;
                case FAIL:
                    nftOrderDao.update(
                            orderQuery,
                            NftOrderUpdate.newBuilder().setStatus(NftOrderStatus.CANCEL).remark("pay failed").build()
                    );
                    break;
                default:
            }
        }

        return builder.build();
    }

    private void sendExchangeRouteEventToStats(Long assetsId) {
        routeClient.send(
                NftExchange.EXCHANGE_EVENT_IC.newBuilder().setAssetsId(assetsId).build(),
                ExchangeEventRouteRequest.class
        );
    }

    public void create(NftOrder nftOrder) {
        nftOrderDao.insert(nftOrder);
    }
}
