package com.tenth.nft.marketplace.buildin.dto;

import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.marketplace.common.dto.NftAseetsOwnerDTO;

/**
 * @author shijie
 */
public class BuildInNftAssetsOwnerDTO extends NftAseetsOwnerDTO {

    private NftUserProfileDTO userProfile;

    public NftUserProfileDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(NftUserProfileDTO userProfile) {
        this.userProfile = userProfile;
    }
}
