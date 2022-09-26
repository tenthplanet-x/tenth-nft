package com.tenth.nft.wallet.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.wallet.dao.WalletSettingDao;
import com.tenth.nft.wallet.dao.expression.WalletSettingQuery;
import com.tenth.nft.wallet.dao.expression.WalletSettingUpdate;
import com.tenth.nft.wallet.entity.WalletSetting;
import com.tenth.nft.wallet.vo.WalletSettingRequest;
import com.tpulse.gs.convention.dao.SimpleQuery;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.wallan.router.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class WalletSettingService {

    @Autowired
    private WalletSettingDao walletSettingDao;

    public void checkPassword(Long uid, String password) {
        WalletSetting walletSetting = walletSettingDao.findOne(
                WalletSettingQuery.newBuilder().uid(uid).build()
        );
        if(null != walletSetting && password.equals(walletSetting.getPassword())){
            return;
        }
        throw BizException.newInstance(NftExchangeErrorCodes.WALLET_EXCEPTION_UNCORRECT_PASSWORD);
    }

    public boolean hasPassword() {
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        WalletSetting walletSetting = walletSettingDao.findOne(
                WalletSettingQuery.newBuilder().uid(uid).build()
        );
        return null != walletSetting && !Strings.isNullOrEmpty(walletSetting.getPassword());
    }

    public void setting(WalletSettingRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        SimpleQuery query = WalletSettingQuery.newBuilder().uid(uid).build();
        walletSettingDao.findAndModify(
                query,
                WalletSettingUpdate.newBuilder()
                        .setUid(uid)
                        .setPassword(request.getPassword())
                        .build(),
                UpdateOptions.options().upsert(true)
        );
    }
}
