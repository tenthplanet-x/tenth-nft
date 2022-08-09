package com.tenth.nft.wallet.dao;

import com.tenth.nft.wallet.entity.WalletSetting;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/08 19:17
 */
@Component
public class WalletSettingDao extends SimpleDao<WalletSetting> {

    public WalletSettingDao() {
        super(WalletSetting.class);
    }
}