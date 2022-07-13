package com.tenth.nft.crawler.dao;

import com.tenth.nft.crawler.entity.ExternalNftBot;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:39
 */
@Component
public class ExternalNftBotDao extends SimpleDao<ExternalNftBot> {

    public ExternalNftBotDao() {
        super(ExternalNftBot.class);
    }
}