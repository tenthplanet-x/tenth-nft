package com.tenth.nft.crawler.sdk.alchemy;

import com.wallan.json.JsonUtil;
import org.junit.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shijie
 */
public class AlchemySdkTest {

    @Test
    public void getTransactionHistories() throws Exception{

        String GetNFTsForCollection_API = "https://eth-mainnet.alchemyapi.io/v2/%s";
        String accessKey = "8VJu1buSC44iko3GH8d6lrvxSnfBY1qE";

        String url = String.format(GetNFTsForCollection_API, accessKey);

        Map<String, Object> postData = new HashMap<>();
        postData.put("jsonrpc", "2.0");
        postData.put("id", "0");
        postData.put("method", "alchemy_getAssetTransfers");
        List<Map<String, Object>> filters = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("fromBlock", "0x0");
        params.put("category", new Object[]{"Internal"});
//        params.put("toAddress", "0xb47e3cd837ddf8e4c57f05d70ab865de6e193bbb");
        filters.add(params);
        postData.put("params", filters);

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofByteArray(JsonUtil.toJson(postData).getBytes(StandardCharsets.UTF_8)))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

    }
}
