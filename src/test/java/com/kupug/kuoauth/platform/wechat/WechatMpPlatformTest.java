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

public class WechatMpPlatformTest {

    private WechatMpPlatform platform;

    @Before
    public void init() {

        KuOAuthConfig kuOAuthConfig = KuOAuthConfig.builder()
                .clientId(OAuthConfigTest.WECHATMP_CLIENTID)
                .clientSecret(OAuthConfigTest.WECHATMP_CLIENTSECRET)
                .redirectUri(OAuthConfigTest.WECHATMP_REDIRECTURI)
                .build();

        platform = (WechatMpPlatform) PlatformFactory.newInstance(Platform.WECHATMP, kuOAuthConfig);
    }

    @Test
    public void authorize() {
        String authorizeUrl = platform.authorize(OAuthUtils.randomState());
        System.out.println(authorizeUrl);
    }

    @Test
    public void accessToken() {

        KuOAuthCallback oAuthCallback = KuOAuthCallback.buider()
                .code("071wyh000MPdwK1BRC200aUi7F3wyh03")
                .state("75b88b03d72291275ca0e47dab139687")
                .build();

        KuOAuthToken oAuthToken = platform.getAccessToken(oAuthCallback);

        System.out.println(JsonUtils.toJSONString(oAuthToken));
    }

    @Test
    public void userInfo() {

        KuOAuthToken oAuthToken = KuOAuthToken.builder()
                .accessToken("38_Z1ZUlYjjMUQ7rNIbj53ynT8g6EARkC6GRy-BMw1u5KD0e49wI6N1IMcYFLpShMo5CwirPOh9QDOaV4w3e0RPtgG-PA93w87trTZn-O_DAKo")
                .refreshToken("38_Dxt_uAOJxMxTk1lzosVxWrLJgBRDy_te2fpkikzwi9jKFH7CQGXJzB7TX6YK6x5t1_Z-wR8_Sb5xoj8sHE7jnxWl6hmEeoWcGCy9hgdHNIA")
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