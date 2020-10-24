package com.kupug.kuoauth.platform.gitee;

import com.kupug.kuoauth.KuOAuthConfig;
import com.kupug.kuoauth.OAuthConfigTest;
import com.kupug.kuoauth.KuOAuthCallback;
import com.kupug.kuoauth.KuOAuthLogin;
import com.kupug.kuoauth.KuOAuthToken;
import com.kupug.kuoauth.KuOAuthUser;
import com.kupug.kuoauth.utils.JsonUtils;
import com.kupug.kuoauth.utils.OAuthUtils;
import org.junit.Before;
import org.junit.Test;

public class GiteePlatformTest {

    private GiteePlatform platform;

    @Before
    public void init() {

        KuOAuthConfig kuOAuthConfig = KuOAuthConfig.builder()
                .clientId(OAuthConfigTest.GITEE_CLIENTID)
                .clientSecret(OAuthConfigTest.GITEE_CLIENTSECRET)
                .redirectUri(OAuthConfigTest.GITEE_REDIRECTURI)
                .build();

        platform = new GiteePlatform(kuOAuthConfig);
    }

    @Test
    public void authorize() {
        String authorizeUrl = platform.authorize(OAuthUtils.randomState());

        System.out.println(authorizeUrl);
    }

    @Test
    public void accessToken() {

        KuOAuthCallback oAuthCallback = KuOAuthCallback.buider()
                .code("1db08cd42636b272f246bfdc1c26d402c9cf96e17fa75dfe16e3878cca771b65")
                .state("a1ce0cc847c43c66896ff74b0850f563")
                .build();

        KuOAuthToken oAuthToken = platform.getAccessToken(oAuthCallback);

        System.out.println(JsonUtils.toJSONString(oAuthToken));
    }

    @Test
    public void userInfo() {

        KuOAuthToken oAuthToken = KuOAuthToken.builder()
                .accessToken("ce0fd0692c3b80037f804bb140a440ff")
                .refreshToken("de338764e46b90ba184787cfdba8d6efe52132072924c6bd9f44188ad81d929b")
                .expiresIn(86400)
                .build();

        KuOAuthUser oAuthUser = platform.getUserInfo(oAuthToken);

        System.out.println(JsonUtils.toJSONString(oAuthUser));
    }

    @Test
    public void login() {

        KuOAuthCallback oAuthCallback = KuOAuthCallback.buider()
                .code("b8ede40bb13a60c470ec5a5eea949c457aa0fd7f71b81cf230fe43ea7d9bef49")
                .state("95e7ab5eb20186211b2fec71851291c3")
                .build();

        KuOAuthLogin oAuthLogin = platform.login(oAuthCallback);

        System.out.println(JsonUtils.toJSONString(oAuthLogin));
    }
}