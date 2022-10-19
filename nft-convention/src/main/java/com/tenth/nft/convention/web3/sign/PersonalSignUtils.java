package com.tenth.nft.convention.web3.sign;

import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.beans.Transient;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author shijie
 */
public class PersonalSignUtils {

    public static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";

    /**
     * Recover address from signature and message
     * @param signature
     * @return address with lowerCase
     */
    public static String recover(String message, String signature, String expectAddress){

        expectAddress = expectAddress.toLowerCase();

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
                addressRecovered = addressRecovered.toLowerCase();
//                return addressRecovered;
                if (addressRecovered.equals(expectAddress)) {
                    match = true;
                    return addressRecovered;
                }
            }
        }

        throw new RuntimeException("recover exception");

    }
}
