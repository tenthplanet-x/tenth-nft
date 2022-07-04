package com.tenth.nft.search.lucenedao;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tenth.nft.orm.marketplace.dao.NftAssetsNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsQuery;
import com.tenth.nft.orm.marketplace.entity.NftAssets;
import com.tenth.nft.search.vo.AssetsSearchRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.lucenedb.dao.SimpleLuceneDao;
import com.tpulse.gs.lucenedb.dao.SimpleLucenedbProperties;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
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

    public NftAssetsLuceneDao(SimpleLucenedbProperties properties, NftAssetsNoCacheDao nftAssetsNoCacheDao) {
        super(NftAssetsLuceneDTO.class, properties);
        this.nftAssetsNoCacheDao = nftAssetsNoCacheDao;
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
        return dto;
    }

    public void rebuild(NftAssets nftAssets) {
        remove(nftAssets.getId());
        insert(toLuceneDTO(nftAssets));
    }
}
