package com.tenth.nft.convention.wallet;

/**
 * @author shijie
 */
public enum WalletOrderType {

    NftExpense(1),
    NftIncome(2),
    FundsIncome(3),
    CreatorIncome(4),
    ServiceIncome(5)
    ;

    private int activityCfgId;
    WalletOrderType(int activityCfgId) {
        this.activityCfgId = activityCfgId;
    }

    public int getActivityCfgId() {
        return activityCfgId;
    }
}
