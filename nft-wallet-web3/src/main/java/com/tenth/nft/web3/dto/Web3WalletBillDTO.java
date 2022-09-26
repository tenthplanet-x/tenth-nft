package com.tenth.nft.web3.dto;

import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tenth.nft.web3.entity.Web3WalletBill;

/**
 * @author shijie
 */
public class Web3WalletBillDTO {

    private Long billId;

    private String state;

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static Web3WalletBillDTO from(NftWeb3Wallet.Web3BillDTO web3BillDTO) {

        Web3WalletBillDTO billDTO = new Web3WalletBillDTO();
        billDTO.setBillId(web3BillDTO.getBillId());
        billDTO.setState(web3BillDTO.getState());

        return billDTO;
    }

    public static Web3WalletBillDTO from(Web3WalletBill entity) {

        Web3WalletBillDTO billDTO = new Web3WalletBillDTO();
        billDTO.setBillId(entity.getId());
        billDTO.setState(entity.getState());

        return billDTO;
    }
}
