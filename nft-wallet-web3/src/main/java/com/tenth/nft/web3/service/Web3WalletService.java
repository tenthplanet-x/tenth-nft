package com.tenth.nft.web3.service;

import com.google.common.base.Strings;
import com.ruixi.tpulse.convention.TpulseHeaders;
import com.tenth.nft.convention.BuildInProperties;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.WalletCurrencyConfig;
import com.tenth.nft.convention.templates.WalletCurrencyTemplate;
import com.tenth.nft.convention.web3.sign.PersonalSignUtils;
import com.tenth.nft.convention.web3.utils.WalletBridgeUrl;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tenth.nft.solidity.ContractTransactionReceipt;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.tenth.nft.web3.dao.Web3WalletDao;
import com.tenth.nft.web3.dao.expression.Web3WalletQuery;
import com.tenth.nft.web3.dao.expression.Web3WalletUpdate;
import com.tenth.nft.web3.dto.Web3WalletBalance;
import com.tenth.nft.web3.dto.Web3WalletBindSIgnTicket;
import com.tenth.nft.web3.dto.Web3WalletDetailDTO;
import com.tenth.nft.web3.entity.Web3Wallet;
import com.tenth.nft.web3.vo.*;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shijie
 */
@Service
public class Web3WalletService {

    public static final String BIND_MESSAGE = "BIND_ACCOUNT";

    @Autowired
    private Web3j web3j;
    @Autowired
    private Web3WalletDao walletDao;
    @Autowired
    private RouteClient routeClient;
    @Value("${web3.blockchain}")
    private String blockchain;
    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    private TpulseContractHelper tpulseContractHelper;
    @Autowired
    private I18nGsTemplates i18nGsTemplates;
    @Autowired
    private BuildInProperties buildInProperties;

    public String createAuth() {
        return WalletBridgeUrl.newBuilder(web3Properties)
                .auth()
                .build();
    }

    public void bind(Web3WalletBindRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        String address = PersonalSignUtils.recover(BIND_MESSAGE, request.getSignature(), request.getAccountId());

        //remove address info bound with different accounts
        walletDao.update(
                Web3WalletQuery.newBuilder().walletAccountId(address).build(),
                Web3WalletUpdate.newBuilder().walletAccountIdNull().build()
        );

        walletDao.findAndModify(
                Web3WalletQuery.newBuilder().uid(uid).build(),
                Web3WalletUpdate.newBuilder()
                        .setBlockchain(blockchain)
                        .wallet(request.getWallet())
                        .setWalletAccountId(address)
                        .setCreatedAtOnInsert()
                        .build(),
                UpdateOptions.options().upsert(true)
        );

    }

    public void unBind() {
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        walletDao.findAndModify(
                Web3WalletQuery.newBuilder().uid(uid).build(),
                Web3WalletUpdate.newBuilder()
                        .walletNull()
                        .walletAccountIdNull()
                        .setCreatedAtOnInsert()
                        .build(),
                UpdateOptions.options().upsert(true)
        );
    }

    public List<Web3WalletBalance> balance(){

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        Web3Wallet web3Wallet = walletDao.findOne(Web3WalletQuery.newBuilder().uid(uid).build());
        if(null != web3Wallet){

            List<Web3WalletBalance> output = new ArrayList<>();

            BigDecimal ethBalance = tpulseContractHelper.getEthBalance(web3Wallet.getWalletAccountId());
            WalletCurrencyConfig ethConfig = i18nGsTemplates.get(NftTemplateTypes.wallet_currency, WalletCurrencyTemplate.class).findOne(web3Properties.getMainCurrency());
            output.add(new Web3WalletBalance(
                    web3Properties.getMainCurrency(),
                    ethBalance.toString(),
                    ethConfig.getLabel(),
                    ethConfig.getIcon()
            ));

            BigDecimal wethBalance = tpulseContractHelper.getWETHBalance(web3Wallet.getWalletAccountId());
            WalletCurrencyConfig wethConfig = i18nGsTemplates.get(NftTemplateTypes.wallet_currency, WalletCurrencyTemplate.class).findOne(web3Properties.getWethCurrency());
            output.add(new Web3WalletBalance(
                    web3Properties.getWethCurrency(),
                    wethBalance.toString(),
                    wethConfig.getLabel(),
                    wethConfig.getIcon()
            ));

            return output;
        }

        return null;

    }


