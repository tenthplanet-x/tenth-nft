package com.tenth.nft.wallet;

import com.tpulse.gs.convention.cypher.rsa.RSAUtils;
import com.tpulse.gs.convention.cypher.utils.Base64Utils;
import com.tpulse.gs.convention.cypher.utils.Pair;
import org.junit.Test;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author shijie
 */
public class RsaTest {

    @Test
    public void generate(){

        Pair<RSAPublicKey, RSAPrivateKey> pair = RSAUtils.genKeyPair(1);
        System.out.println(Base64Utils.encode(pair.key.getEncoded()));
        System.out.println(Base64Utils.encode(pair.value.getEncoded()));

    }
}
