package com.tenth.nft.convention.cmd;

import com.google.protobuf.Message;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.convention.cmd.CmdGroup;
import com.tpulse.gs.convention.cmd.CmdType;

/**
 * @author shijie
 */
public enum Web3NftCmdTypes implements CmdType {

    COLLECTION_CREATE_IC(NftCmdGroup.EXCHANGE, NftWeb3Cmds.COLLECTION_CREATE_IC, NftMarketplace.COLLECTION_CREATE_IC.newBuilder()),
    COLLECTION_DETAIL_IC(NftCmdGroup.EXCHANGE, NftWeb3Cmds.COLLECTION_DETAIL_IC, NftMarketplace.COLLECTION_DETAIL_IC.newBuilder()),

    ;

    private CmdGroup group;
    private int cmd;
    private Message.Builder builder;
    private boolean debugCmd;
    Web3NftCmdTypes(CmdGroup group, int cmd, Message.Builder builder, boolean debugCmd) {
        this.group = group;
        this.cmd = cmd;
        this.builder = builder;
        this.debugCmd = debugCmd;
    }

    Web3NftCmdTypes(CmdGroup group, int cmd, Message.Builder builder) {
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
