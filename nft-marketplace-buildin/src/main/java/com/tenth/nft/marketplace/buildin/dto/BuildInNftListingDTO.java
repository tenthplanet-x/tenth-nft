package com.tenth.nft.marketplace.buildin.dto;

import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.marketplace.common.dto.NftListingDTO;

/**
 * @author shijie
 */
public class BuildInNftListingDTO extends NftListingDTO {

    private NftUserProfileDTO sellerProfile;

    public UserProfileDTO getSellerProfile() {
        return sellerProfile;
    }

    public void setSellerProfile(NftUserProfileDTO sellerProfile) {
        this.sellerProfile = sellerProfile;
    }

    public static BuildInNftListingDTO from(NftListingDTO dto) {
        BuildInNftListingDTO wrapper = new BuildInNftListingDTO();
        wrapper.setId(dto.getId());
        wrapper.setAssetsId(dto.getAssetsId());
        wrapper.setCurrency(dto.getCurrency());
        wrapper.setPrice(dto.getPrice());
        wrapper.setQuantity(dto.getQuantity());
        wrapper.setSeller(dto.getSeller());
        wrapper.setStartAt(dto.getStartAt());
        wrapper.setExpireAt(dto.getExpireAt());
        wrapper.setCreatedAt(dto.getCreatedAt());
        return wrapper;
    }

}
