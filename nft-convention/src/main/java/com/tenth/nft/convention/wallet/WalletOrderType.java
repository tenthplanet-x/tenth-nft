package com.tenth.nft.convention.wallet;

/**
 * @author shijie
 */
public enum WalletOrderType {

    PAY(1),
    RECEIVE(2),
    RECHAGE(3);

    private int activityCfgId;
    WalletOrderType(int activityCfgId) {
        this.activityCfgId = activityCfgId;
    }

    public int getActivityCfgId() {
        return activityCfgId;
    }
}
