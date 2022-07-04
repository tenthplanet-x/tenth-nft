package com.tenth.nft.search.service;

import com.tenth.nft.orm.marketplace.dao.NftAssetsDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsQuery;
import com.tenth.nft.orm.marketplace.entity.NftAssets;
import com.tenth.nft.search.dto.AssetsSearchDTO;
import com.tenth.nft.search.lucenedao.NftAssetsLuceneDao;
import com.tenth.nft.search.vo.AssetsSearchRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class AssetsSearchService {

    @Autowired
    private NftAssetsLuceneDao nftAssetsLuceneDao;
    @Autowired
    private NftAssetsDao nftAssetsDao;

    public Page<AssetsSearchDTO> list(AssetsSearchRequest request) {

        List<Long> page = nftAssetsLuceneDao.list(request);
        if(!page.isEmpty()){
            List<AssetsSearchDTO> assets = page.stream().map(id -> {
                return nftAssetsDao.findOne(
                        NftAssetsQuery.newBuilder().id(id).build(),
                        AssetsSearchDTO.class
                );
            }).collect(Collectors.toList());
            return new Page<>(
                    0,
                    assets
            );
        }

        return new Page<>();
    }

    public void rebuild(Long assetsId){

        nftAssetsDao.clearCache(assetsId);

        NftAssets nftAssets = nftAssetsDao.findOne(NftAssetsQuery.newBuilder().id(assetsId).build());
        nftAssetsLuceneDao.rebuild(nftAssets);
    }

}
