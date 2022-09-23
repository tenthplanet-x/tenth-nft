package com.tenth.nft.marketplace.buildin.service;

import com.tenth.nft.marketplace.buildin.dao.BuildInNftBelongDao;
import com.tenth.nft.marketplace.buildin.entity.BuildInNftBelong;
import com.tenth.nft.marketplace.common.service.AbsNftBelongService;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class BuildInNftBelongService extends AbsNftBelongService<BuildInNftBelong> {

    public BuildInNftBelongService(BuildInNftBelongDao nftBelongDao) {
        super(nftBelongDao);
    }

    @Override
    protected void afterQuantityChange(BuildInNftBelong nftBelong) {

    }
}
