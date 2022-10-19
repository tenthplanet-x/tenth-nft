package web3;

import com.tenth.nft.app.NftApplicationTest;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.web3.sign.PersonalSignUtils;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.tpulse.gs.convention.cypher.utils.Base64Utils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.Arrays;

/**
 * @author shijie
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = NftApplicationTest.class)
public class Web3SignTest {

    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    private TpulseContractHelper tpulseContractHelper;

    public static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";

    @Test
    public void message() throws Exception{
        String message = "helloworld";
        String prefix = PERSONAL_MESSAGE_PREFIX + message.length();
        String dataForSign = prefix + message;
        String msgHash = Hash.sha3String(dataForSign);
        System.out.println(msgHash);
    }

    @Test
    public void recover(){

        String signature =
                "0x2e3d0d23ef322f2c5b8beff28af9810ccbdb7a87d2562ec28773a7cf6a4cce7a69e46ca5b2300d5ad9081723960e8323184a9c75cdf59581760c7c1d0a9c6ba01c";

        String address = "0xcc98Cf60b156a28625Ef3669f69Abc18F8cebcdc";
        String message = "helloworld";

        String prefix = PERSONAL_MESSAGE_PREFIX + message.length();
        byte[] msgHash = Hash.sha3((prefix + message).getBytes());
        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }

        Sign.SignatureData sd =
                new Sign.SignatureData(
                        v,
                        (byte[]) Arrays.copyOfRange(signatureBytes, 0, 32),
                        (byte[]) Arrays.copyOfRange(signatureBytes, 32, 64));

        String addressRecovered = null;
        boolean match = false;

        // Iterate for each possible key to recover
        for (int i = 0; i < 4; i++) {
            BigInteger publicKey =
                    Sign.recoverFromSignature(
                            (byte) i,
                            new ECDSASignature(
                                    new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())),
                            msgHash);

            if (publicKey != null) {
                addressRecovered = "0x" + Keys.getAddress(publicKey);

                if (addressRecovered.equals(address)) {
                    match = true;
                    break;
                }
            }
        }

        String recovered = PersonalSignUtils.recover(message, signature, address);

        //System.out.println(addressRecovered);
        Assert.assertTrue(addressRecovered.toLowerCase().equals(address.toLowerCase()));
//        assertEquals(addressRecovered, (address));
//        assertTrue(match);
    }


}
