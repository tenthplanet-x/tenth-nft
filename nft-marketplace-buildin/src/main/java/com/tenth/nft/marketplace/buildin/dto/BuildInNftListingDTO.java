package com.tenth.nft.marketplace.buildin.dto;

import com.tenth.nft.marketplace.common.dto.NftListingDTO;

/**
 * @author shijie
 */
public class BuildInNftListingDTO extends NftListingDTO {

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
