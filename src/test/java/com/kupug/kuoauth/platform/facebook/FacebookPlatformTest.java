package com.kupug.kuoauth.platform.facebook;

import com.kupug.kuoauth.model.KuOAuthConfig;
import com.kupug.kuoauth.OAuthConfigTest;
import com.kupug.kuoauth.model.KuOAuthCallback;
import com.kupug.kuoauth.model.KuOAuthLogin;
import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.utils.JsonUtils;
import com.kupug.kuoauth.utils.OAuthUtils;
import org.junit.Before;
import org.junit.Test;

public class FacebookPlatformTest {

    private FacebookPlatform platform;

    @Before
    public void init() {

        KuOAuthConfig kuOAuthConfig = KuOAuthConfig.builder()
                .clientId(OAuthConfigTest.FACEBOOK_CLIENTID)
                .clientSecret(OAuthConfigTest.FACEBOOK_CLIENTSECRET)
                .redirectUri(OAuthConfigTest.FACEBOOK_REDIRECTURI)
                .build();

        platform = new FacebookPlatform(kuOAuthConfig);
    }

    @Test
    public void authorize() {
        String authorizeUrl = platform.authorize(OAuthUtils.randomState());
        System.out.println(authorizeUrl);
    }

    @Test
    public void accessToken() {

        KuOAuthCallback oAuthCallback = KuOAuthCallback.buider()
                .code("4/0AfDhmrjxK16Q_EddRUMj5Gb93iXozDDaPUEuQQnW_3IUvo_HN_QwplpmJJPxcPR_wriWxA")
                .state("b2cc9139ce1723ea8e0f12f27f56b0b3")
                .build();

        KuOAuthToken oAuthToken = platform.getAccessToken(oAuthCallback);

        System.out.println(JsonUtils.toJSONString(oAuthToken));
    }

    @Test
    public void userInfo() {

        KuOAuthToken oAuthToken = KuOAuthToken.builder()
                .accessToken("933fbfbd9975d6f33ef42bc4b0336e0e32664f2d3")
                .build();

        KuOAuthUser oAuthUser = platform.getUserInfo(oAuthToken);

        System.out.println(JsonUtils.toJSONString(oAuthUser));
    }

    @Test
    public void login() {

        KuOAuthCallback oAuthCallback = KuOAuthCallback.buider()
                .code("7cbbc5c765ec885f0d4a")
                .state("b73455ac3dfbc6f129e99a9b88068ad2")
                .build();

        KuOAuthLogin oAuthLogin = platform.login(oAuthCallback);

        System.out.println(JsonUtils.toJSONString(oAuthLogin));
    }
}