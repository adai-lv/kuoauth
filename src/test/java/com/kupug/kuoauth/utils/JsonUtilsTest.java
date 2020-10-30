package com.kupug.kuoauth.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.kupug.kuoauth.model.KuOAuthToken;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JsonUtilsTest {

    @Test
    public void mapToJSONString() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", 1);
        params.put("boy", true);
        params.put("email", "xxx@xxx.com");
        params.put("phone", "1234****345");
        params.put("phone2", null);

        System.out.println(JsonUtils.toJSONString(params));
    }

    @Test
    public void objectToJSONString() {
        KuOAuthToken oAuthToken = KuOAuthToken.builder().accessToken("xxx@xxx.com").build();
        System.out.println(JsonUtils.toJSONString(oAuthToken));
    }

    @Test
    public void parseObjectOfJsonNode() {

        String jsonText = "{\"phone\":\"1234****345\",\"id\":1,\"boy\":true,\"email\":\"xxx@xxx.com\"}";

        JsonNode jsonNode = JsonUtils.parseObject(jsonText);

        System.out.println(jsonNode.toString());
    }

    @Test
    public void parseObjectOfClazz() {

        String jsonText = "{\"accessToken\":\"1234****345\",\"expiresIn\":1000}";

        KuOAuthToken oAuthToken = JsonUtils.parseObject(jsonText, KuOAuthToken.class);

        System.out.println(JsonUtils.toJSONString(oAuthToken));
    }
}