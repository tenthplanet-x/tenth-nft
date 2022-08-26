package com.tenth.nft.utils;

import org.junit.Test;

/**
 * @author shijie
 */
public class WalletBridgeTest {

    public static final String GATEWAY = "http://172.16.16.108:9000/?cmd=%s&token=1";

    @Test
    public void cmd1(){

    }

    @Test
    public void cmd2(){

        String dataForSign = "eyJ0eXBlcyI6eyJMaXN0aW5nIjpbeyJuYW1lIjoic2VsbGVyIiwidHlwZSI6ImFkZHJlc3MifSx7Im5hbWUiOiJhc3NldHNJZCIsInR5cGUiOiJ1aW50MjU2In0seyJuYW1lIjoicXVhbnRpdHkiLCJ0eXBlIjoicXVhbGl0eSJ9XSwiRUlQNzEyRG9tYWluIjpbeyJuYW1lIjoibmFtZSIsInR5cGUiOiJzdHJpbmcifSx7Im5hbWUiOiJ2ZXJzaW9uIiwidHlwZSI6InN0cmluZyJ9LHsibmFtZSI6ImNoYWluSWQiLCJ0eXBlIjoidWludDI1NiJ9LHsibmFtZSI6InZlcmlmeWluZ0NvbnRyYWN0IiwidHlwZSI6ImFkZHJlc3MifSx7Im5hbWUiOiJzYWx0IiwidHlwZSI6ImJ5dGVzMzIifV19LCJwcmltYXJ5VHlwZSI6Ikxpc3RpbmciLCJkb21haW4iOnsibmFtZSI6IlRwdWxzZSIsInZlcnNpb24iOiIwLjAuMSIsImNoYWluSWQiOjQsInZlcmlmeWluZ0NvbnRyYWN0IjoiMHhhYzlkMWM2ZDhjMzMzZWFhYzMyY2E4YzJjMjViZTUxYTMzOGNkZTQ0Iiwic2FsdCI6IjB4MSJ9LCJtZXNzYWdlIjp7fX0=";
        System.out.println(
                String.format(GATEWAY, 2) + "&dataForSign=" + dataForSign
        );

    }

    @Test
    public void cmd3(){

        String txnTo = "0xac9d1c6d8c333eaac32ca8c2c25be51a338cde44";
        String txnData = "0x4d8bdd7c000000000000000000000000ab22314aa31e881070f3572313e88886af353daa000000000000000000000000000000000000000000000000000000000000b798000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000038d7ea4c680000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        String txnValue = "1000000000000000";

        String queryStringPattern = "&txnTo=%s&txnData=%s&txnValue=%s";
        String queryString = String.format(queryStringPattern, txnTo, txnData, txnValue);
        System.out.println(
                String.format(GATEWAY, 3) + queryString
        );

    }
}
