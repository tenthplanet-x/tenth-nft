package com.tenth.nft.utils;

import com.tpulse.gs.convention.cypher.utils.Base64Utils;
import com.wallan.json.JsonUtil;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shijie
 */
public class Base64UtilsTest {

    @Test
    public void encode(){

        Map<String, Object> dataForSign = new HashMap<>();
        dataForSign.put("a", "b");

        String encodeContent = Base64Utils.encode(JsonUtil.toJson(dataForSign).getBytes(StandardCharsets.UTF_8));
        System.out.println(encodeContent);

    }

    @Test
    public void decode(){
        String str = "eyJ0eXBlcyI6eyJMaXN0aW5nIjpbeyJuYW1lIjoiYXNzZXRzSWQiLCJ0eXBlIjoic3RyaW5nIn0seyJuYW1lIjoicXVhbnRpdHkiLCJ0eXBlIjoidWludDI1NiJ9LHsibmFtZSI6ImN1cnJlbmN5IiwidHlwZSI6InVpbnQyNTYifSx7Im5hbWUiOiJwcmljZSIsInR5cGUiOiJzdHJpbmcifV0sIkVJUDcxMkRvbWFpbiI6W3sibmFtZSI6Im5hbWUiLCJ0eXBlIjoic3RyaW5nIn0seyJuYW1lIjoidmVyc2lvbiIsInR5cGUiOiJzdHJpbmcifSx7Im5hbWUiOiJjaGFpbklkIiwidHlwZSI6InVpbnQyNTYifSx7Im5hbWUiOiJ2ZXJpZnlpbmdDb250cmFjdCIsInR5cGUiOiJhZGRyZXNzIn0seyJuYW1lIjoidmVyaWZ5aW5nQ29udHJhY3QiLCJ0eXBlIjoiYnl0ZXMzMiJ9XX0sInByaW1hcnlUeXBlIjoiTGlzdGluZyIsImRvbWFpbiI6eyJuYW1lIjoiVHB1bHNlIiwidmVyc2lvbiI6IjEiLCJjaGFpbklkIjo0LCJ2ZXJpZnlpbmdDb250cmFjdCI6IjB4OTgxMWQyNWMyZmI2OGUzNzY3NzM1ZTA3NjQ0Y2VkMzhhYTE4ZWMyYyIsInNhbHQiOiIweDEifSwibWVzc2FnZSI6eyJsaXN0aW5nIjp7InF1YW50aXR5IjoxLCJwcmljZSI6IjAuMDAxIiwiY3VycmVuY3kiOiJFVEgtRXRoZXJldW0iLCJhc3NldHNJZCI6NDYwMDB9fX0=";
        System.out.println(new String(Base64Utils.decode(str), StandardCharsets.UTF_8));
    }
}
