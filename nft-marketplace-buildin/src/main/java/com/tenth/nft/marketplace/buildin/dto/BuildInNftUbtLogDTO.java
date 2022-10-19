package com.tenth.nft.marketplace.buildin.dto;

import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.marketplace.common.dto.NftUbtLogDTO;

/**
 * @author shijie
 */
public class BuildInNftUbtLogDTO extends NftUbtLogDTO {

    private NftUserProfileDTO fromProfile;

    private NftUserProfileDTO toProfile;

    public NftUserProfileDTO getFromProfile() {
        return fromProfile;
    }

    public void setFromProfile(NftUserProfileDTO fromProfile) {
        this.fromProfile = fromProfile;
    }

    public NftUserProfileDTO getToProfile() {
        return toProfile;
    }

    public void setToProfile(NftUserProfileDTO toProfile) {
        this.toProfile = toProfile;
    }
}
