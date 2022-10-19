package com.tenth.nft.web3.dao;

import com.tenth.nft.web3.entity.Web3ContractApproval;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/26 19:28
 */
@Component
public class Web3ContractApprovalDao extends SimpleDao<Web3ContractApproval> {

    public Web3ContractApprovalDao() {
        super(Web3ContractApproval.class);
    }
}