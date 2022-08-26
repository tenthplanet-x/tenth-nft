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

        String dataForSign = "eyJ0eXBlcyI6eyJNaW50IjpbeyJuYW1lIjoibmFtZSIsInR5cGUiOiJzdHJpbmcifSx7Im5hbWUiOiJkZXNjIiwidHlwZSI6InN0cmluZyJ9LHsibmFtZSI6InN1cHBseSIsInR5cGUiOiJ1aW50MjU2In0seyJuYW1lIjoiY3JlYXRvckZlZVJhdGUiLCJ0eXBlIjoidWludDI1NiJ9LHsibmFtZSI6ImNyZWF0b3JGZWVSYXRlUHJlY2lzaW9uIiwidHlwZSI6InVpbnQ4In1dLCJFSVA3MTJEb21haW4iOlt7Im5hbWUiOiJuYW1lIiwidHlwZSI6InN0cmluZyJ9LHsibmFtZSI6InZlcnNpb24iLCJ0eXBlIjoic3RyaW5nIn0seyJuYW1lIjoiY2hhaW5JZCIsInR5cGUiOiJ1aW50MjU2In0seyJuYW1lIjoidmVyaWZ5aW5nQ29udHJhY3QiLCJ0eXBlIjoiYWRkcmVzcyJ9LHsibmFtZSI6InNhbHQiLCJ0eXBlIjoiYnl0ZXMzMiJ9XX0sInByaW1hcnlUeXBlIjoiTWludCIsImRvbWFpbiI6eyJuYW1lIjoiVHB1bHNlIiwidmVyc2lvbiI6IjAuMC4xIiwiY2hhaW5JZCI6NCwidmVyaWZ5aW5nQ29udHJhY3QiOiIweGFjOWQxYzZkOGMzMzNlYWFjMzJjYThjMmMyNWJlNTFhMzM4Y2RlNDQiLCJzYWx0IjoiMHgxIn0sIm1lc3NhZ2UiOnt9fQ==";
        System.out.println(
                String.format(GATEWAY, 2) + "&dataForSign=" + dataForSign
        );

    }

    @Test
    public void cmd3(){

    }
}
