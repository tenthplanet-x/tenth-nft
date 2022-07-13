package com.tenth.nft.crawler.sdk.opensea;

import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.crawler.sdk.opensea.dto.OpenseaCollectionDTO;
import com.tenth.nft.crawler.sdk.opensea.dto.OpenseaConstractDTO;
import com.tenth.nft.crawler.sdk.opensea.dto.OpenseaGetCollectionResponse;
import com.tenth.nft.crawler.sdk.opensea.dto.OpenseaItemDTO;
import com.wallan.json.JsonUtil;
import com.wallan.router.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shijie
 * @createdAt 2022/6/14 14:22
 */
@Service
public class OpenseaSdk {

    private Logger LOGGER = LoggerFactory.getLogger(OpenseaSdk.class);

    private final int connectTimeout = 10000;

    public OpenseaCollectionDTO getByMarketplaceId(String marketplaceId){
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.of(connectTimeout, ChronoUnit.MILLIS))
                .build();
        String url = String.format("https://api.opensea.io/api/v1/collection/%s", marketplaceId);
        long startedAt = System.currentTimeMillis();
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String data = response.body();
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("""
                        request: {},
                        response: {}
                        """, url, data);
            }
            OpenseaGetCollectionResponse openseaGetCollectionResponse = JsonUtil.fromJson(data, OpenseaGetCollectionResponse.class);
            return openseaGetCollectionResponse.getCollection();
        }catch (Exception e){
            LOGGER.error(String.format("scan url: %s get exception", url), e);
            throw BizException.newInstance(NftExchangeErrorCodes.OPEANSEASDK_EXCEPTION);
        }finally {
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("url: {} cost: {} ms ", url, System.currentTimeMillis() - startedAt);
            }
        }
    }

    public OpenseaConstractDTO getByConstract(String contractAddress) {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.of(connectTimeout, ChronoUnit.MILLIS))
                .build();
        String url = String.format("https://api.opensea.io/api/v1/asset_contract/%s", contractAddress);
        long startedAt = System.currentTimeMillis();
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String data = response.body();
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("""
                        request: {},
                        response: {}
                        """, url, data);
            }
            return JsonUtil.fromJson(data, OpenseaConstractDTO.class);
        }catch (Exception e){
            LOGGER.error(String.format("scan url: %s get exception", url), e);
            throw BizException.newInstance(NftExchangeErrorCodes.OPEANSEASDK_EXCEPTION);
        }finally {
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("url: {} cost: {} ms ", url, System.currentTimeMillis() - startedAt);
            }
        }
    }


    public List<OpenseaItemDTO> getItemsByConstract(String contractAddress, int fromTokenId, int limit) {

        List<OpenseaItemDTO> result = new ArrayList<>();

        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.of(connectTimeout, ChronoUnit.MILLIS)).build();
        for(int i = 0; i < limit; i++){
            String tokenId = String.valueOf(fromTokenId + i);
            String url = String.format("https://api.opensea.io/api/v1/asset/%s/%s/?include_orders=false", contractAddress, tokenId);
            long startedAt = System.currentTimeMillis();
            try{
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String data = response.body();
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("""
                        request: {},
                        response: {}
                        """, url, data);
                }
                OpenseaItemDTO itemDTO = JsonUtil.fromJson(data, OpenseaItemDTO.class);
                if(!itemDTO.isSuccess()) break;
                itemDTO.setTokenId(tokenId);
                result.add(itemDTO);
            }catch (Exception e){
                LOGGER.error(String.format("scan url: %s get exception", url), e);
                throw BizException.newInstance(NftExchangeErrorCodes.OPEANSEASDK_EXCEPTION);
            }finally {
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("url: {} cost: {} ms ", url, System.currentTimeMillis() - startedAt);
                }
            }
        }

        return result;

    }
}
