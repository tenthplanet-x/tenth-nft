package com.tenth.nft.exchange;

import com.tenth.nft.app.NftApplicationTest;
import com.tenth.nft.orm.marketplace.dao.NftAssetsDao;
import com.tenth.nft.orm.marketplace.dao.NftAssetsNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.NftBelongDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsQuery;
import com.tenth.nft.orm.marketplace.entity.NftAssets;
import com.tenth.nft.orm.marketplace.entity.NftBelong;
import com.tpulse.gs.convention.dao.dto.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author shijie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NftApplicationTest.class)
public class NftExchangeServiceTest {

    @Autowired
    private NftBelongDao nftBelongDao;
    @Autowired
    private NftAssetsNoCacheDao nftAssetsDao;

    @Test
    public void createBelongs(){

        int page = 1;
        int pageSize = 20;
        boolean hasMore = true;
        while (hasMore){
            Page<NftAssets> nftAssets = nftAssetsDao.findPage(NftAssetsQuery.newBuilder()
                            .setPage(page)
                            .setPageSize(pageSize)
                    .build());

            hasMore = !nftAssets.getData().isEmpty();
            if(hasMore){
                for(NftAssets assets: nftAssets.getData()){
                    NftBelong nftBelong = new NftBelong();
                    nftBelong.setAssetsId(assets.getId());
                    nftBelong.setOwner(assets.getCreator());
                    nftBelong.setQuantity(assets.getSupply());
                    nftBelong.setCreatedAt(System.currentTimeMillis());
                    nftBelong.setUpdatedAt(assets.getCreatedAt());
                    nftBelongDao.insert(nftBelong);
                }
                page++;
            }else{
                System.out.println();
            }

        }
    }
}
