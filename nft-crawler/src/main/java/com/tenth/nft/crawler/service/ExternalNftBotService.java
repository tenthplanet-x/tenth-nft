package com.tenth.nft.crawler.service;

import com.google.common.base.Strings;
import com.tenth.nft.crawler.dao.ExternalNftBotDao;
import com.tenth.nft.crawler.dao.expression.ExternalNftBotQuery;
import com.tenth.nft.crawler.dao.expression.ExternalNftBotUpdate;
import com.tenth.nft.crawler.dto.ExternalNftBotDTO;
import com.tenth.nft.crawler.entity.ExternalNftBot;
import com.tenth.nft.crawler.vo.*;
import com.tpulse.gs.convention.dao.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:39
 */
@Service
public class ExternalNftBotService {

    @Autowired
    private ExternalNftBotDao nftBotDao;

    public Page<ExternalNftBotDTO> list(ExternalNftBotListRequest request) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<ExternalNftBotDTO> dataPage = nftBotDao.findPage(
                ExternalNftBotQuery.newBuilder()
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build(),
                ExternalNftBotDTO.class
        );

        return dataPage;
    }

    public void create(ExternalNftBotCreateRequest request) {

        ExternalNftBot nftBot = new ExternalNftBot();
        nftBot.setCreatedAt(System.currentTimeMillis());
        nftBot.setUpdatedAt(System.currentTimeMillis());
        nftBot.setDescription(request.getDescription());
        nftBot.setContractAddress(request.getContractAddress());
        nftBot.setMarketplace(request.getMarketplace());
        nftBot.setMarketplaceId(request.getMarketplaceId());
        nftBot.setCollectedAt(0l);
        nftBotDao.insert(nftBot);

    }

    public void edit(ExternalNftBotEditRequest request) {

        nftBotDao.update(
                ExternalNftBotQuery.newBuilder().id(request.getId()).build(),
                ExternalNftBotUpdate.newBuilder()
                                .setDescription(request.getDescription())
                                .setContractAddress(request.getContractAddress())
                                .setCollectedAt(request.getCollectedAt())
                                .setMarketplace(request.getMarketplace())
                                .setMarketplaceId(request.getMarketplaceId())
                        .setOffline(request.getOffline())
                        .build()
        );
    }

    public void delete(ExternalNftBotDeleteRequest request) {
        nftBotDao.remove(ExternalNftBotQuery.newBuilder().id(request.getId()).build());
    }

    public ExternalNftBotDTO detail(ExternalNftBotDeleteRequest request) {

        ExternalNftBotDTO dto = nftBotDao.findOne(ExternalNftBotQuery.newBuilder()
                        .id(request.getId())
                .build(), ExternalNftBotDTO.class);

        return dto;
    }

    public void toggleOffline(ExternalNftBotToggleOfflineRequest request) {
        nftBotDao.update(
                ExternalNftBotQuery.newBuilder().idIn(request.getIds()).build(),
                ExternalNftBotUpdate.newBuilder().setOffline(request.getOffline()).build()
        );
    }
}
