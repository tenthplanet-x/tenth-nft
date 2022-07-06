package com.tenth.nft.exchange.dto;

import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.orm.marketplace.entity.NftBelong;
import com.tenth.nft.protobuf.NftExchange;

/**
 * @author shijie
 */
public class NftOwnerDTO {

    private Long uid;

    private Integer quantity;

    private UserProfileDTO userProfile;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UserProfileDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileDTO userProfile) {
        this.userProfile = userProfile;
    }

    public static NftOwnerDTO from(NftExchange.NftOwnerDTO nftOwnerDTO) {
        NftOwnerDTO ownerDTO = new NftOwnerDTO();
        ownerDTO.setUid(nftOwnerDTO.getUid());
        ownerDTO.setQuantity(nftOwnerDTO.getQuantity());
        return ownerDTO;
    }

    public static NftExchange.NftOwnerDTO toProto(NftBelong nftBelong) {
        return NftExchange.NftOwnerDTO.newBuilder()
                .setUid(nftBelong.getOwner())
                .setQuantity(nftBelong.getQuantity())
                .build();
    }
}
