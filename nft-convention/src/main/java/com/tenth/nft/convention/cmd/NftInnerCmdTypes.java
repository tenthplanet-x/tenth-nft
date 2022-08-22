package com.tenth.nft.convention.cmd;

import com.google.protobuf.Message;
import com.tenth.nft.protobuf.*;
import com.tpulse.gs.convention.cmd.CmdGroup;
import com.tpulse.gs.convention.cmd.CmdType;

/**
 * @author shijie
 */
public enum NftInnerCmdTypes implements CmdType {

    EXTERNAL_NFT_CATEGORY_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.EXTERNAL_NFT_CATEGORY_REBUILD_IC, NftSearch.EXTERNAL_NFT_CATEGORY_REBUILD_IC.newBuilder(), false),
    EXTERNAL_NFT_COLLECTION_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.EXTERNAL_NFT_COLLECTION_REBUILD_IC, NftSearch.EXTERNAL_NFT_COLLECTION_REBUILD_IC.newBuilder(), false),
    EXTERNAL_NFT_ITEM_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.EXTERNAL_NFT_ITEM_REBUILD_IC, NftSearch.EXTERNAL_NFT_ITEM_REBUILD_IC.newBuilder(), false),

    NFT_CATEGORY_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.NFT_CATEGORY_REBUILD_IC, NftSearch.NFT_CATEGORY_REBUILD_IC.newBuilder()),
    NFT_COLLECTION_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.NFT_COLLECTION_REBUILD_IC, NftSearch.NFT_COLLECTION_REBUILD_IC.newBuilder()),
    NFT_ITEM_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.NFT_ASSETS_REBUILD_IC, NftSearch.NFT_ASSETS_REBUILD_IC.newBuilder()),
    NFT_BLOCKCHAIN_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.NFT_BLOCKCHAIN_REBUILD_IC, NftSearch.NFT_BLOCKCHAIN_REBUILD_IC.newBuilder()),
    NFT_CURRENCY_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.NFT_CURRENCY_REBUILD_IC, NftSearch.NFT_CURRENCY_REBUILD_IC.newBuilder()),
    NFT_CURRENCY_RATE_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.NFT_CURRENCY_RATE_REBUILD_IC, NftSearch.NFT_CURRENCY_RATE_REBUILD_IC.newBuilder()),

    ASSETS_IC(NftCmdGroup.SEARCH, NftInnerCmds.ASSETS_IC, NftSearch.ASSETS_IC.newBuilder()),
    NFT_CURRENCY_RATES_IC(NftCmdGroup.SEARCH, NftInnerCmds.NFT_CURRENCY_RATES_IC, NftSearch.NFT_CURRENCY_RATES_IC.newBuilder()),
    NFT_CURRENCY_IC(NftCmdGroup.SEARCH, NftInnerCmds.NFT_CURRENCY_IC, NftOperation.NFT_CURRENCY_IC.newBuilder()),

    BUY_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.BUY_IC, NftExchange.BUY_IC.newBuilder()),
    SELL_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.SELL_IC, NftExchange.SELL_IC.newBuilder()),
    LISTING_LIST_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.LISTING_LIST_IC, NftExchange.LISTING_LIST_IC.newBuilder()),
    ACTIVITY_LIST_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.ACTIVITY_LIST_IC, NftExchange.ACTIVITY_LIST_IC.newBuilder()),
    MINT_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.MINT_IC, NftExchange.MINT_IC.newBuilder()),
    OWNER_LIST_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.OWNER_LIST_IC, NftExchange.OWNER_LIST_IC.newBuilder()),
    ASSETS_EXCHANGE_PROFILE_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.ASSETS_EXCHANGE_PROFILE_IC, NftExchange.ASSETS_EXCHANGE_PROFILE_IC.newBuilder()),
    COLLECTION_EXCHANGE_PROFILE_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.COLLECTION_EXCHANGE_PROFILE_IC, NftExchange.COLLECTION_EXCHANGE_PROFILE_IC.newBuilder()),
    SELL_CANCEL_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.SELL_CANCEL_IC, NftExchange.SELL_CANCEL_IC.newBuilder()),
    OFFER_MAKE_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.OFFER_MAKE_IC, NftExchange.OFFER_MAKE_IC.newBuilder()),
    OFFER_ACCEPT_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.OFFER_ACCEPT_IC, NftExchange.OFFER_ACCEPT_IC.newBuilder()),
    OFFER_CANCEL_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.OFFER_CANCEL_IC, NftExchange.OFFER_CANCEL_IC.newBuilder()),
    OFFER_LIST_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.OFFER_LIST_IC, NftExchange.OFFER_LIST_IC.newBuilder()),
    LISTING_EVENT_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.LISTING_EVENT_IC, NftExchange.LISTING_EVENT_IC.newBuilder()),
    EXCHANGE_EVENT_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.EXCHANGE_EVENT_IC, NftExchange.EXCHANGE_EVENT_IC.newBuilder()),
    OFFER_EXPIRE_CHECK_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.OFFER_EXPIRE_CHECK_IC, NftExchange.OFFER_EXPIRE_CHECK_IC.newBuilder()),
    LISTING_EXPIRE_CHECK_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.LISTING_EXPIRE_CHECK_IC, NftExchange.LISTING_EXPIRE_CHECK_IC.newBuilder()),
    ASSETS_CREATE_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.ASSETS_CREATE_IC, NftMarketplace.ASSETS_CREATE_IC.newBuilder()),
    ASSETS_DETAIL_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.ASSETS_DETAIL_IC, NftMarketplace.ASSETS_DETAIL_IC.newBuilder()),
    COLLECTION_CREATE_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.COLLECTION_CREATE_IC, NftMarketplace.COLLECTION_CREATE_IC.newBuilder()),
    COLLECTION_DETAIL_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.COLLECTION_DETAIL_IC, NftMarketplace.COLLECTION_DETAIL_IC.newBuilder()),
    NFT_BLOCKCHAIN_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.NFT_BLOCKCHAIN_IC, NftOperation.NFT_BLOCKCHAIN_IC.newBuilder()),
    BUY_RECEIPT_PUSH_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.PAY_RECEIPT_PUSH_IC, NftExchange.PAY_RECEIPT_PUSH_IC.newBuilder()),

    //player
    ASSETS_BELONGS_UPDATE_IC(NftCmdGroup.PLAYER, NftInnerCmds.ASSETS_BELONGS_UPDATE_IC, NftPlayer.ASSETS_BELONGS_UPDATE_IC.newBuilder()),

    //wallet
    BILL_PAY_IC(NftCmdGroup.WALLET, NftInnerCmds.BILL_PAY_IC, NftWallet.BILL_PAY_IC.newBuilder()),
    BILL_DETAIL_IC(NftCmdGroup.WALLET, NftInnerCmds.BILL_DETAIL_IC, NftWallet.BILL_DETAIL_IC.newBuilder()),
    RECHARGE_IC(NftCmdGroup.WALLET, NftInnerCmds.RECHARGE_IC, NftWallet.RECHARGE_IC.newBuilder()),
    BILL_INCOME_TRIGGER_IC(NftCmdGroup.WALLET, NftInnerCmds.BILL_INCOME_TRIGGER_IC, NftWallet.BILL_INCOME_TRIGGER_IC.newBuilder()),

    //web3 wallet
    WEB3_BILL_PAY_IC(NftCmdGroup.WEB3_WALLET, NftInnerCmds.WEB3_BILL_PAY_IC, NftWeb3Wallet.WEB3_BILL_PAY_IC.newBuilder())
    ;


    private CmdGroup group;
    private int cmd;
    private Message.Builder builder;
    private boolean debugCmd;
    NftInnerCmdTypes(CmdGroup group, int cmd, Message.Builder builder, boolean debugCmd) {
        this.group = group;
        this.cmd = cmd;
        this.builder = builder;
        this.debugCmd = debugCmd;
    }

    NftInnerCmdTypes(CmdGroup group, int cmd, Message.Builder builder) {
        this(group, cmd, builder, false);

    }

    @Override
    public CmdGroup getGroup() {
        return group;
    }

    @Override
    public Integer getCmd() {
        return cmd;
    }

    @Override
    public Message parse(byte[] body) {
        try {
            return builder.clone().clear().mergeFrom(body).build();
        }catch (Exception e){
            throw new RuntimeException(String.format("cmdType: %s parse exception", this), e);
        }
    }

    @Override
    public boolean isDebugCmd() {
        return this.debugCmd;
    }

    /**
     * 消息构建器
     * @return
     */
    public Message.Builder getBuilder() {
        return builder;
    }
}
