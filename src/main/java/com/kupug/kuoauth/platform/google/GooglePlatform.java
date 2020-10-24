package com.kupug.kuoauth.platform.google;

import com.kupug.kuoauth.KuOAuthCallback;
import com.kupug.kuoauth.KuOAuthConfig;
import com.kupug.kuoauth.KuOAuthException;
import com.kupug.kuoauth.KuOAuthToken;
import com.kupug.kuoauth.KuOAuthUser;
import com.kupug.kuoauth.platform.OAuthPlatform;
import com.kupug.kuoauth.utils.HttpClient;
import com.kupug.kuoauth.utils.JsonUtils;

import java.util.Objects;

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

        String responseBody = HttpClient.builder()
                .fromUrl(oAuthApi.accessToken())
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", authCallback.getCode())
                .queryParam("client_id", config.getClientId())
                .queryParam("client_secret", config.getClientSecret())
                .queryParam("redirect_uri", config.getRedirectUri())
                .post();

        OAuthToken oAuthToken = JsonUtils.parseObject(responseBody, OAuthToken.class);

        if (Objects.nonNull(oAuthToken.getError())) {
            throw new KuOAuthException(String.format("[%s]%s",
                    oAuthToken.getError(), oAuthToken.getErrorDescription()));
        }

        return oAuthToken.valueOf();
    }

    @Override
    protected KuOAuthUser getUserInfo(KuOAuthToken authToken) {

        String responseBody = HttpClient.builder()
                .fromUrl(oAuthApi.userInfo())
                .addHeader("Authorization", "Bearer " + authToken.getAccessToken())
                .queryParam("access_token", authToken.getAccessToken())
                .post();

        OAuthUser oAuthUser = JsonUtils.parseObject(responseBody, OAuthUser.class);

        if (Objects.nonNull(oAuthUser.getError())) {
            throw new KuOAuthException(String.format("[%s]%s",
                    oAuthUser.getError(), oAuthUser.getErrorDescription()));
        }

        return oAuthUser.valueOf();
    }
}
