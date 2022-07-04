package com.tenth.nft.marketplace.service;

import com.google.common.base.Strings;
import com.ruixi.tpulse.convention.TpulseErrorCodes;
import com.tenth.nft.orm.marketplace.dao.NftSellNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftSellQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftSellUpdate;
import com.tenth.nft.marketplace.dto.NftSellDTO;
import com.tenth.nft.orm.marketplace.entity.NftSell;
import com.tenth.nft.marketplace.vo.NftSellCreateRequest;
import com.tenth.nft.marketplace.vo.NftSellDeleteRequest;
import com.tenth.nft.marketplace.vo.NftSellEditRequest;
import com.tenth.nft.marketplace.vo.NftSellListRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.wallan.router.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 15:27
 */
@Service
public class NftSellService {

    @Autowired
    private NftSellNoCacheDao nftSellDao;

    public Page<NftSellDTO> list(NftSellListRequest request) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<NftSellDTO> dataPage = nftSellDao.findPage(
                NftSellQuery.newBuilder()
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build(),
                NftSellDTO.class
        );

        return dataPage;
    }

    public void create(NftSellCreateRequest request) {

        NftSell nftSell = new NftSell();

        if(request.getFixedPrice()){
            if(null == request.getPrice()){
                throw BizException.newInstance(TpulseErrorCodes.NFT_SELL_CREATE_EXCEPTION_NO_PRICE);
            }
        }else if(null == request.getStartPrice() || null == request.getReversePrice() || null == request.getMargin()){
            throw BizException.newInstance(TpulseErrorCodes.NFT_SELL_CREATE_EXCEPTION_ILLEGAL_AUCTION_PRICE);
        }

        NftSell exist = nftSellDao.findOne(NftSellQuery.newBuilder().build());
        if(null != exist){
            throw BizException.newInstance(TpulseErrorCodes.NFT_SELL_CREATE_EXCEPTION_ILLEGAL_AUCTION_PRICE);
        }

        nftSell.setAssetsId(request.getAssetsId());
        nftSell.setCreatedAt(System.currentTimeMillis());
        nftSell.setUpdatedAt(System.currentTimeMillis());
        nftSell.setFixedPrice(request.getFixedPrice());
        nftSell.setCurrency(request.getCurrency());
        nftSell.setPrice(request.getPrice());
        nftSell.setStartPrice(request.getStartPrice());
        nftSell.setReversePrice(request.getReversePrice());
        nftSell.setMargin(request.getMargin());
        nftSell.setStartTime(request.getStartTime());
        nftSell.setEndTime(request.getEndTime());
        nftSellDao.insert(nftSell);

    }

    public void edit(NftSellEditRequest request) {

        nftSellDao.update(
                NftSellQuery.newBuilder().id(request.getId()).build(),
                NftSellUpdate.newBuilder()
                                .setFixedPrice(request.getFixedPrice())
                                .setCurrency(request.getCurrency())
                                .setPrice(request.getPrice())
                                .setStartPrice(request.getStartPrice())
                                .setReversePrice(request.getReversePrice())
                                .setMargin(request.getMargin())
                                .setStartTime(request.getStartTime())
                                .setEndTime(request.getEndTime())
                        .build()
        );
    }

    public void delete(NftSellDeleteRequest request) {
        nftSellDao.remove(NftSellQuery.newBuilder().id(request.getId()).build());
    }

    public NftSellDTO detail(NftSellDeleteRequest request) {

        NftSellDTO dto = nftSellDao.findOne(NftSellQuery.newBuilder()
                        .id(request.getId())
                .build(), NftSellDTO.class);

        return dto;
    }
}
