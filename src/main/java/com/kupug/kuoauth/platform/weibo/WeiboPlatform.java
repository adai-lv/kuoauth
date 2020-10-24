package com.kupug.kuoauth.platform.weibo;

import com.fasterxml.jackson.databind.JsonNode;
import com.kupug.kuoauth.KuOAuthCallback;
import com.kupug.kuoauth.KuOAuthConfig;
import com.kupug.kuoauth.KuOAuthToken;
import com.kupug.kuoauth.KuOAuthUser;
import com.kupug.kuoauth.platform.OAuthPlatform;
import com.kupug.kuoauth.utils.HttpClient;
import com.kupug.kuoauth.utils.JsonUtils;
import com.kupug.kuoauth.utils.UrlUtils;

/**
 * <p>
 * 微博平台授权
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public final class WeiboPlatform extends OAuthPlatform {

    public WeiboPlatform(KuOAuthConfig config) {
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
    public boolean revoke(KuOAuthToken authToken) {

        String responseBody = HttpClient.builder()
                .fromUrl(oAuthApi.revoke())
                .queryParam("access_token", authToken.getAccessToken())
                .get();

        JsonNode object = JsonUtils.parseObject(responseBody);

        if (object.has("result")) {
            return object.get("result").asBoolean();
        }

        return false;
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

        String uid = authToken.getOpenId();
        String accessToken = authToken.getAccessToken();

        String oauthParam = String.format("uid=%s&access_token=%s", uid, accessToken);

        String responseBody = HttpClient.builder()
                .fromUrl(oAuthApi.userInfo())
                .addHeader("Authorization", "OAuth2 " + oauthParam)
                .addHeader("API-RemoteIP", UrlUtils.getLocalIp())
                .queryParam("access_token", accessToken)
                .queryParam("uid", uid)
                .get();

        OAuthUser oAuthUser = JsonUtils.parseObject(responseBody, OAuthUser.class);

        return oAuthUser.valueOf();
    }
}
