package com.tenth.nft.marketplace.common.dto;

import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author shijie
 */
public class NftAseetsOwnerDTO implements SimpleResponse {

    @SimpleField(name = "owner")
    private String uid;
    @SimpleField(name = "quantity")
    private Integer quantity;

    private NftUserProfileDTO userProfile;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public NftUserProfileDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(NftUserProfileDTO userProfile) {
        this.userProfile = userProfile;
    }

//    public static NftOwnerDTO from(NftExchange.NftOwnerDTO nftOwnerDTO) {
//        NftOwnerDTO ownerDTO = new NftOwnerDTO();
//        ownerDTO.setUid(nftOwnerDTO.getUid());
//        ownerDTO.setQuantity(nftOwnerDTO.getQuantity());
//        return ownerDTO;
//    }
//
//    public static NftExchange.NftOwnerDTO toProto(NftBelong nftBelong) {
//        return NftExchange.NftOwnerDTO.newBuilder()
//                .setUid(nftBelong.getOwner())
//                .setQuantity(nftBelong.getQuantity())
//                .build();
//    }
}
