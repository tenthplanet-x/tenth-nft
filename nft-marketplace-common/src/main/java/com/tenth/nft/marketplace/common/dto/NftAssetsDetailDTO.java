package com.tenth.nft.marketplace.common.dto;

import com.tenth.nft.convention.dto.NftUserProfileDTO;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
public class NftAssetsDetailDTO extends NftAssetsDTO {

    private int owners;

    private NftUserProfileDTO ownerProfile;

    public int getOwners() {
        return owners;
    }

    public void setOwners(int owners) {
        this.owners = owners;
    }

    public NftUserProfileDTO getOwnerProfile() {
        return ownerProfile;
    }

    public void setOwnerProfile(NftUserProfileDTO ownerProfile) {
        this.ownerProfile = ownerProfile;
    }
}
