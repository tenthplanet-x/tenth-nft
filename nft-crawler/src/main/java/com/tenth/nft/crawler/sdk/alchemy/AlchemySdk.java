package com.tenth.nft.crawler.sdk.alchemy;

import com.tenth.nft.crawler.sdk.alchemy.dto.AlchemyNftDTO;
import com.tenth.nft.crawler.sdk.alchemy.dto.GetNFTsForCollectionResponse;
import com.wallan.json.JsonUtil;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class AlchemySdk {

    @Autowired
    private AlchemyProperties alchemyProperties;

    private final String GetNFTsForCollection_API = "https://eth-mainnet.alchemyapi.io/v2/%s/getNFTsForCollection/";

    public List<AlchemyNftDTO> getItemsByConstract(String contractAddress, int fromTokenId) throws Exception{

        String url = String.format(GetNFTsForCollection_API, alchemyProperties.getKey()) + String.format("?contractAddress=%s&withMetadata=true&startToken=%s", contractAddress, fromTokenId);

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        GetNFTsForCollectionResponse getNFTsForCollectionResponse = JsonUtil.fromJson(response.body(), GetNFTsForCollectionResponse.class);
        return getNFTsForCollectionResponse.getNfts();
    }
}
