package com.tenth.nft.convention.dto;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;

/**
 * @author shijie
 */
public class NftUserProfileDTO extends UserProfileDTO {

    public static NftUserProfileDTO from(Search.SearchUserDTO searchUserDTO) {
        NftUserProfileDTO userProfileDTO = new NftUserProfileDTO();
        userProfileDTO.setNickname(searchUserDTO.getNickname());
        userProfileDTO.setUid(searchUserDTO.getUid());
        userProfileDTO.setFaceImg(searchUserDTO.getFaceImg());
        userProfileDTO.setGender(searchUserDTO.getGender());
        userProfileDTO.setAge(searchUserDTO.getAge());
        return userProfileDTO;
    }
}
