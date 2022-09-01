package com.tenth.nft.convention.web3.sign;

import com.tpulse.gs.convention.cypher.utils.Base64Utils;
import com.wallan.json.JsonUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shijie
 */
public abstract class DataForSign {

    private static Object domainTypeDefine;
    static {

        domainTypeDefine = DataForSignTypeDefine.newBuilder()
                .add("name", "string")
                .add("version", "string")
                .add("chainId", "uint256")
                .add("verifyingContract", "address")
                .add("salt", "bytes32")
                .build();
    }

    private EIP712Domain domain;

    public static class EIP712Domain {
        private String name;
        private String version;
        private Integer chainId;
        private String verifyingContract;
        private String salt;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Integer getChainId() {
            return chainId;
        }

        public void setChainId(Integer chainId) {
            this.chainId = chainId;
        }

        public String getVerifyingContract() {
            return verifyingContract;
        }

        public void setVerifyingContract(String verifyingContract) {
            this.verifyingContract = verifyingContract;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }
    }

    public EIP712Domain getDomain() {
        return domain;
    }

    public void setDomain(EIP712Domain domain) {
        this.domain = domain;
    }

    public Object toDataForSign(){

        Map<String, Object> output = new HashMap<>();

        Map<String, Object> types = new HashMap<>();
        types.put("EIP712Domain", domainTypeDefine);
        Map<String, Object> customTypes = getCustomTypes();
        types.putAll(customTypes);
        output.put("types", types);

        output.put("domain", domain);
        String primaryType = getPrimaryType();
        output.put("primaryType", primaryType);
        output.put("message", getMessage());

        return output;
    }

    public String toDataForSignBase64(){
        String json = JsonUtil.toJson(toDataForSign());
        return Base64Utils.encode(json.getBytes(StandardCharsets.UTF_8));
    }

    protected abstract Object getMessage();

    protected abstract String getPrimaryType();

    protected abstract Map<String, Object> getCustomTypes();

}
