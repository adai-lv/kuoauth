package com.kupug.kuoauth.platform.google;

import com.kupug.kuoauth.model.HttpConfig;
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

        HttpConfig httpConfig = HttpConfig.builder()
                .timeout(30000)
                .proxy("127.0.0.1", 1081)
                .build();

        KuOAuthConfig kuOAuthConfig = KuOAuthConfig.builder()
                .clientId(OAuthConfigTest.GOOGLE_CLIENTID)
                .clientSecret(OAuthConfigTest.GOOGLE_CLIENTSECRET)
                .redirectUri(OAuthConfigTest.GOOGLE_REDIRECTURI)
                .httpConfig(httpConfig)
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
                .code("4/0AfDhmrhUlfqGTgxGX_IbNxVoKUpwQGr84dILFrWFtiWGTOTF0-oscUZB_sLRbR2af7Zonw")
                .state("1494c60c57b986376c506863e6c9e803")
                .build();

        KuOAuthToken oAuthToken = platform.getAccessToken(oAuthCallback);

        System.out.println(JsonUtils.toJSONString(oAuthToken));
    }

    @Test
    public void userInfo() {

        KuOAuthToken oAuthToken = KuOAuthToken.builder()
                .accessToken("ya29.a0AfH6SMB87flrJRy4q4V1bG97NGtPBLsGe_gbf8b3ESjysqdsLov0-18MEW2JVRwiknHm1td4YkOhol4om7-HFtqW-VYVVlwOrUmqb1nyCbxqaZ4UYFipdooLHg3sq3wYkRGbnGRut3Jgg6frTZdN5t_bmvAKod8TpJk")
                .refreshToken("")
                .expiresIn(3599)
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