package com.tenth.nft.crawler.service;

import com.google.common.base.Strings;
import com.tenth.nft.crawler.dao.NftBotDao;
import com.tenth.nft.crawler.dao.expression.NftBotQuery;
import com.tenth.nft.crawler.dao.expression.NftBotUpdate;
import com.tenth.nft.crawler.dto.NftBotDTO;
import com.tenth.nft.crawler.entity.NftBot;
import com.tenth.nft.crawler.vo.NftBotCreateRequest;
import com.tenth.nft.crawler.vo.NftBotDeleteRequest;
import com.tenth.nft.crawler.vo.NftBotEditRequest;
import com.tenth.nft.crawler.vo.NftBotListRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:39
 */
@Service
public class NftBotService {

    @Autowired
    private NftBotDao nftBotDao;

    public Page<NftBotDTO> list(NftBotListRequest request) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<NftBotDTO> dataPage = nftBotDao.findPage(
                NftBotQuery.newBuilder()
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build(),
                NftBotDTO.class
        );

        return dataPage;
    }

    public void create(NftBotCreateRequest request) {

        NftBot nftBot = new NftBot();
        nftBot.setCreatedAt(System.currentTimeMillis());
        nftBot.setUpdatedAt(System.currentTimeMillis());
        nftBot.setDescription(request.getDescription());
        nftBot.setContractAddress(request.getContractAddress());
        nftBot.setMarketplace(request.getMarketplace());
        nftBot.setMarketplaceId(request.getMarketplaceId());
        nftBot.setCollectedAt(0l);
        nftBotDao.insert(nftBot);

    }

    public void edit(NftBotEditRequest request) {

        nftBotDao.update(
                NftBotQuery.newBuilder().id(request.getId()).build(),
                NftBotUpdate.newBuilder()
                                .setDescription(request.getDescription())
                                .setContractAddress(request.getContractAddress())
                                .setCollectedAt(request.getCollectedAt())
                                .setMarketplace(request.getMarketplace())
                                .setMarketplaceId(request.getMarketplaceId())
                        .setOffline(request.getOffline())
                        .build()
        );
    }

    public void delete(NftBotDeleteRequest request) {
        nftBotDao.remove(NftBotQuery.newBuilder().id(request.getId()).build());
    }

    public NftBotDTO detail(NftBotDeleteRequest request) {

        NftBotDTO dto = nftBotDao.findOne(NftBotQuery.newBuilder()
                        .id(request.getId())
                .build(), NftBotDTO.class);

        return dto;
    }
}
