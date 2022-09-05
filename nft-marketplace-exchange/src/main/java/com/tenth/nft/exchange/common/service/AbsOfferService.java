package com.tenth.nft.exchange.common.service;

import com.google.common.base.Strings;
import com.tenth.nft.assets.service.NftAssetsService;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.convention.wallet.WalletOrderBizContent;
import com.tenth.nft.convention.wallet.WalletOrderType;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftBelongQuery;
import com.tenth.nft.orm.marketplace.entity.NftAssets;
import com.tenth.nft.orm.marketplace.entity.NftBelong;
import com.tenth.nft.orm.marketplace.entity.NftOffer;
import com.tenth.nft.orm.marketplace.entity.NftOrder;
import com.wallan.router.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shijie
 */
@Service
public class AbsOfferService {

    @Autowired
    private NftAssetsService nftAssetsService;
    @Autowired
    private NftBelongService nftBelongService;

    protected void preMakeOfferCheck(
            Long uid,
            Long assetsId,
            int quantity,
            String currency,
            String price,
            Long expireAt
    ) {

        NftAssets assets = nftAssetsService.findOne(assetsId);
        if(assets.getSupply() < quantity){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_INVALID_PARAMS);
        }
        if(Times.isExpired(expireAt)){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_INVALID_PARAMS);
        }
        int owns = 0;
        NftBelong nftBelong = nftBelongService.findOne(assetsId, uid);
        if(null != nftBelong){
            owns = nftBelong.getQuantity();
        }
        if(owns == assets.getSupply()){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_BELONGS_TO_YOU);
        }
    }

    protected void preAcceptCheck(Long uid, NftOffer nftOffer) {

        if(null == nftOffer){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_NOT_EXIST);
        }
        if(Times.isExpired(nftOffer.getExpireAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_EXPIRED);
        }
        if(nftOffer.getUid().equals(uid)){
            throw BizException.newInstance(NftExchangeErrorCodes.ACCEPT_EXCEPTION_OWNS);
        }
    }

    protected List<WalletOrderBizContent.Profit> createProfits(Long seller, NftOffer nftOffer) {

        List<WalletOrderBizContent.Profit> profits = new ArrayList<>();

//        BigDecimal profitValue = new BigDecimal(nftListing.getPrice());
//        BigDecimal creatorFee = BigDecimal.ZERO;
//        if(!Strings.isNullOrEmpty(nftListing.getCreatorFeeRate())){
//            creatorFee = profitValue.multiply(new BigDecimal(nftListing.getCreatorFeeRate()).divide(new BigDecimal(100)));
//            profitValue = profitValue.subtract(creatorFee);
//        }
        //seller
        {
            WalletOrderBizContent.Profit profit = new WalletOrderBizContent.Profit();
            profit.setActivityCfgId(WalletOrderType.NftIncome.getActivityCfgId());
            profit.setCurrency(nftOffer.getCurrency());
            profit.setValue(nftOffer.getPrice());
            profit.setTo(seller);
            profits.add(profit);
        }
        //creator fee
        {
            if(new BigDecimal(nftOffer.getCreatorFeeRate()).compareTo(BigDecimal.ZERO) > 0){
                WalletOrderBizContent.Profit profit = new WalletOrderBizContent.Profit();
                profit.setActivityCfgId(WalletOrderType.CreatorIncome.getActivityCfgId());
                profit.setCurrency(nftOffer.getCurrency());
                profit.setValue(nftOffer.getCreatorFeeRate());
                profit.setTo(nftOffer.getCreatorUid());
                profits.add(profit);
            }
        }
        //service fee

        return profits;
    }

}
