package com.tenth.nft.crawler.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author shijie
 */
public class ExternalNftBotToggleOfflineRequest {

    @NotNull
    @Size(min = 1)
    private List<Long> ids;

    @NotNull
    private Boolean offline;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Boolean getOffline() {
        return offline;
    }

    public void setOffline(Boolean offline) {
        this.offline = offline;
    }
}
