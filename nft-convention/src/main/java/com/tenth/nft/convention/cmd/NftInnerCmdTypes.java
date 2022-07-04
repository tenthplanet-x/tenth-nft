package com.tenth.nft.convention.cmd;

import com.google.protobuf.Message;
import com.ruixi.tpulse.convention.TpulseCmdGroup;
import com.ruixi.tpulse.convention.TpulseInnerCmds;
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
    NFT_COLLECTION_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.NFT_COLLECTION_REBUILD_IC, com.ruixi.tpulse.convention.protobuf.NftSearch.NFT_COLLECTION_REBUILD_IC.newBuilder()),
    NFT_ITEM_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.NFT_ASSETS_REBUILD_IC, com.ruixi.tpulse.convention.protobuf.NftSearch.NFT_ASSETS_REBUILD_IC.newBuilder()),
    NFT_BLOCKCHAIN_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.NFT_BLOCKCHAIN_REBUILD_IC, com.ruixi.tpulse.convention.protobuf.NftSearch.NFT_BLOCKCHAIN_REBUILD_IC.newBuilder()),
    NFT_CURRENCY_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.NFT_CURRENCY_REBUILD_IC, com.ruixi.tpulse.convention.protobuf.NftSearch.NFT_CURRENCY_REBUILD_IC.newBuilder()),
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
