package com.tenth.nft.marketplace.common.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.blockchain.NullAddress;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.marketplace.common.dao.AbsNftUbtLogDao;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftUbtLogQuery;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftUbtLogUpdate;
import com.tenth.nft.marketplace.common.dto.NftUbtLogDTO;
import com.tenth.nft.marketplace.common.entity.*;
import com.tenth.nft.marketplace.common.entity.event.*;
import com.tenth.nft.marketplace.common.vo.NftUbtLogListRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public abstract class AbsNftUbtLogService<T extends AbsNftUbtLog> {

    @Autowired
    private RouteClient routeClient;

    private AbsNftUbtLogDao<T> nftUbtLogDao;

    public AbsNftUbtLogService(AbsNftUbtLogDao nftUbtLogDao) {
        this.nftUbtLogDao = nftUbtLogDao;
    }

    public Page<NftUbtLogDTO> list(NftUbtLogListRequest request) {

        Page<T> dataPage = nftUbtLogDao.findPage(
                AbsNftUbtLogQuery.newBuilder()
                        .assetsId(request.getAssetsId())
                        .event(null != request.getEvent()? request.getEvent().name(): null)
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField("_id")
                        .setReverse(true)
                        .build()
        );

        List<NftUbtLogDTO> dtos = dataPage.getData().stream().map(this::toDTO).collect(Collectors.toList());

        //fill with userProfile
//        Set<Long> fromUids = dtos.stream().map(dto -> dto.getFrom()).filter(Objects::nonNull).filter(from -> !NullAddress.TOKEN.equals(from)).collect(Collectors.toSet());
//        Set<Long> toUids = dtos.stream().map(dto -> dto.getTo()).filter(Objects::nonNull).filter(from -> !NullAddress.TOKEN.equals(from)).collect(Collectors.toSet());
//        fromUids.addAll(toUids);
//        Map<Long, NftUserProfileDTO> userProfileDTOMap = routeClient.send(
//                Search.SEARCH_USER_PROFILE_IC.newBuilder().addAllUids(fromUids).build(),
//                SearchUserProfileRouteRequest.class
//        ).getProfilesList().stream().map(NftUserProfileDTO::from).collect(Collectors.toMap(NftUserProfileDTO::getUid, Function.identity()));
//        dtos.stream().forEach(dto -> {
//            if(null != dto.getFrom()){
//                dto.setFromProfile(userProfileDTOMap.get(dto.getFrom()));
//            }
//            if(null != dto.getTo()){
//                dto.setToProfile(userProfileDTOMap.get(dto.getTo()));
//            }
//        });

        return new Page<>(0, dtos);
    }

    public void sendMintEvent(AbsNftAssets assets) {

        T activity = newNftUbtLog();
        activity.setAssetsId(assets.getId());
        activity.setType(NftActivityEventType.Minted);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        MintEvent mintEvent = new MintEvent();
        mintEvent.setFrom(NullAddress.TOKEN);
        mintEvent.setTo(assets.getCreator());
        mintEvent.setQuantity(assets.getSupply());
        activity.setMint(mintEvent);

        nftUbtLogDao.insert(activity);
    }

    protected abstract T newNftUbtLog();

    public Long sendListingEvent(AbsNftListing listing) {

        T activity = newNftUbtLog();
        activity.setAssetsId(listing.getAssetsId());
        activity.setType(NftActivityEventType.List);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        ListEvent listEvent = new ListEvent();
        listEvent.setFrom(listing.getSeller());
        listEvent.setPrice(listing.getPrice().toString());
        listEvent.setQuantity(listing.getQuantity());
        listEvent.setCurrency(listing.getCurrency());
        listEvent.setExpireAt(listing.getExpireAt());
        activity.setList(listEvent);

        activity = nftUbtLogDao.insert(activity);
        return activity.getId();

    }

    public void sendTransferEvent(AbsNftOrder nftOrder) {

        T activity = newNftUbtLog();
        activity.setAssetsId(nftOrder.getAssetsId());
        activity.setType(NftActivityEventType.Transfer);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        TransferEvent transfer = new TransferEvent();
        transfer.setFrom(nftOrder.getSeller());
        transfer.setTo(nftOrder.getBuyer());
        transfer.setQuantity(nftOrder.getQuantity());
        transfer.setPrice(nftOrder.getPrice().toString());
        transfer.setCurrency(nftOrder.getCurrency());
        activity.setTransfer(transfer);

        nftUbtLogDao.insert(activity);

    }

    public void sendSaleEvent(AbsNftOrder nftOrder) {

        T activity = newNftUbtLog();
        activity.setAssetsId(nftOrder.getAssetsId());
        activity.setType(NftActivityEventType.Sale);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        SaleEvent sale = new SaleEvent();
        sale.setFrom(nftOrder.getSeller());
        sale.setTo(nftOrder.getBuyer());
        sale.setQuantity(nftOrder.getQuantity());
        sale.setPrice(nftOrder.getPrice().toString());
        sale.setCurrency(nftOrder.getCurrency());
        activity.setSale(sale);

        nftUbtLogDao.insert(activity);
    }

    /**
     * Make it can't show expired state
     */
    public void freezeListingEvent(Long activityId) {
        if(null != activityId){
            nftUbtLogDao.update(
                    AbsNftUbtLogQuery.newBuilder().id(activityId).build(),
                    AbsNftUbtLogUpdate.newBuilder().freeze(true).build()
            );
        }
    }

    public void createCancelEvent(AbsNftListing nftListing, String reason) {

        freezeListingEvent(nftListing.getActivityId());

        T activity = newNftUbtLog();
        activity.setAssetsId(nftListing.getAssetsId());
        activity.setType(NftActivityEventType.Cancel);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        ListCancelEvent list = new ListCancelEvent();
        list.setFrom(nftListing.getSeller());
        list.setQuantity(nftListing.getQuantity());
        list.setPrice(nftListing.getPrice().toString());
        list.setCurrency(nftListing.getCurrency());
        list.setReason(reason);
        activity.setCancel(list);

        nftUbtLogDao.insert(activity);

    }

    public Long sendOfferEvent(AbsNftOffer nftOffer) {

        T activity = newNftUbtLog();
        activity.setAssetsId(nftOffer.getAssetsId());
        activity.setType(NftActivityEventType.OFFER);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        OfferEvent offerEvent = new OfferEvent();
        offerEvent.setFrom(nftOffer.getBuyer());
        offerEvent.setQuantity(nftOffer.getQuantity());
        offerEvent.setPrice(nftOffer.getPrice().toString());
        offerEvent.setCurrency(nftOffer.getCurrency());
        offerEvent.setExpireAt(nftOffer.getExpireAt());
        activity.setOffer(offerEvent);

        return nftUbtLogDao.insert(activity).getId();
    }

    public void sendOfferCancelEvent(AbsNftOffer nftOffer) {

        //freeze offer event
        freezeOfferEvent(nftOffer);

        //cancel event
        T activity = newNftUbtLog();
        activity.setAssetsId(nftOffer.getAssetsId());
        activity.setType(NftActivityEventType.OFFER_CANCEL);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());
        OfferEvent offerEvent = new OfferEvent();
        offerEvent.setFrom(nftOffer.getBuyer());
        offerEvent.setQuantity(nftOffer.getQuantity());
        offerEvent.setPrice(nftOffer.getPrice().toString());
        offerEvent.setCurrency(nftOffer.getCurrency());
        offerEvent.setCancel(true);
        activity.setOffer(offerEvent);
        nftUbtLogDao.insert(activity);
    }

    public void freezeOfferEvent(AbsNftOffer nftOffer) {
        if(null != nftOffer && null != nftOffer.getActivityId()){
            nftUbtLogDao.update(
                    AbsNftUbtLogQuery.newBuilder().id(nftOffer.getActivityId()).build(),
                    AbsNftUbtLogUpdate.newBuilder().freeze(true).build()
            );
        }
    }

    protected NftUbtLogDTO toDTO(T nftActivity) {
        NftUbtLogDTO nftActivityDTO = newDTO();
        nftActivityDTO.setId(nftActivity.getId());
        nftActivityDTO.setEvent(nftActivity.getType().getLabel());
        nftActivityDTO.setCreatedAt(nftActivity.getCreatedAt());


        switch (nftActivity.getType()){
            case Minted:
                nftActivityDTO.setTo(nftActivity.getMint().getTo());
                nftActivityDTO.setQuantity(nftActivity.getMint().getQuantity());
                if(NullAddress.TOKEN.equals(nftActivity.getMint().getFrom())){
                    //nftActivityDTO.setFrom(0);
                }
                break;
            case List:
                ListEvent listEvent = nftActivity.getList();
                nftActivityDTO.setFrom(listEvent.getFrom());
                nftActivityDTO.setCurrency(listEvent.getCurrency());
                nftActivityDTO.setPrice(listEvent.getPrice());
                nftActivityDTO.setQuantity(listEvent.getQuantity());
                if(!nftActivity.getFreeze() && null != listEvent.getExpireAt() && Times.isExpired(listEvent.getExpireAt())){
                    nftActivityDTO.setExpired(true);
                }
                break;
            case Sale:
                SaleEvent saleEvent = nftActivity.getSale();
                nftActivityDTO.setFrom(saleEvent.getFrom());
                nftActivityDTO.setTo(saleEvent.getTo());
                nftActivityDTO.setCurrency(saleEvent.getCurrency());
                nftActivityDTO.setPrice(saleEvent.getPrice());
                nftActivityDTO.setQuantity(saleEvent.getQuantity());
                break;
            case Transfer:
                TransferEvent transfer = nftActivity.getTransfer();
                nftActivityDTO.setFrom(transfer.getFrom());
                nftActivityDTO.setTo(transfer.getTo());
                nftActivityDTO.setCurrency(transfer.getCurrency());
                nftActivityDTO.setPrice(transfer.getPrice());
                nftActivityDTO.setQuantity(transfer.getQuantity());
                break;
            case Cancel:
                ListCancelEvent cancelEvent = nftActivity.getCancel();
                nftActivityDTO.setFrom(cancelEvent.getFrom());
                nftActivityDTO.setCurrency(cancelEvent.getCurrency());
                nftActivityDTO.setPrice(cancelEvent.getPrice());
                nftActivityDTO.setQuantity(cancelEvent.getQuantity());
                nftActivityDTO.setCanceled(true);
                if(!Strings.isNullOrEmpty(cancelEvent.getReason())){
                    nftActivityDTO.setReason(cancelEvent.getReason());
                }
                break;
            case OFFER:
            case OFFER_CANCEL:
                OfferEvent offerEvent = nftActivity.getOffer();
                nftActivityDTO.setFrom(offerEvent.getFrom());
                nftActivityDTO.setCurrency(offerEvent.getCurrency());
                nftActivityDTO.setPrice(offerEvent.getPrice());
                nftActivityDTO.setQuantity(offerEvent.getQuantity());
                nftActivityDTO.setCanceled(null != offerEvent.getCancel()? offerEvent.getCancel(): false);
                if(!Strings.isNullOrEmpty(offerEvent.getReason())){
                    nftActivityDTO.setReason(offerEvent.getReason());
                }
                if(!nftActivity.getFreeze() && nftActivity.getType() == NftActivityEventType.OFFER && null != offerEvent.getExpireAt() && Times.isExpired(offerEvent.getExpireAt())){
                    nftActivityDTO.setExpired(true);
                }
        }

        return nftActivityDTO;
    }

    protected NftUbtLogDTO newDTO() {
        return new NftUbtLogDTO();
    }
}
