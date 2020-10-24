package com.kupug.kuoauth;

import com.kupug.kuoauth.utils.JsonUtils;
import org.junit.Test;

public class KuOAuthCallbackTest {

    @Test
    public void parseObjectOfClazz() {

        KuOAuthCallback oAuthToken;

        String jsonText = "{\"code\":\"code\",\"state\":\"fsfasfas\"}";
        oAuthToken = JsonUtils.parseObject(jsonText, KuOAuthCallback.class);
        System.out.println(JsonUtils.toJSONString(oAuthToken));

        String jsonText2 = "{\"auth_code\":\"auth code\",\"state\":\"fsfasfas\"}";
        oAuthToken = JsonUtils.parseObject(jsonText2, KuOAuthCallback.class);
        System.out.println(JsonUtils.toJSONString(oAuthToken));

        String jsonText3 = "{\"authorization_code\":\"authorization code\",\"state\":\"fsfasfas\"}";
        oAuthToken = JsonUtils.parseObject(jsonText3, KuOAuthCallback.class);
        System.out.println(JsonUtils.toJSONString(oAuthToken));
    }
}