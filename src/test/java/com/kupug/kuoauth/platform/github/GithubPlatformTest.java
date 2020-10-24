package com.kupug.kuoauth.platform.github;

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

public class GithubPlatformTest {

    private GithubPlatform platform;

    @Before
    public void init() {

        KuOAuthConfig kuOAuthConfig = KuOAuthConfig.builder()
                .clientId(OAuthConfigTest.GITHUB.getClientId())
                .clientSecret(OAuthConfigTest.GITHUB.getClientSecret())
                .redirectUri(OAuthConfigTest.GITHUB.getRedirectUri())
                .build();

        platform = new GithubPlatform(kuOAuthConfig);
    }

    @Test
    public void authorize() {
        String authorizeUrl = platform.authorize(OAuthUtils.randomState());
        System.out.println(authorizeUrl);
    }

    @Test
    public void accessToken() {

        KuOAuthCallback oAuthCallback = KuOAuthCallback.buider()
                .code("ef0532d8945c1ae0681d")
                .state("1e77f34a4f97f6487dff3f1e89bdb8d2")
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