package com.tenth.nft.convention.cmd;

import com.google.protobuf.Message;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftSearch;
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
    ASSETS_IC(NftCmdGroup.SEARCH, NftInnerCmds.ASSETS_IC, NftSearch.ASSETS_IC.newBuilder()),

    BUY_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.BUY_IC, NftExchange.BUY_IC.newBuilder()),
    SELL_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.SELL_IC, NftExchange.SELL_IC.newBuilder()),
    LISTING_LIST_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.LISTING_LIST_IC, NftExchange.LISTING_LIST_IC.newBuilder()),
    ACTIVITY_LIST_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.ACTIVITY_LIST_IC, NftExchange.ACTIVITY_LIST_IC.newBuilder()),
    MINT_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.MINT_IC, NftExchange.MINT_IC.newBuilder()),
    OWNER_LIST_IC(NftCmdGroup.EXCHANGE, NftInnerCmds.OWNER_LIST_IC, NftExchange.OWNER_LIST_IC.newBuilder()),


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
