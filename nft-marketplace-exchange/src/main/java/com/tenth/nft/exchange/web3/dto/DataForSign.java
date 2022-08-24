package com.tenth.nft.exchange.web3.dto;

/**
 * @author shijie
 */
public class DataForSign {

    private Domain domain;

    private String method;

    public static class Domain{
        private String name;
        private String version;
        private String chainId;
        private String verifyingContract;
        private String address;
        private String salt;
    }


    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
