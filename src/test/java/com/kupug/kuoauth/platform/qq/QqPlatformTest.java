package com.kupug.kuoauth.platform.qq;

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

public class QqPlatformTest {

    private QqPlatform platform;

    @Before
    public void init() {

        KuOAuthConfig kuOAuthConfig = KuOAuthConfig.builder()
                .clientId(OAuthConfigTest.QQ_CLIENTID)
                .clientSecret(OAuthConfigTest.QQ_CLIENTSECRET)
                .redirectUri(OAuthConfigTest.QQ_REDIRECTURI)
                .build();

        platform = (QqPlatform) PlatformFactory.newInstance(Platform.QQ, kuOAuthConfig);
    }

    @Test
    public void authorize() {
        String authorizeUrl = platform.authorize(OAuthUtils.randomState());
        System.out.println(authorizeUrl);
    }

    @Test
    public void accessToken() {

        KuOAuthCallback oAuthCallback = KuOAuthCallback.buider()
                .code("077CF4A168E31118593429504D05FA1B")
                .state("a738e8709f7a6fa989a0dc988d945f05")
                .build();

        KuOAuthToken oAuthToken = platform.getAccessToken(oAuthCallback);

        System.out.println(JsonUtils.toJSONString(oAuthToken));
    }

    @Test
    public void userInfo() {

        KuOAuthToken oAuthToken = KuOAuthToken.builder()
                .accessToken("49B75E5D9025C979E3F7420B9699820C")
                .refreshToken("FA441759CA17A9DF8FFE3DC9CFC41266")
                .expiresIn(7776000)
                .build();

        KuOAuthUser oAuthUser = platform.getUserInfo(oAuthToken);

        System.out.println(JsonUtils.toJSONString(oAuthUser));
    }

    @Test
    public void refresh() {

        KuOAuthToken oAuthToken = KuOAuthToken.builder()
                .accessToken("5156BF0AB58B6AE43C037806BD2CCEE5")
                .refreshToken("58547BC272A729A5AB1EC38C0C7442CD")
                .expiresIn(7776000)
                .build();

        oAuthToken = platform.refresh(oAuthToken);

        System.out.println(JsonUtils.toJSONString(oAuthToken));
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