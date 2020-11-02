package com.kupug.kuoauth.platform.google;

import com.kupug.kuoauth.model.KuOAuthCallback;
import com.kupug.kuoauth.model.KuOAuthConfig;
import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.platform.OAuthPlatform;
import com.kupug.kuoauth.utils.HttpClient;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * Google 平台授权
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
public final class GooglePlatform extends OAuthPlatform {

    public GooglePlatform(KuOAuthConfig config) {
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
                .queryParam("access_type", "offline")
                .queryParam("prompt", "select_account")
                .build();
    }

    @Override
    protected KuOAuthToken getAccessToken(KuOAuthCallback authCallback) {

        HttpClient.Builder builder = HttpClient.builder()
                .fromUrl(oAuthApi.accessToken())
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", authCallback.getCode())
                .queryParam("client_id", config.getClientId())
                .queryParam("client_secret", config.getClientSecret())
                .queryParam("redirect_uri", config.getRedirectUri());

        String responseBody = wrapHttpProxy(builder).post();

        OAuthToken oAuthToken = JsonUtils.parseObject(responseBody, OAuthToken.class);

        return oAuthToken.valueOf();
    }

    @Override
    protected KuOAuthUser getUserInfo(KuOAuthToken authToken) {
        HttpClient.Builder builder = HttpClient.builder()
                .fromUrl(oAuthApi.userInfo())
                .addHeader("Authorization", "Bearer " + authToken.getAccessToken())
                .queryParam("access_token", authToken.getAccessToken());

        String responseBody = wrapHttpProxy(builder).post();

        OAuthUser oAuthUser = JsonUtils.parseObject(responseBody, OAuthUser.class);

        return oAuthUser.valueOf();
    }
}
