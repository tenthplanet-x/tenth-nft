package com.tenth.nft.marketplace.buildin.dto;

import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.marketplace.common.dto.NftAseetsOwnerDTO;

/**
 * @author shijie
 */
public class BuildInNftAssetsOwnerDTO extends NftAseetsOwnerDTO {

    private UserProfileDTO userProfile;

    public UserProfileDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileDTO userProfile) {
        this.userProfile = userProfile;
    }
}
