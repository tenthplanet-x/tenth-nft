package com.tenth.nft.search.lucene;

import com.tenth.nft.orm.dao.NftCollectionNoCacheDao;
import com.tenth.nft.orm.dao.expression.NftCollectionQuery;
import com.tenth.nft.orm.entity.NftCollection;
import com.tenth.nft.search.dto.SearchCollectionListRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.lucenedb.dao.LuceneDao;
import com.tpulse.gs.lucenedb.datasource.LuceneDatasource;
import org.apache.lucene.document.*;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.function.FunctionScoreQuery;
import org.apache.lucene.search.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleConsumer;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Component
public class NftCollectionLuceneDao extends LuceneDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(NftCollectionLuceneDao.class);

    private NftCollectionNoCacheDao nftCollectionNoCacheDao;

    private LuceneDatasource luceneDatasource;

    public NftCollectionLuceneDao(LucenedbProperties properties, NftCollectionNoCacheDao nftCollectionNoCacheDao) {
        super(properties.getDir(), true, true);
        this.nftCollectionNoCacheDao = nftCollectionNoCacheDao;
        luceneDatasource = create(NftCollection.class);
    }

    /**
     * 查询列表
     * @param request
     * @return
     */
    public List<Long> list(SearchCollectionListRequest request) {

        List<Long> output = new ArrayList<>();
        try{

            luceneDatasource.search(new LuceneDatasource.SearchFunction() {
                @Override
                public void search(IndexSearcher indexSearcher) throws Exception {

                    BooleanQuery.Builder builder = new BooleanQuery.Builder();

                    {
                        if(null != request.getCategoryId()){
                            Query query = NumericDocValuesField.newSlowExactQuery("categoryId", request.getCategoryId());
                            builder.add(new BooleanClause(query, BooleanClause.Occur.MUST));
                        }else{
                            Query query = NumericDocValuesField.newSlowExactQuery("categoryNull", 0);
                            builder.add(new BooleanClause(query, BooleanClause.Occur.MUST));
                        }
                    }

                    int total = request.getPage() * request.getPageSize();
                    Sort sort = new Sort(new SortField("totalVolume", SortField.Type.DOUBLE, true));
                    TopFieldDocs docs = indexSearcher.search(builder.build(), total, sort);
                    int skips = (request.getPage() - 1) * request.getPageSize();
                    List<Long> ids = Arrays.stream(docs.scoreDocs).skip(skips).limit(request.getPageSize()).map(doc -> {
                        try {
                            Document document = indexSearcher.doc(doc.doc);
                            Long id = Long.valueOf(document.get("id"));
                            return id;
                        } catch (IOException e) {
                            LOGGER.error("", e);
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());
                    output.addAll(ids);
                }
            });
        }catch (Exception e){
            LOGGER.error("", e);
        }
        return output;
    }

    @Override
    protected void createIdxes(LuceneDatasource luceneDatasource) throws Exception {
        //NO-OP
        boolean empty = false;
        int page = 1;
        do {

            Page<NftCollection> dataPage = nftCollectionNoCacheDao.findPage(NftCollectionQuery.newBuilder().setPage(page).setPageSize(100).build());
            if(dataPage.getData().isEmpty()){
                empty = true;
                continue;
            }

            luceneDatasource.write(indexWriter -> {
                for(NftCollection collection: dataPage.getData()){
                    Document document = toDocument(collection);
                    indexWriter.addDocument(document);
                }
            });
            page++;
        }while (!empty);

    }

    private Document toDocument(NftCollection collection) {
        Document document = new Document();

        document.add(new StringField("id", String.valueOf(collection.getId()), Field.Store.YES));
        document.add(new NumericDocValuesField("categoryNull", 0));
        document.add(new NumericDocValuesField("categoryId", collection.getCategoryId()));
        document.add(new DoubleDocValuesField("totalVolume", collection.getTotalVolume()));

        return document;

    }
}
