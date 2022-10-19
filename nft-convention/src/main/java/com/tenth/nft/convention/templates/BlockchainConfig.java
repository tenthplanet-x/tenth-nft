package com.tenth.nft.convention.templates;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.tenth.nft.convention.wallet.WalletPayChannel;

/**
 * @author shijie
 */
@JsonDeserialize(builder = BlockchainConfig.Builder.class)
public class BlockchainConfig {

    private String id;

    private String displayName;

    private String link;

    private WalletPayChannel channel;

    private String contractAddress;

    private String tokenStandard;

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLink() {
        return link;
    }

    public WalletPayChannel getChannel() {
        return channel;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public String getTokenStandard() {
        return tokenStandard;
    }


    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder{

        private BlockchainConfig config = new BlockchainConfig();

        public void setId(String id) {
            config.id = id;
        }

        public void setDisplayName(String displayName) {
            config.displayName = displayName;
        }

        public void setLink(String link) {
            config.link = link;
        }

        public void setChannel(WalletPayChannel channel) {
            config.channel = channel;
        }

        public void setContractAddress(String contractAddress) {
            config.contractAddress = contractAddress;
        }

        public void setTokenStandard(String tokenStandard) {
            config.tokenStandard = tokenStandard;
        }

        public BlockchainConfig build(){
            return config;
        }
    }
}
