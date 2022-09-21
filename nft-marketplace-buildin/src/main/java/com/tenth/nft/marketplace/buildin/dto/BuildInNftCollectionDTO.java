package com.tenth.nft.marketplace.buildin.dto;

import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.marketplace.common.dto.NftCollectionDTO;

/**
 * @author shijie
 */
public class BuildInNftCollectionDTO extends NftCollectionDTO {

    private UserProfileDTO creatorProfile;

    public UserProfileDTO getCreatorProfile() {
        return creatorProfile;
    }

    public void setCreatorProfile(UserProfileDTO creatorProfile) {
        this.creatorProfile = creatorProfile;
    }
}
