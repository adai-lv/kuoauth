package com.kupug.kuoauth.platform.wechat;

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

public class WechatOpPlatformTest {

    private WechatOpPlatform platform;

    @Before
    public void init() {

        KuOAuthConfig kuOAuthConfig = KuOAuthConfig.builder()
                .clientId(OAuthConfigTest.WECHATOP_CLIENTID)
                .clientSecret(OAuthConfigTest.WECHATOP_CLIENTSECRET)
                .redirectUri(OAuthConfigTest.WECHATOP_REDIRECTURI)
                .build();

        platform = (WechatOpPlatform) PlatformFactory.newInstance(Platform.WECHAT, kuOAuthConfig);
    }

    @Test
    public void authorize() {
        String authorizeUrl = platform.authorize(OAuthUtils.randomState());
        System.out.println(authorizeUrl);
    }

    @Test
    public void accessToken() {

        KuOAuthCallback oAuthCallback = KuOAuthCallback.buider()
                .code("061n72000mOVvK1PsY300fUjUu3n720g")
                .state("5682dc1f61ffac5fabe44ecbdd7c47ea")
                .build();

        KuOAuthToken oAuthToken = platform.getAccessToken(oAuthCallback);

        System.out.println(JsonUtils.toJSONString(oAuthToken));
    }

    @Test
    public void userInfo() {

        KuOAuthToken oAuthToken = KuOAuthToken.builder()
                .accessToken("38_0Y72b2jwwEZfM1WePdRzvQKuJZrtJJojD3jdi-UNW5VI4Q1tljTd3rZopTC8vt7tn7Q0o6O9WQOiA5vrOFEk0I36cr9XdItnc2xZ0pXPptk")
                .refreshToken("38_vHJ3aSMEil2WKdth4D3o6jjawZR6r0nkf4Zyt9aW02XLxctLslhAhyZEskXUapVrMpnjXXA2b-c6jRWwq5VHYjThPzn-UAjSTkIswCbWL9c")
                .expiresIn(7200)
                .build();

        KuOAuthUser oAuthUser = platform.getUserInfo(oAuthToken);

        System.out.println(JsonUtils.toJSONString(oAuthUser));
    }

    @Test
    public void refresh() {

        KuOAuthToken oAuthToken = KuOAuthToken.builder()
                .accessToken("38_0Y72b2jwwEZfM1WePdRzvQKuJZrtJJojD3jdi-UNW5VI4Q1tljTd3rZopTC8vt7tn7Q0o6O9WQOiA5vrOFEk0I36cr9XdItnc2xZ0pXPptk")
                .refreshToken("38_vHJ3aSMEil2WKdth4D3o6jjawZR6r0nkf4Zyt9aW02XLxctLslhAhyZEskXUapVrMpnjXXA2b-c6jRWwq5VHYjThPzn-UAjSTkIswCbWL9c")
                .expiresIn(7200)
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