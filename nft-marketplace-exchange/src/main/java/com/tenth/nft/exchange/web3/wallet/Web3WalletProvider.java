package com.tenth.nft.exchange.web3.wallet;

import com.google.common.base.Joiner;
import com.tenth.nft.convention.wallet.WalletOrderBizContent;
import com.tenth.nft.convention.wallet.WalletPayChannel;
import com.tenth.nft.exchange.common.wallet.IWalletProvider;
import com.tpulse.gs.convention.cypher.rsa.RSAUtils;
import com.tpulse.gs.convention.cypher.utils.Base64Utils;
import com.wallan.json.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Component
public class Web3WalletProvider implements IWalletProvider {

    @Value("${web3.blockchain}")
    private String blockchain;
    @Value("${web3.rsa.private-key}")
    private String rsaPrivateKey;

    @Override
    public String createToken(WalletOrderBizContent bizContent) {

        String contentString = JsonUtil.toJson(bizContent);
        Map<String, Object> params = new TreeMap<>();
        params.putAll(JsonUtil.fromJson(contentString, Map.class));
        String queryString = Joiner.on("&").join(params.entrySet().stream().map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue())).collect(Collectors.toList()));
        String sign = null;
        try {
            sign = Base64Utils.encode(RSAUtils.sign(queryString.getBytes(StandardCharsets.UTF_8), rsaPrivateKey));
        }catch (Exception e){
            throw new RuntimeException("", e);
        }

        //from to value data
        String txn = null;

//        TpulseV2Contract.Listing listing = new TpulseV2Contract.Listing(
//                CONTRACT_SELLER,
//                BigInteger.valueOf(ITEM_ID),
//                BigInteger.valueOf(1),
//                BigInteger.valueOf(0),
//                new TpulseV2Contract.Signature(
//                        new byte[32],
//                        BigInteger.valueOf(1),
//                        new byte[32],
//                        new byte[32]
//                )
//        );
//        Function function = new Function(
//                "buy",  // function we're calling
//                Arrays.asList(listing),  // Parameters to pass as Solidity Types
//                Arrays.asList());
//        String encodedFunction = FunctionEncoder.encode(function);
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("content", contentString);
        tokenMap.put("txn", "");
        tokenMap.put("sign", sign);
        return Base64Utils.encode(JsonUtil.toJson(tokenMap).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String getChannel() {
        return WalletPayChannel.WEB3.name();
    }

    @Override
    public String getBlockchain() {
        return blockchain;
    }
}
