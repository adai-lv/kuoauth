package com.kupug.kuoauth.platform.google;

import com.kupug.kuoauth.model.KuOAuthCallback;
import com.kupug.kuoauth.model.KuOAuthConfig;
import com.kupug.kuoauth.model.KuOAuthLogin;
import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.OAuthConfigTest;
import com.kupug.kuoauth.utils.JsonUtils;
import com.kupug.kuoauth.utils.OAuthUtils;
import org.junit.Before;
import org.junit.Test;

public class GooglePlatformTest {

    private GooglePlatform platform;

    @Before
    public void init() {

        KuOAuthConfig kuOAuthConfig = KuOAuthConfig.builder()
                .clientId(OAuthConfigTest.GOOGLE_CLIENTID)
                .clientSecret(OAuthConfigTest.GOOGLE_CLIENTSECRET)
                .redirectUri(OAuthConfigTest.GOOGLE_REDIRECTURI)
                .build();

        platform = new GooglePlatform(kuOAuthConfig);
    }

    @Test
    public void authorize() {
        String authorizeUrl = platform.authorize(OAuthUtils.randomState());
        System.out.println(authorizeUrl);
    }

    @Test
    public void accessToken() {

        KuOAuthCallback oAuthCallback = KuOAuthCallback.buider()
                .code("4/0AfDhmrg0zb8k2h-q_z3Wh-sYKlwL3MAftkrAzAjskpcMeb91cp10yWqjgCOx0CnSvdV7EQ")
                .state("19c5f628af43e7577d2cea0c34280683")
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