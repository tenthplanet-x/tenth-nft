package com.tenth.nft.crawler.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

import java.util.List;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:39
 */
public class ExternalNftBotQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Boolean offline;
    @SimpleQueryParam(name = "_id", opt = QueryOpt.IN)
    private List<Long> idIn;

    public Long getId() {
        return id;
    }

    public Boolean getOffline() {
        return offline;
    }

    public List<Long> getIdIn() {
        return idIn;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        ExternalNftBotQuery query;

        public Builder() {
            super(new ExternalNftBotQuery());
            this.query = (ExternalNftBotQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder offline(Boolean offline) {
            query.offline = offline;
            return this;
        }

        public Builder idIn(List<Long> ids) {
            query.idIn = ids;
            return this;
        }
    }

}
