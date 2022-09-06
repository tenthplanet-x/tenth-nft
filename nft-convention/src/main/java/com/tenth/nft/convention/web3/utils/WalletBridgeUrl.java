package com.tenth.nft.convention.web3.utils;

import com.google.common.base.Joiner;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.templates.BlockchainConfig;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;

import java.security.Signature;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
public class WalletBridgeUrl {

    public static Builder newBuilder(Web3Properties web3Properties) {
        return new Builder(web3Properties);
    }

    public static class Builder{

        private Web3Properties web3Properties;

        private Map<String, Object> params = new HashMap<>();

        public Builder(Web3Properties web3Properties) {
            this.web3Properties = web3Properties;
        }

        public Builder sign() {
            params.put("cmd", 2);
            return this;
        }

        public Builder put(String key, Object value) {
            params.put(key, value);
            return this;
        }

        public String build() {

            String gateway = web3Properties.getWalletBridgeGateway();
            String queryString = Joiner.on("&").join(params.entrySet().stream().map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue())).collect(Collectors.toList()));
            return String.format("%s?%s", gateway, queryString);
        }

        public Builder auth() {
            params.put("cmd", 1);
            return this;
        }

        public Builder transaction() {
            params.put("cmd", 3);
            return this;
        }
    }
}
