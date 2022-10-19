package com.tenth.nft.marketplace.service;

import com.tenth.nft.convention.Web3Properties;
import com.tpulse.gs.convention.dao.id.service.GsCollectionIdService;
import com.tpulse.gs.oss.IGsOssService;
import com.tpulse.gs.oss.qiniu.QiniuProperties;
import com.tpulse.gs.oss.vo.OSSToken;
import com.tpulse.gs.oss.vo.OSSTokenCreateOption;
import com.tpulse.gs.oss.vo.OSSTokenType;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 10:06
 */
@Service
public class NftOSSService {

    @Autowired
    private IGsOssService gsOssService;
    @Autowired
    private QiniuProperties qiniuProperties;
    @Autowired
    private GsCollectionIdService gsCollectionIdService;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private Web3Properties web3Properties;

    public OSSToken getUploadToken() {
        return gsOssService.token(OSSTokenCreateOption.newBuilder()
                .bucket(qiniuProperties.getDefaultBucket())
                .keyPrefix("tmp/")
                .type(OSSTokenType.FILE)
                .build()
        );
    }

}
