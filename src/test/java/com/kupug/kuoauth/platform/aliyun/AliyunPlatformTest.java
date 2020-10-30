package com.kupug.kuoauth.platform.aliyun;

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

public class AliyunPlatformTest {

    private AliyunPlatform platform;

    @Before
    public void init() {

        KuOAuthConfig kuOAuthConfig = KuOAuthConfig.builder()
                .clientId(OAuthConfigTest.ALIYUN_CLIENTID)
                .clientSecret(OAuthConfigTest.ALIYUN_CLIENTSECRET)
                .redirectUri(OAuthConfigTest.ALIYUN_REDIRECTURI)
                .build();

        platform = new AliyunPlatform(kuOAuthConfig);
    }

    @Test
    public void authorize() {
        String authorizeUrl = platform.authorize(OAuthUtils.randomState());

        System.out.println(authorizeUrl);
    }

    @Test
    public void accessToken() {

        KuOAuthCallback oAuthCallback = KuOAuthCallback.buider()
                .code("TeQwhxP61Tldf1cKNf92RQikfbw7t2ON")
                .state("1d6e299a9552ce4dee384e35c2d30009")
                .build();

        KuOAuthToken oAuthToken = platform.getAccessToken(oAuthCallback);

        System.out.println(JsonUtils.toJSONString(oAuthToken));
    }

    @Test
    public void userInfo() {

        KuOAuthToken oAuthToken = KuOAuthToken.builder()
                .accessToken("eyJhbGciOiJSUzI1NiIsImsyaWQiOiJlNE92NnVOUDhsMEY2RmVUMVhvek5wb1NBcVZLblNGRyIsImtpZCI6IkpDOXd4enJocUowZ3RhQ0V0MlFMVWZldkVVSXdsdEZodWk0TzFiaDY3dFUifQ.c1RWc0huOERLUzlRTUNrWFRMdUZwMldzNW45cFBOSWdNelV1QittVmVpai9Vc1VoMFJEYTZULzl3RDdkbzhHQkorMzNvN0gwRi9CSDYyalJlQ0hJVXdka1AzT1NYanBuK2N6UkN1SnRmNG1ZeDZ2czJsWFZmS3dacUI2cUtSQnVkcVpHeTY1RTU3VGNwS1RNQWU2ZldvWmNsUzU0SDdZMkcwVXFRZ09iTDVLUXROTDRxRWtuUzkrSEVZOHpMRXNiV3BsS1lQV202Z1Vhd3lXTy9tOWN3TFhSU0tXWXg2QWRWNnlSOFdHMXlpS2M1dW89.VDrbZ7omu7mFe1Qg25QDbHLooGneyOf6bREMnfZDezCJkaQWU4e-UBPt0GGy_bbvSS6Y5qiqpqPvtOjYBf581L9WWRmzOyvnQU5EQj1A0OUgJVhgvyEIvH3tuvnKAg_6jWgNqpHanka2jrOQ0m32jlqmUbXfAZ2Kl_UP77G3jzGE2mRY7uwVKQuJ3LVqOayYkpPGl5HjgHD34tigi8oSXK1B_hTfUzieHgCfG1_qOXOCMcNzE9-pcks2Xn4GAlZZf24TSRJhseWtLfb-KtdaH15cC9tclFoOfcC62gy46jX9UM4Y5HA93X7l7O1khx2wpZc8aX9CVrVFE4YpKMD5vQ")
                .refreshToken("V1ZTGN7ZmsYwT3td")
                .expiresIn(10799)
                .build();

        KuOAuthUser oAuthUser = platform.getUserInfo(oAuthToken);

        System.out.println(JsonUtils.toJSONString(oAuthUser));
    }

