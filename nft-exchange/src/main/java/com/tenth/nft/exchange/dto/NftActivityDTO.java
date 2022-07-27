package com.tenth.nft.exchange.dto;

import com.google.common.base.Strings;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.blockchain.NullAddress;
import com.tenth.nft.convention.utils.Prices;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.orm.marketplace.entity.NftActivity;
import com.tenth.nft.orm.marketplace.entity.NftActivityEventType;
import com.tenth.nft.orm.marketplace.entity.event.*;
import com.tenth.nft.protobuf.NftExchange;

/**
 * @author shijie
 */
public class NftActivityDTO {

    private Long id;

    private String event;

    private Long from;

    private Long to;

    private String price;

    private String currency;

    private Integer quantity;

    private UserProfileDTO fromProfile;

    private UserProfileDTO toProfile;

    private Boolean expired;

    private Boolean canceled;

    private Long createdAt;

    private String reason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UserProfileDTO getFromProfile() {
        return fromProfile;
    }

    public void setFromProfile(UserProfileDTO fromProfile) {
        this.fromProfile = fromProfile;
    }

    public UserProfileDTO getToProfile() {
        return toProfile;
    }

    public void setToProfile(UserProfileDTO toProfile) {
        this.toProfile = toProfile;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static NftActivityDTO from(NftExchange.NftActivityDTO nftActivityDTO) {
        NftActivityDTO dto = new NftActivityDTO();
        dto.setId(nftActivityDTO.getId());
        dto.setEvent(nftActivityDTO.getEvent());
        if(nftActivityDTO.hasFrom()){
            dto.setFrom(nftActivityDTO.getFrom());
        }
        if(nftActivityDTO.hasTo()){
            dto.setTo(nftActivityDTO.getTo());
        }

        if(nftActivityDTO.hasPrice()){
            dto.setPrice(Prices.toString(nftActivityDTO.getPrice()));
            dto.setQuantity(nftActivityDTO.getQuantity());
            dto.setCurrency(nftActivityDTO.getCurrency());
        }
        if(nftActivityDTO.hasQuantity()){
            dto.setQuantity(nftActivityDTO.getQuantity());
        }

        if(nftActivityDTO.hasExpired()){
            dto.setExpired(true);
        }
        if(nftActivityDTO.hasCanceled()){
            dto.setCanceled(nftActivityDTO.getCanceled());
        }

        dto.setCreatedAt(nftActivityDTO.getCreatedAt());
        dto.setReason(nftActivityDTO.getReason());
        if(nftActivityDTO.hasExpired()){
            dto.setExpired(nftActivityDTO.getExpired());
        }
        return dto;
    }

    public static NftExchange.NftActivityDTO toProto(NftActivity nftActivity) {

        NftExchange.NftActivityDTO.Builder builder = NftExchange.NftActivityDTO.newBuilder()
                .setId(nftActivity.getId())
                .setEvent(nftActivity.getType().getLabel())
                .setCreatedAt(nftActivity.getCreatedAt())
                ;

        switch (nftActivity.getType()){
            case Minted:
                builder.setTo(nftActivity.getMint().getTo());
                builder.setQuantity(nftActivity.getMint().getQuantity());
                if(NullAddress.TOKEN.equals(nftActivity.getMint().getFrom())){
                    builder.setFrom(0);
                }
                break;
            case List:
                ListEvent listEvent = nftActivity.getList();
                builder.setFrom(listEvent.getFrom());
                builder.setCurrency(listEvent.getCurrency());
                builder.setPrice(listEvent.getPrice());
                builder.setQuantity(listEvent.getQuantity());
                if(!nftActivity.getFreeze() && null != listEvent.getExpireAt() && Times.isExpired(listEvent.getExpireAt())){
                    builder.setExpired(true);
                }
                break;
            case Sale:
                SaleEvent saleEvent = nftActivity.getSale();
                builder.setFrom(saleEvent.getFrom());
                builder.setTo(saleEvent.getTo());
                builder.setCurrency(saleEvent.getCurrency());
                builder.setPrice(saleEvent.getPrice());
                builder.setQuantity(saleEvent.getQuantity());
                break;
            case Transfer:
                TransferEvent transfer = nftActivity.getTransfer();
                builder.setFrom(transfer.getFrom());
                builder.setTo(transfer.getTo());
                builder.setCurrency(transfer.getCurrency());
                builder.setPrice(transfer.getPrice());
                builder.setQuantity(transfer.getQuantity());
                break;
            case Cancel:
                ListCancelEvent cancelEvent = nftActivity.getCancel();
                builder.setFrom(cancelEvent.getFrom());
                builder.setCurrency(cancelEvent.getCurrency());
                builder.setPrice(cancelEvent.getPrice());
                builder.setQuantity(cancelEvent.getQuantity());
                builder.setCanceled(true);
                if(!Strings.isNullOrEmpty(cancelEvent.getReason())){
                    builder.setReason(cancelEvent.getReason());
                }
                break;
            case OFFER:
            case OFFER_CANCEL:
                OfferEvent offerEvent = nftActivity.getOffer();
                builder.setFrom(offerEvent.getFrom());
                builder.setCurrency(offerEvent.getCurrency());
                builder.setPrice(offerEvent.getPrice());
                builder.setQuantity(offerEvent.getQuantity());
                builder.setCanceled(null != offerEvent.getCancel()? offerEvent.getCancel(): false);
                if(!Strings.isNullOrEmpty(offerEvent.getReason())){
                    builder.setReason(offerEvent.getReason());
                }
                if(!nftActivity.getFreeze() && nftActivity.getType() == NftActivityEventType.OFFER && null != offerEvent.getExpireAt() && Times.isExpired(offerEvent.getExpireAt())){
                    builder.setExpired(true);
                }
        }

        return builder.build();
    }
}
