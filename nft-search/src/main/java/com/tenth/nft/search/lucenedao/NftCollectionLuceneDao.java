package com.tenth.nft.search.lucenedao;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tenth.nft.convention.routes.exchange.CollectionsExchangeProfileRouteRequest;
import com.tenth.nft.orm.marketplace.dao.NftCollectionNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftCollectionQuery;
import com.tenth.nft.orm.marketplace.entity.NftCollection;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.search.vo.CollectionLuceneSearchParams;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.lucenedb.dao.SimpleLuceneDao;
import com.tpulse.gs.lucenedb.dao.SimpleLucenedbProperties;
import com.tpulse.gs.router.client.RouteClient;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.search.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Component
public class NftCollectionLuceneDao extends SimpleLuceneDao<NftCollectionLuceneDTO> {

    private static ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("lucenedb-nftcollection-init").setDaemon(true).build());
    private static final Logger LOGGER = LoggerFactory.getLogger(NftCollectionLuceneDao.class);
    private NftCollectionNoCacheDao nftCollectionNoCacheDao;
    private RouteClient routeClient;
    private NftAssetsLuceneDao nftAssetsLuceneDao;

    public NftCollectionLuceneDao(SimpleLucenedbProperties properties, NftCollectionNoCacheDao nftCollectionNoCacheDao, RouteClient routeClient, NftAssetsLuceneDao nftAssetsLuceneDao) {
        super(NftCollectionLuceneDTO.class, properties);
        this.nftCollectionNoCacheDao = nftCollectionNoCacheDao;
        this.routeClient = routeClient;
        this.nftAssetsLuceneDao = nftAssetsLuceneDao;
        //init();
    }

    /**
     * 查询列表
     * @param request
     * @return
     */
    public List<Long> list(CollectionLuceneSearchParams request) {

        List<Long> output = new ArrayList<>();
        try{

            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            {
                if(null != request.getCategoryId()){
                    Query query = LongPoint.newExactQuery("category", request.getCategoryId());
                    builder.add(new BooleanClause(query, BooleanClause.Occur.MUST));
                }else{
                    Query query = LongPoint.newExactQuery("categoryNull", 0);
                    builder.add(new BooleanClause(query, BooleanClause.Occur.MUST));
                }
            }
            {
                builder.add(new BooleanClause(LongPoint.newExactQuery("uid", request.getUid()), BooleanClause.Occur.MUST));
            }
            Sort sort = new Sort(new SortField("createdAt", SortField.Type.LONG, true));
            return find(builder.build(), request.getPage(), request.getPageSize(), sort).stream()
                    .map(document -> Long.valueOf(document.get("id")))
                    .collect(Collectors.toList());
        }catch (Exception e){
            LOGGER.error("", e);
        }
        return output;
    }

    /**
     * load histories
     */
    public void init() {
        //NO-OP
        boolean empty = false;
        int page = 1;
        do {

            Page<NftCollection> dataPage = nftCollectionNoCacheDao.findPage(NftCollectionQuery.newBuilder().setPage(page).setPageSize(100).build());
            if(dataPage.getData().isEmpty()){
                empty = true;
                continue;
            }
            //insert
            dataPage.getData().stream().map(this::toLuceneDTO).forEach(this::insert);
            page++;
        }while (!empty);
    }

    public void rebuild(NftCollection collection) {
        remove(collection.getId());
        insert(toLuceneDTO(collection));
    }

    public List<Long> recommendList(CollectionLuceneSearchParams request) {
        List<Long> output = new ArrayList<>();
        try{

            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            if(null != request.getCategoryId()){
                Query query = LongPoint.newExactQuery("category", request.getCategoryId());
                builder.add(new BooleanClause(query, BooleanClause.Occur.MUST));
            }else{
                Query query = LongPoint.newExactQuery("categoryNull", 0);
                builder.add(new BooleanClause(query, BooleanClause.Occur.MUST));
            }

            Query query = IntPoint.newRangeQuery("items", 1, Integer.MAX_VALUE);
            builder.add(new BooleanClause(query, BooleanClause.Occur.MUST));

            Sort sort = new Sort(new SortField("totalVolume", SortField.Type.FLOAT, true), new SortField("items", SortField.Type.INT, true), new SortField("createdAt", SortField.Type.LONG, true));
            return find(builder.build(), request.getPage(), request.getPageSize(), sort).stream()
                    .map(document -> Long.valueOf(document.get("id")))
                    .collect(Collectors.toList());
        }catch (Exception e){
            LOGGER.error("", e);
        }
        return output;
    }

    public void rebuild(NftMarketplace.CollectionDTO collection) {
        remove(collection.getId());
        insert(toLuceneDTO(collection));
    }

    private NftCollectionLuceneDTO toLuceneDTO(NftMarketplace.CollectionDTO nftCollection) {
        NftCollectionLuceneDTO nftCollectionLuceneDTO = new NftCollectionLuceneDTO();

        nftCollectionLuceneDTO.setId(nftCollection.getId());
        nftCollectionLuceneDTO.setCategoryNull(0l);
        nftCollectionLuceneDTO.setUid(Long.valueOf(nftCollection.getCreator()));
        nftCollectionLuceneDTO.setCreatedAt(nftCollection.getCreatedAt());
        nftCollectionLuceneDTO.setCategory(nftCollection.getCategory());
        nftCollectionLuceneDTO.setItems(nftCollection.getItems());

        nftAssetsLuceneDao.updateReader();
        List<Long> assetsIds = nftAssetsLuceneDao.listByCollectionId(nftCollection.getId());
        if(!assetsIds.isEmpty()){
            NftExchange.NftCollectionProfileDTO nftCollectionProfileDTO = routeClient.send(
                    NftExchange.COLLECTION_EXCHANGE_PROFILE_IC.newBuilder()
                            .addAllAssetsIds(assetsIds)
                            .build(),
                    CollectionsExchangeProfileRouteRequest.class
            ).getProfile();
            if(nftCollectionProfileDTO.hasTotalVolume()){
                nftCollectionLuceneDTO.setTotalVolume(Double.valueOf(nftCollectionProfileDTO.getTotalVolume()));
            }else{
                nftCollectionLuceneDTO.setTotalVolume(0d);
            }

        }

        return nftCollectionLuceneDTO;
    }

    private NftCollectionLuceneDTO toLuceneDTO(NftCollection nftCollection) {

        NftCollectionLuceneDTO nftCollectionLuceneDTO = new NftCollectionLuceneDTO();
        nftCollectionLuceneDTO.setId(nftCollection.getId());
        nftCollectionLuceneDTO.setCategoryNull(0l);
        nftCollectionLuceneDTO.setUid(nftCollection.getUid());
        nftCollectionLuceneDTO.setCreatedAt(nftCollection.getCreatedAt());
        nftCollectionLuceneDTO.setCategory(nftCollection.getCategory());
        nftCollectionLuceneDTO.setItems(nftCollection.getItems());

        nftAssetsLuceneDao.updateReader();
        List<Long> assetsIds = nftAssetsLuceneDao.listByCollectionId(nftCollection.getId());
        if(!assetsIds.isEmpty()){
            NftExchange.NftCollectionProfileDTO nftCollectionProfileDTO = routeClient.send(
                    NftExchange.COLLECTION_EXCHANGE_PROFILE_IC.newBuilder()
                            .addAllAssetsIds(assetsIds)
                            .build(),
                    CollectionsExchangeProfileRouteRequest.class
            ).getProfile();
            if(nftCollectionProfileDTO.hasTotalVolume()){
                nftCollectionLuceneDTO.setTotalVolume(Double.valueOf(nftCollectionProfileDTO.getTotalVolume()));
            }else{
                nftCollectionLuceneDTO.setTotalVolume(0d);
            }

        }
        return nftCollectionLuceneDTO;
    }
}
