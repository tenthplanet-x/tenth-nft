package com.tenth.nft.marketplace.buildin.service;

import com.tenth.nft.convention.routes.CollectionRebuildRouteRequest;
import com.tenth.nft.marketplace.buildin.entity.BuildInNftCollection;
import com.tenth.nft.marketplace.common.dao.AbsNftCollectionDao;
import com.tenth.nft.marketplace.common.entity.AbsNftCollection;
import com.tenth.nft.marketplace.common.service.AbsNftCollectionService;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class BuildInNftCollectionService extends AbsNftCollectionService<BuildInNftCollection> {

    @Autowired
    private RouteClient routeClient;

    public BuildInNftCollectionService(AbsNftCollectionDao<BuildInNftCollection> nftCollectionDao) {
        super(nftCollectionDao);
    }

    @Override
    protected BuildInNftCollection newCollection() {
        return new BuildInNftCollection();
    }

    @Override
    protected void afterInsert(AbsNftCollection collection) {
        rebuild(collection.getId());
    }

    private void rebuild(Long collectionId) {
        routeClient.send(
                NftSearch.NFT_COLLECTION_REBUILD_IC.newBuilder()
                        .setCollectionId(collectionId)
                        .build(),
                CollectionRebuildRouteRequest.class
        );
    }
}
