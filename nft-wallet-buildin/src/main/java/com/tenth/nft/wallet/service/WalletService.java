package com.tenth.nft.wallet.service;

import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.templates.*;
import com.tenth.nft.convention.wallet.utils.BigNumberUtils;
import com.tenth.nft.protobuf.NftOperation;
import com.tenth.nft.protobuf.NftWallet;
import com.tenth.nft.wallet.dao.WalletDao;
import com.tenth.nft.wallet.dao.expression.WalletQuery;
import com.tenth.nft.wallet.dao.expression.WalletUpdate;
import com.tenth.nft.wallet.dto.WalletBalanceDTO;
import com.tenth.nft.wallet.dto.WalletProfileDTO;
import com.tenth.nft.wallet.entity.Wallet;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.router.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class WalletService {

    @Value("${wallet.blockchain}")
    private String walletBlockchain;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private WalletSettingService walletSettingService;
    @Autowired
    private WalletDao walletDao;
    @Autowired
    private I18nGsTemplates i18nGsTemplates;

    public WalletProfileDTO getProfile() {


        WalletProfileDTO walletProfileDTO = new WalletProfileDTO();

        boolean hasPassword = walletSettingService.hasPassword();
        walletProfileDTO.setHasPassword(hasPassword);
        walletProfileDTO.setBalances(getBalances());

        return walletProfileDTO;
    }

    public WalletBalanceDTO getBalance(String currency) {
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        return _getBalance(uid, currency);
    }

    private WalletBalanceDTO _getBalance(Long uid, String currency) {

        Wallet wallet = walletDao.findOne(
                WalletQuery.newBuilder().uid(uid).currency(currency).build()
        );
        if(null != wallet){
            return WalletBalanceDTO.from(wallet);
        }else{
            return WalletBalanceDTO.emptyOf(currency);
        }
    }

    public List<WalletBalanceDTO> getBalances(){

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        WalletCurrencyTemplate walletCurrencyTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_currency);
        return walletCurrencyTemplate.findByBlockchain(walletBlockchain).stream().map(currency -> {
            Wallet wallet = walletDao.findOne(WalletQuery.newBuilder().uid(uid).currency(currency.getCode()).build());
            if(null != wallet){
                return WalletBalanceDTO.from(wallet);
            }else{
                return WalletBalanceDTO.emptyOf(currency.getCode());
            }
        }).collect(Collectors.toList());

//        List<Wallet> wallets = walletDao.find(
//                WalletQuery.newBuilder().uid(uid).build()
//        );
//        if(wallets.isEmpty()){
//            initWallet(uid);
//        }
//        wallets = walletDao.find(
//                WalletQuery.newBuilder().uid(uid).build()
//        );
//        return wallets.stream().map(WalletBalanceDTO::from).collect(Collectors.toList());

    }

    /**
     * init with main currency
     * @param uid
     */
    private void initWallet(Long uid) {

        WalletCurrencyTemplate walletCurrencyTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_currency);
        WalletCurrencyConfig walletCurrencyConfig = walletCurrencyTemplate.findMainCurrency(walletBlockchain);
        String currency = walletCurrencyConfig.getCode();

        Wallet wallet = new Wallet();
        wallet.setUid(uid);
        wallet.setCurrency(currency);
        wallet.setValue("0");
        wallet.setCreatedAt(System.currentTimeMillis());
        wallet.setUpdatedAt(wallet.getCreatedAt());
        walletDao.insert(wallet);

    }

    /**
     * check balance
     * @param uid
     * @param currency
     * @param value
     */
    public void checkBalance(Long uid, String currency, String value) {

        String current = _getBalance(uid, currency).getValue();
        if(BigNumberUtils.gte(value, current)){
            throw BizException.newInstance(NftExchangeErrorCodes.WALLET_PAY_EXCEPTION_LACK_OF_BALANCE);
        }
    }


    public void decBalance(Long uid, String currency, String value) {

        String current = _getBalance(uid, currency).getValue();
        String now = BigNumberUtils.minus(current, value);

        walletDao.findAndModify(
                WalletQuery.newBuilder().uid(uid).currency(currency).build(),
                WalletUpdate.newBuilder().setValue(now).build(),
                UpdateOptions.options().upsert(true)
        );

    }

    public void incBalance(Long uid, String currency, String value) {

        String current = _getBalance(uid, currency).getValue();
        String now = BigNumberUtils.add(current, value);

        walletDao.findAndModify(
                WalletQuery.newBuilder().uid(uid).currency(currency).build(),
                WalletUpdate.newBuilder().setValue(now).build(),
                UpdateOptions.options().upsert(true)
        );
    }


}