    public TpulseContractHelper.ApprovalTxn createApproval() {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        Web3Wallet web3Wallet = walletDao.findOne(Web3WalletQuery.newBuilder().uid(uid).build());
        String walletAddress = web3Wallet.getWalletAccountId();
        TpulseContractHelper.ApprovalTxn approvalTxn = tpulseContractHelper.createApprovalTxn(walletAddress);
        String walletBridgeUrl = WalletBridgeUrl.newBuilder(web3Properties)
                .transaction()
                .put("from", approvalTxn.getFrom())
                .put("txnTo", approvalTxn.getTxnTo())
                .put("txnData", approvalTxn.getTxnData())
                .build();
        approvalTxn.setWalletBridgeUrl(walletBridgeUrl);
        return approvalTxn;
    }

    public void confirmApproval(Web3ContractApprovalConfirmRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        ContractTransactionReceipt receipt = tpulseContractHelper.getTxn(request.getTxn());
        if(receipt.isSuccess()){
            walletDao.update(
                   Web3WalletQuery.newBuilder().uid(uid).build(),
                    Web3WalletUpdate.newBuilder()
                            .contractAddress(web3Properties.getContract().getAddress())
                            .contractApproved(true)
                            .build()
            );
        }

    }

    public Web3WalletDetailDTO profile() {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        Web3Wallet web3Wallet = walletDao.findOne(Web3WalletQuery.newBuilder().uid(uid).build());
        if(null != web3Wallet){
            Web3WalletDetailDTO profile = new Web3WalletDetailDTO();
            profile.setWallet(web3Wallet.getWallet());
            profile.setAddress(web3Wallet.getWalletAccountId());
            profile.setContractApproved(web3Wallet.isContractApproved());
            profile.setWethContractApproved(web3Wallet.isWethContractApproved());
            return profile;
        }

        return null;
    }

    public List<Web3WalletProfile> profiles(Web3WalletProfilesRequest request) {

        Long uid = request.getUid();

        List<Web3WalletProfile> profiles = new ArrayList<>();
        {
            Web3WalletProfile profile = new Web3WalletProfile();
            profile.setBlockchain(buildInProperties.getBlockchain());
            profile.setAddress(String.valueOf(uid));
            profiles.add(profile);
        }

        {

            Web3Wallet web3Wallet = walletDao.findOne(Web3WalletQuery.newBuilder().uid(uid).build());
            if(null != web3Wallet && !Strings.isNullOrEmpty(web3Wallet.getWalletAccountId())){
                Web3WalletProfile profile = new Web3WalletProfile();
                profile.setBlockchain(web3Wallet.getBlockchain());
                profile.setAddress(web3Wallet.getWalletAccountId());
                profiles.add(profile);
            }
        }

        return profiles;
    }

    public void cancelApproval() {
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        walletDao.update(
                Web3WalletQuery.newBuilder().uid(uid).build(),
                Web3WalletUpdate.newBuilder()
                        .contractAddressNull()
                        .contractApprovedNull()
                        .build()
        );
    }

    public void updateWethApprovalState(Boolean contractApproved) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        String wethContractAddress = tpulseContractHelper.getWETHContract().getContractAddress();
        walletDao.update(
                Web3WalletQuery.newBuilder().uid(uid).build(),
                Web3WalletUpdate.newBuilder()
                        .wethContractAddress(wethContractAddress)
                        .wethContractApproved(contractApproved)
                        .build()
        );
    }


    public Web3Wallet findOne(Long uid) {
        Web3Wallet web3Wallet = walletDao.findOne(Web3WalletQuery.newBuilder().uid(uid).build());
        return web3Wallet;
    }

    public Web3WalletBindSIgnTicket prepareBind(Web3WalletBindPrepareRequest request) {

        Web3WalletBindSIgnTicket ticket = new Web3WalletBindSIgnTicket();
        String walletBridgeUrl = WalletBridgeUrl.newBuilder(web3Properties)
                .personalSign()
                .put("content", BIND_MESSAGE)
                .build();
        ticket.setWalletBridgeUrl(walletBridgeUrl);
        return ticket;
    }

    public NftWeb3Wallet.WEB3_WALLET_RPOFILE_IS walletProfile(NftWeb3Wallet.WEB3_WALLET_RPOFILE_IC request) {

        NftWeb3Wallet.WEB3_WALLET_RPOFILE_IS.Builder builder = NftWeb3Wallet.WEB3_WALLET_RPOFILE_IS.newBuilder();

        Web3Wallet web3Wallet = walletDao.findOne(Web3WalletQuery.newBuilder().walletAccountIdIn(request.getAddressesList()).build());
        if(null != web3Wallet){
            builder.addProfiles(
                    NftWeb3Wallet.Web3WalletProfileDTO.newBuilder()
                            .setAddress(Strings.isNullOrEmpty(web3Wallet.getWalletAccountId())?"": web3Wallet.getWalletAccountId())
                            .setUid(web3Wallet.getUid())
                            .build()
            );
        }

        return builder.build();
    }


}
