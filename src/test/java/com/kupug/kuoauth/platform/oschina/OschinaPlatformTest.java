package com.kupug.kuoauth.platform.oschina;

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

public class OschinaPlatformTest {

    private OschinaPlatform platform;

    @Before
    public void init() {

        KuOAuthConfig kuOAuthConfig = KuOAuthConfig.builder()
                .clientId(OAuthConfigTest.OSCHINA_CLIENTID)
                .clientSecret(OAuthConfigTest.OSCHINA_CLIENTSECRET)
                .redirectUri(OAuthConfigTest.OSCHINA_REDIRECTURI)
                .build();

        platform = new OschinaPlatform(kuOAuthConfig);
    }

    @Test
    public void authorize() {
        String authorizeUrl = platform.authorize(OAuthUtils.randomState());

        System.out.println(authorizeUrl);
    }

    @Test
    public void accessToken() {

        KuOAuthCallback oAuthCallback = KuOAuthCallback.buider()
                .code("McSqzT")
                .state("409edd713ff75da5f8c3c8cafb37cecd")
                .build();

        KuOAuthToken oAuthToken = platform.getAccessToken(oAuthCallback);

        System.out.println(JsonUtils.toJSONString(oAuthToken));
    }

    @Test
    public void userInfo() {

        KuOAuthToken oAuthToken = KuOAuthToken.builder()
                .accessToken("cb10840e-12f3-4683-8ac5-62e107f3d98")
                .refreshToken("52134b29-65a7-4dd5-8a31-5afc8f4aafd0")
                .expiresIn(604687)
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