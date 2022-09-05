package com.tenth.nft.solidity;

import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.WalletCurrencyTemplate;
import com.tenth.nft.convention.web3.sign.DataForSign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author shijie
 */
@Component
public class TpulseContractHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(TpulseContractHelper.class);

    @Autowired
    private I18nGsTemplates i18nGsTemplates;

    private Web3j web3j;
    private Web3Properties web3Properties;
    private TpulseContract tpulseContract;
    private BigInteger blockNumber;
    private String contractOwner;
    private WETHContract wethContract;

    public TpulseContractHelper(Web3Properties web3Properties) throws Exception{
        this.web3Properties = web3Properties;
        web3j = Web3j.build(new HttpService(web3Properties.getNetwork()));
        EthGasPrice gasPrice = web3j.ethGasPrice().send();
        EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().send();
        EthBlock currentBlock = web3j.ethGetBlockByNumber(
                DefaultBlockParameter.valueOf(ethBlockNumber.getBlockNumber()),
                false
        ).send();
        Credentials credentials = Credentials.create(web3Properties.getContract().getOwnerPrivateKey());
        tpulseContract = TpulseContract.load(
                web3Properties.getContract().getAddress(),
                web3j,
                credentials,
                new DefaultGasProvider()
        );
        blockNumber = ethBlockNumber.getBlockNumber();
        contractOwner = tpulseContract.owner().send();

        //weth
        wethContract = WETHContract.load(
                web3Properties.getWethAddress(),
                web3j,
                credentials,
                new DefaultGasProvider()
        );
    }

    public Web3j getWeb3j() {
        return web3j;
    }

    public String getContractAddress() {
        return tpulseContract.getContractAddress();
    }

    public String createPaymentTransactionData(
            String seller,
            Long assetsId,
            Integer quantity,
            String price,
            String signature) {
        //throw new UnsupportedOperationException();

//        TpulseContract.Listing listing = new TpulseContract.Listing(
//                seller,
//                BigInteger.valueOf(assetsId),
//                BigInteger.valueOf(quantity),
//                Convert.toWei(price, Convert.Unit.ETHER).toBigInteger()
//        );
//        TpulseContract.Signature signatures = new TpulseContract.Signature(
//                new byte[32],
//                BigInteger.valueOf(1),
//                new byte[32],
//                new byte[32]
//        );
        String txnData = tpulseContract._buy(
                seller,
                BigInteger.valueOf(assetsId),
                BigInteger.valueOf(quantity)
        ).encodeFunctionCall();
        return txnData;
    }

    /**
     * Accept method
     * @param uidAddress
     * @param assetsId
     * @param quantity
     * @param price
     * @param expireAt
     * @param uidSignature
     * @return
     */
    public String createAcceptTransactionData(String uidAddress, Long assetsId, Integer quantity, String price, Long expireAt, String uidSignature) {
        return tpulseContract.accept(
                uidAddress,
                BigInteger.valueOf(assetsId),
                BigInteger.valueOf(quantity),
                Convert.toWei(price, Convert.Unit.ETHER).toBigInteger()
        ).encodeFunctionCall();
    }

    public DataForSign.EIP712Domain getDomain() {

        DataForSign.EIP712Domain domain = new DataForSign.EIP712Domain();
        domain.setName("Tpulse");
        domain.setChainId(web3Properties.getChainId());
        domain.setVersion("0.0.1");
        domain.setVerifyingContract(web3Properties.getContract().getAddress());
        domain.setSalt("0x1");

        return domain;

    }

    public BigDecimal getBalance(String currency, String address){

        try{
            WalletCurrencyTemplate walletCurrencyTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_currency);
            boolean isMain = walletCurrencyTemplate.findOne(currency).getMain();

            if(isMain){
                BigInteger balanceUseWei = web3j.ethGetBalance(address, DefaultBlockParameter.valueOf(blockNumber)).send().getBalance();
                return Convert.fromWei(new BigDecimal(balanceUseWei), Convert.Unit.WEI);
            }else{
                //TODO
                throw new UnsupportedOperationException("Will support later");
            }
        }catch (Exception e){
            LOGGER.error("", e);
            throw new TpulseContractException("", e);
        }
    }

    public ContractTransactionReceipt getTxn(String txn){

        try{
            EthTransaction ethTransaction = web3j.ethGetTransactionByHash(txn).send();
            Transaction transaction = ethTransaction.getResult();
            TransactionReceipt receipt = web3j.ethGetTransactionReceipt(txn).send().getResult();
            return new ContractTransactionReceipt(transaction, receipt);
        }catch (Exception e){
            LOGGER.error("", e);
            throw new TpulseContractException("", e);
        }

    }

    public ApprovalTxn createApprovalTxn(String fromAddress) {
        String txnData = tpulseContract.setApprovalForAll(tpulseContract.getContractAddress(), true).encodeFunctionCall();
        ApprovalTxn txn = new ApprovalTxn();
        txn.setFrom(fromAddress);
        txn.setTxnTo(tpulseContract.getContractAddress());
        txn.setTxnData(txnData);
        return txn;
    }

    public BigDecimal getEthBalance(String walletAccountId) {
        BigDecimal ethBalance = getBalance(web3Properties.getMainCurrency(), walletAccountId);
        ethBalance = Convert.fromWei(ethBalance, Convert.Unit.ETHER);
        return ethBalance;
    }

    public BigDecimal getWETHBalance(String walletAccountId) {
        try{
            BigInteger _balance = wethContract.balanceOf(walletAccountId).send();
            if(null == _balance){
                //balance = BigInteger.ZERO;
                return BigDecimal.ZERO;
            }
            BigDecimal balance = Convert.fromWei(new BigDecimal(_balance), Convert.Unit.ETHER);
            return balance;
        }catch (Exception e){
            LOGGER.error("", e);
            throw new TpulseContractException("", e);
        }
    }

    public WETHContract getWETHContract() {
        return wethContract;
    }

    public TpulseContract getTpulseContract() {
        return tpulseContract;
    }

    public static class ApprovalTxn{

        private String gateway;
        private String from;
        private String txnTo;
        private String txnData;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTxnTo() {
            return txnTo;
        }

        public void setTxnTo(String txnTo) {
            this.txnTo = txnTo;
        }

        public String getTxnData() {
            return txnData;
        }

        public void setTxnData(String txnData) {
            this.txnData = txnData;
        }

        public String getGateway() {
            return gateway;
        }

        public void setGateway(String gateway) {
            this.gateway = gateway;
        }
    }
}
