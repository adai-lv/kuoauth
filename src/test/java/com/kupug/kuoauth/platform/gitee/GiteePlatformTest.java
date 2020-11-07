package com.kupug.kuoauth.platform.gitee;

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
                .code("112c31656e18d881b54a17b648a05282a9b3253517b4b5ebf9c371c49979a890")
                .state("a1ce0cc847c43c66896ff74b0850f563")
                .build();

        KuOAuthToken oAuthToken = platform.getAccessToken(oAuthCallback);

        System.out.println(JsonUtils.toJSONString(oAuthToken));
    }

    @Test
    public void userInfo() {

        KuOAuthToken oAuthToken = KuOAuthToken.builder()
                .accessToken("8a0b41759e1356dea385fbd68cc5146f")
                .refreshToken("d3c45d6d95bdb1a4774605e754f1fbe50986658b261aa1bcce961fe5775a480c")
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