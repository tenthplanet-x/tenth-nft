package com.tenth.nft.marketplace.buildin.dto;

import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.marketplace.common.dto.NftOfferDTO;

/**
 * @author shijie
 */
public class BuildInNftOfferDTO extends NftOfferDTO {

    private NftUserProfileDTO userProfile;

    public NftUserProfileDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(NftUserProfileDTO userProfile) {
        this.userProfile = userProfile;
    }
}