    @Test
    public void refresh() {

        KuOAuthToken oAuthToken = KuOAuthToken.builder()
                .accessToken("eyJhbGciOiJSUzI1NiIsImsyaWQiOiJlNE92NnVOUDhsMEY2RmVUMVhvek5wb1NBcVZLblNGRyIsImtpZCI6IkpDOXd4enJocUowZ3RhQ0V0MlFMVWZldkVVSXdsdEZodWk0TzFiaDY3dFUifQ.c1RWc0huOERLUzlRTUNrWFRMdUZwMldzNW45cFBOSWdNelV1QittVmVpai9Vc1VoMFJEYTZULzl3RDdkbzhHQkorMzNvN0gwRi9CSDYyalJlQ0hJVXdka1AzT1NYanBuK2N6UkN1SnRmNG1ZeDZ2czJsWFZmS3dacUI2cUtSQnVkcVpHeTY1RTU3VGNwS1RNQWU2ZldvWmNsUzU0SDdZMkcwVXFRZ09iTDVLUXROTDRxRWtuUzkrSEVZOHpMRXNiV3BsS1lQV202Z1Vhd3lXTy9tOWN3TFhSU0tXWXg2QWRWNnlSOFdHMXlpS2M1dW89.VDrbZ7omu7mFe1Qg25QDbHLooGneyOf6bREMnfZDezCJkaQWU4e-UBPt0GGy_bbvSS6Y5qiqpqPvtOjYBf581L9WWRmzOyvnQU5EQj1A0OUgJVhgvyEIvH3tuvnKAg_6jWgNqpHanka2jrOQ0m32jlqmUbXfAZ2Kl_UP77G3jzGE2mRY7uwVKQuJ3LVqOayYkpPGl5HjgHD34tigi8oSXK1B_hTfUzieHgCfG1_qOXOCMcNzE9-pcks2Xn4GAlZZf24TSRJhseWtLfb-KtdaH15cC9tclFoOfcC62gy46jX9UM4Y5HA93X7l7O1khx2wpZc8aX9CVrVFE4YpKMD5vQ")
                .refreshToken("V1ZTGN7ZmsYwT3td")
                .expiresIn(10799)
                .build();

        oAuthToken = platform.refresh(oAuthToken);

        System.out.println(JsonUtils.toJSONString(oAuthToken));
    }

    @Test
    public void revoke() {

        KuOAuthToken oAuthToken = KuOAuthToken.builder()
                .accessToken("1eyJhbGciOiJSUzI1NiIsImsyaWQiOiJlNE92NnVOUDhsMEY2RmVUMVhvek5wb1NBcVZLblNGRyIsImtpZCI6IkpDOXd4enJocUowZ3RhQ0V0MlFMVWZldkVVSXdsdEZodWk0TzFiaDY3dFUifQ.RTdMS2s4d2ltNEZMUU1oc05lVjVNMldzNW45cFBOSWdNelV1QittVmVpai9Vc1VoMFJEYTZULzl3RDdkbzhHQkorMzNvN0gwRi9CSDYyalJlQ0hJVXdka1AzT1NYanBuK2N6UkN1SnRmNG1ZeDZ2czJsWFZmS3dacUI2cUtSQnVkcVpHeTY1RTU3VGNwS1RNQWU2ZldvWmNsUzU0SDdZMkcwVXFRZ09iTDVLUXROTDRxVWtsUTkrSEVZOHpMRXNiV3BsS1lQV202Z1Vhd3lXTy9tOWN3TFhSU0tXWXg2QWRWNnlSOFdHMXl5S2U3dW89.bMxVxdsk-_T0z3eRRDHvbW23H2y1uirtfFLUOdWCfNEQ1MKsC4WmFKB18Bhj0Qef492oR26JQsXapLZjXtoJ6rWFguiSADBo35ah1UM49xyG6uVodu529Xa9RJHeQuJxQ_oELp3JtIL8heNSzu_c8ytWEkTS8FG5it3-Dz9nX4NYxu2wawlxOCzBzNPIWuyMVb9WE_eQpoQb9GAgVKOmVafpS6D5KDGQ2AbCSjT1p5bLi7W5Nj_r7Wrp93dlpGmXblTOe-nqP5jlNPMofHidnhDCu7XzljthNzkh757cTSgjCAqaAFQqkUlMN-JqXYF_kIux3AmFvZB8g1s2XJr2Tg")
                .expiresIn(10799)
                .build();

        boolean isOK = platform.revoke(oAuthToken);

        System.out.println(isOK);
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