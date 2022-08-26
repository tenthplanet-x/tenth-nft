package com.tenth.nft.convention;

/**
 * @author shijie
 */
public class Web3Properties {

    private String blockchain;

    private String network;

    private Rsa rsa;

    private Integer chainId;

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }

    public Rsa getRsa() {
        return rsa;
    }

    public void setRsa(Rsa rsa) {
        this.rsa = rsa;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }

    public static class Rsa {

        private String privateKey;

        private String publickey;

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getPublickey() {
            return publickey;
        }

        public void setPublickey(String publickey) {
            this.publickey = publickey;
        }
    }
}
