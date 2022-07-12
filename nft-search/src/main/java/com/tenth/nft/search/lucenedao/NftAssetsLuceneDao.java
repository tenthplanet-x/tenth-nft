package com.tenth.nft.search.lucenedao;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tenth.nft.orm.marketplace.dao.NftAssetsNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.NftBelongDao;
import com.tenth.nft.orm.marketplace.dao.NftBelongNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftBelongQuery;
import com.tenth.nft.orm.marketplace.dto.NftBelongIdDTO;
import com.tenth.nft.orm.marketplace.entity.NftAssets;
import com.tenth.nft.search.vo.AssetsOwnSearchRequest;
import com.tenth.nft.search.vo.AssetsSearchRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.lucenedb.dao.SimpleLuceneDao;
import com.tpulse.gs.lucenedb.dao.SimpleLucenedbProperties;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.Term;
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
public class NftAssetsLuceneDao extends SimpleLuceneDao<NftAssetsLuceneDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NftAssetsLuceneDao.class);
    private static ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("lucenedb-nftassets-init").setDaemon(true).build());

    private NftAssetsNoCacheDao nftAssetsNoCacheDao;

    private NftBelongNoCacheDao nftBelongNoCacheDao;

    public NftAssetsLuceneDao(SimpleLucenedbProperties properties, NftAssetsNoCacheDao nftAssetsNoCacheDao, NftBelongNoCacheDao nftBelongNoCacheDao) {
        super(NftAssetsLuceneDTO.class, properties);
        this.nftAssetsNoCacheDao = nftAssetsNoCacheDao;
        this.nftBelongNoCacheDao = nftBelongNoCacheDao;
        init();
    }

    public List<Long> list(AssetsSearchRequest request) {

        List<Long> output = new ArrayList<>();
        try{

            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            builder.add(new BooleanClause(LongPoint.newExactQuery("collectionId", request.getCollectionId()), BooleanClause.Occur.MUST));
            Sort sort = new Sort(new SortField("createdAt", SortField.Type.LONG, true));
            return find(builder.build(), request.getPage(), request.getPageSize(), sort).stream().map(document -> Long.valueOf(document.get("id"))).collect(Collectors.toList());
        }catch (Exception e){
            LOGGER.error("", e);
        }
        return output;
    }

    public List<Long> list(AssetsOwnSearchRequest request) {

        List<Long> output = new ArrayList<>();
        try{

            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            builder.add(new BooleanClause(new TermQuery(new Term("owners", String.valueOf(request.getUid()))), BooleanClause.Occur.MUST));
            Sort sort = new Sort(new SortField("createdAt", SortField.Type.LONG, true));
            return find(builder.build(), request.getPage(), request.getPageSize(), sort).stream().map(document -> Long.valueOf(document.get("id"))).collect(Collectors.toList());
        }catch (Exception e){
            LOGGER.error("", e);
        }
        return output;
    }

    private void init() {

        boolean empty = false;
        int page = 1;
        do {

            Page<NftAssets> dataPage = nftAssetsNoCacheDao.findPage(NftAssetsQuery.newBuilder().setPage(page).setPageSize(100).build());
            if(dataPage.getData().isEmpty()){
                empty = true;
                continue;
            }

            dataPage.getData().stream().map(this::toLuceneDTO).forEach(this::insert);

            page++;
        }while (!empty);

    }

    private NftAssetsLuceneDTO toLuceneDTO(NftAssets assets){
        NftAssetsLuceneDTO dto = new NftAssetsLuceneDTO();
        dto.setId(assets.getId());
        dto.setCreatedAt(assets.getCreatedAt());
        dto.setCollectionId(assets.getCollectionId());
        //获取拥有者信息
        List<NftBelongIdDTO> belongs = nftBelongNoCacheDao.find(NftBelongQuery.newBuilder().assetsId(assets.getId()).build(), NftBelongIdDTO.class);
        List<String> belongUids = belongs.stream().map(belong -> String.valueOf(belong.getOwner())).collect(Collectors.toList());
        dto.setOwners(belongUids);

        return dto;
    }

    public void rebuild(NftAssets nftAssets) {
        remove(nftAssets.getId());
        insert(toLuceneDTO(nftAssets));
    }

    public List<Long> listByCollectionId(Long id) {
        List<Long> output = new ArrayList<>();
        try{

            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            builder.add(new BooleanClause(LongPoint.newExactQuery("collectionId", id), BooleanClause.Occur.MUST));
            return find(builder.build(), 1, Integer.MAX_VALUE).stream().map(document -> Long.valueOf(document.get("id"))).collect(Collectors.toList());
        }catch (Exception e){
            LOGGER.error("", e);
        }
        return output;
    }
}
