package com.tenth.nft.exchange.common.service;

import com.tenth.nft.orm.marketplace.dao.NftActivityDao;
import com.tenth.nft.orm.marketplace.dao.NftOfferDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftOfferQuery;
import com.tenth.nft.orm.marketplace.entity.NftActivity;
import com.tenth.nft.orm.marketplace.entity.NftActivityEventType;
import com.tenth.nft.orm.marketplace.entity.NftOffer;
import com.tenth.nft.orm.marketplace.entity.event.OfferEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class NftOfferFlowService {

    @Autowired
    private NftOfferDao nftOfferDao;
    @Autowired
    private NftActivityDao nftActivityDao;

    public void create(NftOffer nftOffer) {

        Long activityId = sendOfferEvent(nftOffer);
        nftOffer.setActivityId(activityId);
        nftOfferDao.insert(nftOffer);

    }

    private Long sendOfferEvent(NftOffer nftOffer) {

        NftActivity nftActivity = new NftActivity();
        nftActivity.setAssetsId(nftOffer.getAssetsId());
        nftActivity.setType(NftActivityEventType.OFFER);
        nftActivity.setCreatedAt(System.currentTimeMillis());
        nftActivity.setUpdatedAt(nftActivity.getCreatedAt());

        OfferEvent offerEvent = new OfferEvent();
        offerEvent.setFrom(nftOffer.getUid());
        offerEvent.setQuantity(nftOffer.getQuantity());
        offerEvent.setPrice(nftOffer.getPrice());
        offerEvent.setCurrency(nftOffer.getCurrency());
        offerEvent.setExpireAt(nftOffer.getExpireAt());
        nftActivity.setOffer(offerEvent);

        return nftActivityDao.insert(nftActivity).getId();
    }

    public NftOffer findOne(Long assetsId, Long offerId) {
        return nftOfferDao.findOne(NftOfferQuery.newBuilder().assetsId(assetsId).id(offerId).build());

    }
}
