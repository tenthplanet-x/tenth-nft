package com.tenth.nft.exchange.common.service;

import com.tenth.nft.convention.routes.player.AssetsBelongsUpdateRouteRequest;
import com.tenth.nft.orm.marketplace.dao.NftBelongDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftBelongQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftBelongUpdate;
import com.tenth.nft.orm.marketplace.entity.NftBelong;
import com.tenth.nft.protobuf.NftPlayer;
import com.tpulse.gs.convention.dao.SimpleQuery;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shijie
 */
@Service
public class NftBelongService {

    @Autowired
    private NftBelongDao nftBelongDao;
    @Autowired
    private RouteClient routeClient;

    public NftBelong findOne(Long assetsId, Long owner) {
        SimpleQuery preBelongQuery = NftBelongQuery.newBuilder().assetsId(assetsId).owner(owner).build();
        return nftBelongDao.findOne(preBelongQuery);
    }

    public void create(long assetsId, long owner, int quantity) {

        NftBelong belong = new NftBelong();
        belong.setOwner(owner);
        belong.setAssetsId(assetsId);
        belong.setQuantity(quantity);
        belong.setCreatedAt(System.currentTimeMillis());
        belong.setUpdatedAt(belong.getCreatedAt());
        nftBelongDao.insert(belong);
        //Sync to player
        syncOwnsToPlayer(belong);
    }

    public void dec(Long assetsId, Long owner, int quantity) {

        NftBelong belong = nftBelongDao.findAndModify(
                NftBelongQuery.newBuilder().assetsId(assetsId).owner(owner).build(),
                NftBelongUpdate.newBuilder().quantityInc(-quantity).build(),
                UpdateOptions.options().returnNew(true)
        );
        if(belong.getQuantity() <= 0){
            nftBelongDao.remove(NftBelongQuery.newBuilder().assetsId(assetsId).owner(owner).build());
        }
        //Sync to player
        syncOwnsToPlayer(belong);
    }

    private void syncOwnsToPlayer(NftBelong belong) {
        routeClient.send(
                NftPlayer.ASSETS_BELONGS_UPDATE_IC.newBuilder()
                        .setUid(belong.getOwner())
                        .setAssetsId(belong.getAssetsId())
                        .setOwns(belong.getQuantity())
                        .build(),
                AssetsBelongsUpdateRouteRequest.class
        );
    }

    public long ownersOf(Long assetsId) {
        return nftBelongDao.count(NftBelongQuery.newBuilder().assetsId(assetsId).build());
    }

    public List<NftBelong> findAll(Long assetsId) {
        return nftBelongDao.find(NftBelongQuery.newBuilder().assetsId(assetsId).build());
    }

    public NftBelong inc(Long assetsId, Long owner, Integer quantity) {

        NftBelong nftBelong = nftBelongDao.findAndModify(
                NftBelongQuery.newBuilder().assetsId(assetsId).owner(owner).build(),
                NftBelongUpdate.newBuilder()
                        .quantityInc(quantity)
                        .createdAtOnInsert()
                        .build(),
                UpdateOptions.options().upsert(true).returnNew(true)
        );
        //Send new quantity of the assets to player
        syncOwnsToPlayer(nftBelong);

        return nftBelong;
    }
}
