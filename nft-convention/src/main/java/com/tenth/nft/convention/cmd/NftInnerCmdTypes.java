package com.tenth.nft.convention.cmd;

import com.google.protobuf.Message;
import com.tenth.nft.protobuf.Search;
import com.tpulse.gs.convention.cmd.CmdGroup;
import com.tpulse.gs.convention.cmd.CmdType;

/**
 * @author shijie
 */
public enum NftInnerCmdTypes implements CmdType {

    NFT_CATEGORY_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.NFT_CATEGORY_REBUILD_IC, Search.NFT_CATEGORY_REBUILD_IC.newBuilder(), false),
    NFT_COLLECTION_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.NFT_COLLECTION_REBUILD_IC, Search.NFT_COLLECTION_REBUILD_IC.newBuilder(), false),
    NFT_ITEM_REBUILD_IC(NftCmdGroup.SEARCH, NftInnerCmds.NFT_ITEM_REBUILD_IC, Search.NFT_ITEM_REBUILD_IC.newBuilder(), false),

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
            throw new RuntimeException(String.format("cmdType: %s 解析异常", this), e);
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
