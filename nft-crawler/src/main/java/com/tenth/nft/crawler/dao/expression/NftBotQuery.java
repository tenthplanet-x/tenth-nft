package com.tenth.nft.crawler.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:39
 */
public class NftBotQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Boolean offline;

    public Long getId() {
        return id;
    }

    public Boolean getOffline() {
        return offline;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftBotQuery query;

        public Builder() {
            super(new NftBotQuery());
            this.query = (NftBotQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder offline(Boolean offline) {
            query.offline = offline;
            return this;
        }
    }

}
