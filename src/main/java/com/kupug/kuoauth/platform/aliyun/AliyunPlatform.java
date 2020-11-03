package com.kupug.kuoauth.platform.aliyun;

import com.fasterxml.jackson.databind.JsonNode;
import com.kupug.kuoauth.model.KuOAuthCallback;
import com.kupug.kuoauth.model.KuOAuthConfig;
import com.kupug.kuoauth.KuOAuthException;
import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.platform.OAuthPlatform;
import com.kupug.kuoauth.KuHttpClient;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * 阿里云授权平台
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
public final class AliyunPlatform extends OAuthPlatform {

    public AliyunPlatform(KuOAuthConfig config) {
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

        return KuHttpClient.builder()
                .fromUrl(oAuthApi.authorize())
                .queryParam("response_type", "code")
                .queryParam("access_type", "offline")
                .queryParam("client_id", config.getClientId())
                .queryParam("redirect_uri", config.getRedirectUri())
                .queryParam("scope", getOAuthScopes())
                .queryParam("state", getRealState(state))
                .build();
    }

    @Override
    public KuOAuthToken refresh(KuOAuthToken authToken) {

        String responseBody = httpClientBuilder()
                .fromUrl(oAuthApi.refresh())
                .queryParam("client_id", config.getClientId())
                .queryParam("client_secret", config.getClientSecret())
                .queryParam("refresh_token", authToken.getRefreshToken())
                .queryParam("grant_type", "refresh_token")
                .post();

        OAuthToken oAuthToken = JsonUtils.parseObject(responseBody, OAuthToken.class);

        return oAuthToken.valueOf();
    }

    @Override
    public boolean revoke(KuOAuthToken authToken) {

        String responseBody = httpClientBuilder()
                .fromUrl(oAuthApi.revoke())
                .queryParam("token", authToken.getAccessToken())
                .queryParam("client_id", config.getClientId())
                .post();

        JsonNode object = JsonUtils.parseObject(responseBody);
        boolean success = object.get("success").asBoolean();
        String messgae = object.get("message").asText();

        if (!success) {
            throw new KuOAuthException("撤销刷新令牌失败: " + messgae);
        }

        return success;
    }

    @Override
    protected KuOAuthToken getAccessToken(KuOAuthCallback authCallback) {

        String responseBody = httpClientBuilder()
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
        String responseBody = httpClientBuilder()
                .fromUrl(oAuthApi.userInfo())
                .queryParam("access_token", authToken.getAccessToken())
                .get();

        OAuthUser oAuthUser = JsonUtils.parseObject(responseBody, OAuthUser.class);

        return oAuthUser.valueOf();
    }
}
