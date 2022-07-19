package com.tenth.nft.search.lucenedao;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tenth.nft.orm.marketplace.dao.NftCollectionNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftCollectionQuery;
import com.tenth.nft.orm.marketplace.entity.NftCollection;
import com.tenth.nft.search.vo.CollectionLuceneSearchParams;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.lucenedb.dao.SimpleLuceneDao;
import com.tpulse.gs.lucenedb.dao.SimpleLucenedbProperties;
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

    public NftCollectionLuceneDao(SimpleLucenedbProperties properties, NftCollectionNoCacheDao nftCollectionNoCacheDao) {
        super(NftCollectionLuceneDTO.class, properties);
        this.nftCollectionNoCacheDao = nftCollectionNoCacheDao;
        init();
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
    private void init() {
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

    private NftCollectionLuceneDTO toLuceneDTO(NftCollection nftCollection) {
        NftCollectionLuceneDTO nftCollectionLuceneDTO = new NftCollectionLuceneDTO();
        nftCollectionLuceneDTO.setId(nftCollection.getId());
        nftCollectionLuceneDTO.setCategoryNull(0l);
        nftCollectionLuceneDTO.setUid(nftCollection.getUid());
        nftCollectionLuceneDTO.setCreatedAt(nftCollection.getCreatedAt());
        nftCollectionLuceneDTO.setCategory(nftCollection.getCategory());
        return nftCollectionLuceneDTO;
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
            Sort sort = new Sort(new SortField("createdAt", SortField.Type.LONG, true));
            return find(builder.build(), request.getPage(), request.getPageSize(), sort).stream()
                    .map(document -> Long.valueOf(document.get("id")))
                    .collect(Collectors.toList());
        }catch (Exception e){
            LOGGER.error("", e);
        }
        return output;
    }
}
