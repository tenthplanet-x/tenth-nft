package com.tenth.nft.exchange;

import com.tenth.nft.app.NftApplicationTest;
import com.tenth.nft.exchange.web3.service.Web3ExchangeService;
import com.tenth.nft.protobuf.NftWeb3Exchange;
import com.tenth.nft.solidity.ContractTransactionReceipt;
import com.tenth.nft.solidity.TpulseContractHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author shijie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NftApplicationTest.class)
public class Web3ExchangeServiceTest {

    @Autowired
    private Web3ExchangeService web3ExchangeService;
    @Autowired
    private TpulseContractHelper tpulseContractHelper;

    private Long assetsId = 46100l;
    private Long listingId = 40700l;

    private Long seller = 18000l;
    private String sellerAddress = "0xcc98Cf60b156a28625Ef3669f69Abc18F8cebcdc";
    private Long buyer = 7000l;

    @Test
    public void createListing() throws Exception{

        NftWeb3Exchange.WEB3_LISTING_CREATE_IS createResponse = web3ExchangeService.createListing(NftWeb3Exchange.WEB3_LISTING_CREATE_IC.newBuilder()
                        .setUid(seller)
                        .setAssetsId(assetsId)
                        .setQuantity(1)
                        .setCurrency("ETH-Ethereum")
                        .setStartAt(System.currentTimeMillis())
                        .setExpireAt(System.currentTimeMillis() + 86400000)
                        .setPrice("0.001")
                .build());
        System.out.println("createListing result: ");
        System.out.println(createResponse.getDataForSign());
        System.out.println(createResponse.getToken());
    }

    @Test
    public void confirmListing(){

        //assetsId  46000
        //uid   18000

        web3ExchangeService.confirmListing(
                NftWeb3Exchange.WEB3_LISTING_CONFIRM_IC.newBuilder()
                        .setUid(seller)
                        .setToken("eyJkb21haW4iOnsibmFtZSI6IlRwdWxzZSIsInZlcnNpb24iOiIxIiwiY2hhaW5JZCI6NCwidmVyaWZ5aW5nQ29udHJhY3QiOiIweDk5MGUxMTRhNTk4Y2QyOTNmNDc4MTMwOWRmNzAxYWIzZWVhNTY1NDIiLCJzYWx0IjoiMHgxIn0sImFzc2V0c0lkIjo0NjEwMCwicXVhbnRpdHkiOjEsImN1cnJlbmN5IjoiRVRILUV0aGVyZXVtIiwicHJpY2UiOiIwLjAwMSIsInN0YXJ0QXQiOjE2NjE0Mjk2NDI1OTUsImV4cGlyZUF0IjoxNjYxNTE2MDQyNTk1fQ==.lRVUlrubDygATxMawJesekL2zxvlohpKR6qh+chdwLq/MltiSFu3CkQfmtoF8f/x0E3RJseSdOdixjaBNYcPkFFxV9hXNUSjnnzMaaTDJsXbSglw8Y/uQAJ90q7Eui9w/j9XoV93REvPcKLadOxFCox5katTU7YMZeVMmpqe5P4=")
                        .setSignature("0x22a2279445ebcbc2ff8a7b216dbf3cc4d4157e537f3a018fb2ad5d1c7f6030952d414c5955ad52282adebb3b48c0b746cc57241dc2111b1dbf4ef2a87ad46e1a1b")
                        .build()
        );
    }

    @Test
    public void createPayment(){

        NftWeb3Exchange.WEB3_PAYMENT_CREATE_IS response = web3ExchangeService.createPayment(NftWeb3Exchange.WEB3_PAYMENT_CREATE_IC.newBuilder()
                        .setUid(buyer)
                        .setAssetsId(assetsId)
                        .setListingId(listingId)
                .build());
        String token = response.getToken();
        String txnValue = response.getTxnValue();
        String txnData = response.getTxnData();
        String txnTo = response.getTxnTo();
        System.out.println("createPayment: ");
        System.out.println(token);
        System.out.println(txnValue);
        System.out.println(txnData);
        System.out.println(txnTo);
    }

    @Test
    public void txnCheck(){

        //0x4c8a2df360c347cbadf03e85f3ae904d4b2921aa3775fa549709894985a76696
        String txn = "0xbd1bcb89890513a3b067063c6c97db6589fa3d34e58fe26c9a7c0273552087c0";
        ContractTransactionReceipt receipt = tpulseContractHelper.getTxn(txn);
        System.out.println("fail: " + receipt.isFail());
    }

    @Test
    public void service(){
//        Web3PaymentCreateRequest request = new Web3PaymentCreateRequest();
//        web3ExchangeService.createPayment()
    }

}
