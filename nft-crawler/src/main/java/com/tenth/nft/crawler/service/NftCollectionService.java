package com.tenth.nft.crawler.service;

import com.google.common.base.Strings;
import com.tenth.nft.orm.dao.NftCollectionNoCacheDao;
import com.tenth.nft.orm.dao.expression.NftCollectionQuery;
import com.tenth.nft.orm.dao.expression.NftCollectionUpdate;
import com.tenth.nft.crawler.dto.NftCollectionDTO;
import com.tenth.nft.orm.entity.NftCollection;
import com.tenth.nft.crawler.vo.NftCollectionCreateRequest;
import com.tenth.nft.crawler.vo.NftCollectionDeleteRequest;
import com.tenth.nft.crawler.vo.NftCollectionEditRequest;
import com.tenth.nft.crawler.vo.NftCollectionListRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:22
 */
@Service
public class NftCollectionService {

    @Autowired
    private NftCollectionNoCacheDao nftCollectionDao;

    public Page<NftCollectionDTO> list(NftCollectionListRequest request) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<NftCollectionDTO> dataPage = nftCollectionDao.findPage(
                NftCollectionQuery.newBuilder()
                        .categoryId(request.getCategoryId())
                        .nameRegex(request.getName())
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build(),
                NftCollectionDTO.class
        );

        return dataPage;
    }

    public void create(NftCollectionCreateRequest request) {

        NftCollection nftCollection = new NftCollection();
        nftCollection.setCreatedAt(System.currentTimeMillis());
        nftCollection.setUpdatedAt(System.currentTimeMillis());
        nftCollection.setName(request.getName());
        nftCollection.setDesc(request.getDesc());
        nftCollection.setContractAddress(request.getContractAddress());
        nftCollection.setLogoImage(request.getLogoImage());
        nftCollection.setFeaturedImage(request.getFeaturedImage());
        nftCollection.setBannerImage(request.getBannerImage());
        nftCollection.setTotalVolume(request.getTotalVolume());
        nftCollection.setFloorPrice(request.getFloorPrice());
        nftCollection.setTotalSupply(request.getTotalSupply());
        nftCollection.setCategoryId(request.getCategoryId());
        nftCollectionDao.insert(nftCollection);

    }

    public void edit(NftCollectionEditRequest request) {

        nftCollectionDao.update(
                NftCollectionQuery.newBuilder().id(request.getId()).build(),
                NftCollectionUpdate.newBuilder()
                                .setName(request.getName())
                                .setDesc(request.getDesc())
                                .setContractAddress(request.getContractAddress())
                                .setLogoImage(request.getLogoImage())
                                .setFeaturedImage(request.getFeaturedImage())
                                .setBannerImage(request.getBannerImage())
                                .setTotalVolume(request.getTotalVolume())
                                .setFloorPrice(request.getFloorPrice())
                                .setTotalSupply(request.getTotalSupply())
                                .setCategoryId(request.getCategoryId())
                        .build()
        );
    }

    public void delete(NftCollectionDeleteRequest request) {
        nftCollectionDao.remove(NftCollectionQuery.newBuilder().id(request.getId()).build());
    }

    public NftCollectionDTO detail(NftCollectionDeleteRequest request) {

        NftCollectionDTO dto = nftCollectionDao.findOne(NftCollectionQuery.newBuilder()
                        .id(request.getId())
                .build(), NftCollectionDTO.class);

        return dto;
    }
}
