package com.tenth.nft.marketplace.buildin.service;

import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.marketplace.buildin.dao.BuildInNftAssetsDao;
import com.tenth.nft.marketplace.buildin.entity.BuildInNftAssets;
import com.tenth.nft.marketplace.common.dto.NftAssetsDTO;
import com.tenth.nft.marketplace.common.service.AbsNftAssetsService;
import com.tenth.nft.marketplace.common.service.AbsNftBelongService;
import com.tenth.nft.marketplace.common.service.AbsNftUbtLogService;
import com.tenth.nft.marketplace.common.vo.NftAssetsCreateRequest;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class BuildInNftAssetsService extends AbsNftAssetsService<BuildInNftAssets> {

    public BuildInNftAssetsService(
            BuildInNftAssetsDao nftAssetsDao,
            BuildInNftCollectionService nftCollectionService,
            BuildInNftBelongService nftBelongService,
            BuildInNftUbtLogService nftUbtLogService
    ) {
        super(nftAssetsDao, nftCollectionService, nftBelongService, nftUbtLogService);
    }

    @Override
    protected BuildInNftAssets newNftAssets() {
        return new BuildInNftAssets();
    }

    @Override
    protected void afterCreate(BuildInNftAssets nftAssets) {

    }

    public NftAssetsDTO create(NftAssetsCreateRequest request) {
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        return create(String.valueOf(uid), request);
    }
}
