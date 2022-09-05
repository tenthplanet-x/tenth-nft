package com.tenth.nft.exchange.common.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.convention.wallet.WalletOrderBizContent;
import com.tenth.nft.convention.wallet.WalletOrderType;
import com.tenth.nft.orm.marketplace.entity.NftBelong;
import com.tenth.nft.orm.marketplace.entity.NftListing;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.router.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shijie
 */
public abstract class AbsListingFlowService {

    @Autowired
    private NftBelongService nftBelongService;
    @Autowired
    protected Web3Properties web3Properties;
    @Autowired
    private I18nGsTemplates i18nGsTemplates;
    @Autowired
    private RouteClient routeClient;

    protected void preListingCheck(
            long uid,
            long assetsId,
            int quantity,
            String currency,
            String price,
            long expireAt
    ) {
        if(Times.isExpired(expireAt)){
            throw BizException.newInstance(NftExchangeErrorCodes.SELL_EXCEPTION_INVALID_PARAMS);
        }
        //Quantity check
        NftBelong nftBelong = nftBelongService.findOne(assetsId, uid);
        if(null == nftBelong || nftBelong.getQuantity() < quantity){
            throw BizException.newInstance(NftExchangeErrorCodes.SELL_EXCEPTION_INVALID_PARAMS);
        }
    }

    protected void preBuyCheck(Long uid, NftListing nftListing) {
        if(null == nftListing || nftListing.getCanceled()){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_NO_EXIST);
        }
        if(Times.earlierThan(nftListing.getStartAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_NOT_START);
        }
        if(Times.isExpired(nftListing.getExpireAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_EXPIRED);
        }
        if(nftListing.getUid().equals(uid)){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_BELONGS_TO_YOU);
        }
    }

    protected List<WalletOrderBizContent.Profit> createProfits(NftListing nftListing) {

        List<WalletOrderBizContent.Profit> profits = new ArrayList<>();

        BigDecimal profitValue = new BigDecimal(nftListing.getPrice());
        BigDecimal creatorFee = BigDecimal.ZERO;
        if(!Strings.isNullOrEmpty(nftListing.getCreatorFeeRate())){
            creatorFee = profitValue.multiply(new BigDecimal(nftListing.getCreatorFeeRate()).divide(new BigDecimal(100)));
            profitValue = profitValue.subtract(creatorFee);
        }
        //seller
        {
            WalletOrderBizContent.Profit profit = new WalletOrderBizContent.Profit();
            profit.setActivityCfgId(WalletOrderType.NftIncome.getActivityCfgId());
            profit.setCurrency(nftListing.getCurrency());
            profit.setValue(profitValue.toString());
            profit.setTo(nftListing.getUid());
            profits.add(profit);
        }
        //creator fee
        {
            if(creatorFee.compareTo(BigDecimal.ZERO) > 0){
                WalletOrderBizContent.Profit profit = new WalletOrderBizContent.Profit();
                profit.setActivityCfgId(WalletOrderType.CreatorIncome.getActivityCfgId());
                profit.setCurrency(nftListing.getCurrency());
                profit.setValue(creatorFee.toString());
                profit.setTo(nftListing.getCreatorUid());
                profits.add(profit);
            }
        }
        //service fee

        return profits;

    }

}
