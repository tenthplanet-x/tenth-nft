package com.tenth.nft.convention;

/**
 * @author shijie
 */
public class Web3Properties {

    private String blockchain;

    private String network;

    private Rsa rsa;

    private Integer chainId;

    private Contract contract;

    private String mainCurrency;

    private String wethCurrency;

    private String wethAddress;

    private String deepLinkGateway;

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

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getMainCurrency() {
        return mainCurrency;
    }

    public void setMainCurrency(String mainCurrency) {
        this.mainCurrency = mainCurrency;
    }

    public String getWethCurrency() {
        return wethCurrency;
    }

    public void setWethCurrency(String wethCurrency) {
        this.wethCurrency = wethCurrency;
    }

    public String getWethAddress() {
        return wethAddress;
    }

    public void setWethAddress(String wethAddress) {
        this.wethAddress = wethAddress;
    }

    public String getDeepLinkGateway() {
        return deepLinkGateway;
    }

    public void setDeepLinkGateway(String deepLinkGateway) {
        this.deepLinkGateway = deepLinkGateway;
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

    public static class Contract{

        private String address;

        private String ownerAddress;

        private String ownerPrivateKey;

        private String tokenStandard;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOwnerAddress() {
            return ownerAddress;
        }

        public void setOwnerAddress(String ownerAddress) {
            this.ownerAddress = ownerAddress;
        }

        public String getOwnerPrivateKey() {
            return ownerPrivateKey;
        }

        public void setOwnerPrivateKey(String ownerPrivateKey) {
            this.ownerPrivateKey = ownerPrivateKey;
        }

        public String getTokenStandard() {
            return tokenStandard;
        }

        public void setTokenStandard(String tokenStandard) {
            this.tokenStandard = tokenStandard;
        }
    }
}
