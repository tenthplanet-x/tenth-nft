package com.tenth.nft.marketplace.stats.dao;

import com.tenth.nft.marketplace.stats.entity.NftAssetsExchangeLog;
import com.tenth.nft.marketplace.stats.entity.NftAssetsVolumeStats;
import com.tenth.nft.marketplace.stats.entity.NftCollectionVolumeStats;
import com.tenth.nft.marketplace.stats.entity.NftBlockchainVolumeStats;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.mysql.MysqlDaoOperator;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/27 16:25
 */
@Component
public class NftAssetsExchangeLogDao extends SimpleDao<NftAssetsExchangeLog> {

    String statsGroupByAssetsSql = """
            SELECT `assetsId`, MIN(`blockchain`) AS blockchain, MIN(`collectionId`) AS collectionId, SUM(price) AS volume, COUNT(*) AS exchanges, MAX(exchangedAt) AS lastExchangedAt
            FROM `stats.nft_assets_exchange_log` 
            WHERE createdAt BETWEEN ? AND ?
            GROUP BY assetsId 
            """;

    String statsGroupByCollectionSql = """
            SELECT MIN(`blockchain`) AS blockchain, MIN(`collectionId`) AS collectionId, SUM(price) AS volume, COUNT(*) AS exchanges, MAX(exchangedAt) AS lastExchangedAt
            FROM `stats.nft_assets_exchange_log` 
            WHERE createdAt BETWEEN ? AND ?
            GROUP BY collectionId
            """;

    String statsGroupByBlockchainSql = """
            SELECT MIN(`blockchain`) AS blockchain, SUM(price) AS volume, COUNT(*) AS exchanges
            FROM `stats.nft_assets_exchange_log` 
            WHERE createdAt BETWEEN ? AND ?
            GROUP BY blockchain
            """;

    public NftAssetsExchangeLogDao() {
        super(NftAssetsExchangeLog.class);
    }

    public List<NftAssetsVolumeStats> doStatsGroupByAssetsId(Long start, Long end) {

        JdbcTemplate jdbcTemplate = ((MysqlDaoOperator)this.getDaoOperator()).getJdbcTemplate("");
        List<NftAssetsVolumeStats> stats = jdbcTemplate.query(
                statsGroupByAssetsSql,
                new BeanPropertyRowMapper<>(NftAssetsVolumeStats.class),
                start,
                end
        );

        return stats;
    }

    public List<NftCollectionVolumeStats> doStatsGroupByCollection(Long start, Long end) {

        JdbcTemplate jdbcTemplate = ((MysqlDaoOperator)this.getDaoOperator()).getJdbcTemplate("");
        List<NftCollectionVolumeStats> stats = jdbcTemplate.query(
                statsGroupByCollectionSql,
                new BeanPropertyRowMapper<>(NftCollectionVolumeStats.class),
                start,
                end
        );

        return stats;
    }

    public List<NftBlockchainVolumeStats> doBlockchainStats(Long start, Long end) {

        JdbcTemplate jdbcTemplate = ((MysqlDaoOperator)this.getDaoOperator()).getJdbcTemplate("");
        List<NftBlockchainVolumeStats> stats = jdbcTemplate.query(
                statsGroupByBlockchainSql,
                new BeanPropertyRowMapper<>(NftBlockchainVolumeStats.class),
                start,
                end
        );

        return stats;
    }
}