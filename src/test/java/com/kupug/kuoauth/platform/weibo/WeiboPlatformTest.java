package com.kupug.kuoauth.platform.weibo;

import com.kupug.kuoauth.model.KuOAuthCallback;
import com.kupug.kuoauth.model.KuOAuthConfig;
import com.kupug.kuoauth.model.KuOAuthLogin;
import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.OAuthConfigTest;
import com.kupug.kuoauth.platform.PlatformFactory;
import com.kupug.kuoauth.model.Platform;
import com.kupug.kuoauth.utils.JsonUtils;
import com.kupug.kuoauth.utils.OAuthUtils;
import org.junit.Before;
import org.junit.Test;

public class WeiboPlatformTest {

    private WeiboPlatform platform;

    @Before
    public void init() {

        KuOAuthConfig kuOAuthConfig = KuOAuthConfig.builder()
                .clientId(OAuthConfigTest.WEIBO_CLIENTID)
                .clientSecret(OAuthConfigTest.WEIBO_CLIENTSECRET)
                .redirectUri(OAuthConfigTest.WEIBO_REDIRECTURI)
                .build();

        platform = (WeiboPlatform) PlatformFactory.newInstance(Platform.WEIBO, kuOAuthConfig);
    }

    @Test
    public void authorize() {
        String authorizeUrl = platform.authorize(OAuthUtils.randomState());
        System.out.println(authorizeUrl);
    }

    @Test
    public void accessToken() {

        KuOAuthCallback oAuthCallback = KuOAuthCallback.buider()
                .code("e6a8601dac520277164d1904b260333a")
                .state("97aca446bdee2613429930ffe7efff29")
                .build();

        KuOAuthToken oAuthToken = platform.getAccessToken(oAuthCallback);

        System.out.println(JsonUtils.toJSONString(oAuthToken));
    }

    @Test
    public void userInfo() {

        KuOAuthToken oAuthToken = KuOAuthToken.builder()
                .accessToken("2.00Lr7qHHY1XC_C735e4d1c9b0fvt4u")
                .openId("6528860473")
                .expiresIn(157679999)
                .build();

        KuOAuthUser oAuthUser = platform.getUserInfo(oAuthToken);

        System.out.println(JsonUtils.toJSONString(oAuthUser));
    }

    @Test
    public void revoke() {

        KuOAuthToken oAuthToken = KuOAuthToken.builder()
                .accessToken("2.00Lr7qHHY1XC_C735e4d1c9b0fvt4u")
                .openId("6528860473")
                .expiresIn(157679999)
                .build();

        boolean isOK = platform.revoke(oAuthToken);

        System.out.println(isOK);
    }

    @Test
    public void login() {

        KuOAuthCallback oAuthCallback = KuOAuthCallback.buider()
                .code("13BF09BEC1782635923D12DE6F983435")
                .state("a526e306d200e74193088b17bbd38830")
                .build();

        KuOAuthLogin oAuthLogin = platform.login(oAuthCallback);

        System.out.println(JsonUtils.toJSONString(oAuthLogin));
    }
}