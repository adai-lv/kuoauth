package com.kupug.kuoauth.platform.facebook;

import com.fasterxml.jackson.databind.JsonNode;
import com.kupug.kuoauth.KuOAuthCallback;
import com.kupug.kuoauth.KuOAuthConfig;
import com.kupug.kuoauth.KuOAuthException;
import com.kupug.kuoauth.KuOAuthToken;
import com.kupug.kuoauth.KuOAuthUser;
import com.kupug.kuoauth.platform.OAuthPlatform;
import com.kupug.kuoauth.utils.HttpClient;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * Facebook 平台授权
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
public final class FacebookPlatform extends OAuthPlatform {

    public FacebookPlatform(KuOAuthConfig config) {
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

        checkResponse(responseBody);

        OAuthToken oAuthToken = JsonUtils.parseObject(responseBody, OAuthToken.class);

        return oAuthToken.valueOf();
    }

    @Override
    protected KuOAuthUser getUserInfo(KuOAuthToken authToken) {

        String responseBody = HttpClient.builder()
                .fromUrl(oAuthApi.userInfo())
                .queryParam("access_token", authToken.getAccessToken())
                .queryParam("fields", "id,name,birthday,gender,hometown,email,devices,picture.width(400),link")
                .get();

        checkResponse(responseBody);

        OAuthUser oAuthUser = JsonUtils.parseObject(responseBody, OAuthUser.class);

        return oAuthUser.valueOf();
    }

    /**
     * 检查响应内容是否正确
     *
     * @param responseBody 请求响应内容
     */
    private void checkResponse(String responseBody) {
        if (responseBody.contains("error")) {
            JsonNode object = JsonUtils.parseObject(responseBody);
            throw new KuOAuthException(object.get("error").get("message").asText());
        }
    }
}
