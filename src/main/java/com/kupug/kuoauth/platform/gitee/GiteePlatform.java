package com.kupug.kuoauth.platform.gitee;

import com.kupug.kuoauth.KuOAuthCallback;
import com.kupug.kuoauth.KuOAuthConfig;
import com.kupug.kuoauth.KuOAuthToken;
import com.kupug.kuoauth.KuOAuthUser;
import com.kupug.kuoauth.platform.OAuthPlatform;
import com.kupug.kuoauth.platform.qq.OAuthScope;
import com.kupug.kuoauth.utils.HttpClient;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * gitee 授权平台
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public final class GiteePlatform extends OAuthPlatform {

    public GiteePlatform(KuOAuthConfig config) {
        super(config);
        this.oAuthApi = OAuthApi.DEFAULT;
        this.oAuthScopes = OAuthScope.values();
    }

    /**
     * 返回带{@code state}参数的授权url，授权回调时会带上这个{@code state}
     *
     * @param state state 验证授权流程的参数，可以防止csrf
     * @return 返回授权地址
     */
    @Override
    public String authorize(String state) {

        return HttpClient.builder()
                .fromUrl(oAuthApi.authorize())
                .queryParam("response_type", "code")
                .queryParam("client_id", config.getClientId())
                .queryParam("redirect_uri", config.getRedirectUri())
                .queryParam("scope", getOAuthScopes())
                .queryParam("state", getRealState(state))
                .build();
    }

    @Override
    protected KuOAuthToken getAccessToken(KuOAuthCallback authCallback) {

        String responseBody = HttpClient.builder()
                .fromUrl(oAuthApi.accessToken())
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", authCallback.getCode())
                .queryParam("client_id", config.getClientId())
                .queryParam("client_secret", config.getClientSecret())
                .queryParam("redirect_uri", config.getRedirectUri())
                .post();

        OAuthToken oAuthToken = JsonUtils.parseObject(responseBody, OAuthToken.class);

        return oAuthToken.valueOf();
    }

    @Override
    protected KuOAuthUser getUserInfo(KuOAuthToken authToken) {
        String responseBody = HttpClient.builder()
                .fromUrl(oAuthApi.userInfo())
                .queryParam("access_token", authToken.getAccessToken())
                .get();

        OAuthUser oAuthUser = JsonUtils.parseObject(responseBody, OAuthUser.class);

        return oAuthUser.valueOf();
    }
}
