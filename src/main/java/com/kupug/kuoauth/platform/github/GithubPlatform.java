package com.kupug.kuoauth.platform.github;

import com.kupug.kuoauth.KuHttpClient;
import com.kupug.kuoauth.KuOAuthException;
import com.kupug.kuoauth.model.KuOAuthCallback;
import com.kupug.kuoauth.model.KuOAuthConfig;
import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.platform.OAuthPlatform;
import com.kupug.kuoauth.utils.JsonUtils;
import com.kupug.kuoauth.utils.StringUtils;
import com.kupug.kuoauth.utils.UrlUtils;

import java.util.Map;

/**
 * <p>
 * github 授权平台
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public final class GithubPlatform extends OAuthPlatform {

    public GithubPlatform(KuOAuthConfig config) {
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
                .queryParam("client_id", config.getClientId())
                .queryParam("redirect_uri", config.getRedirectUri())
                .queryParam("scope", getOAuthScopes())
                .queryParam("state", getRealState(state))
                .build();
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

        Map<String, String> responseMap = UrlUtils.valueOf(responseBody, true);

        if (responseMap.containsKey("error")) {
            throw new KuOAuthException(
                    responseMap.get("error") + ": " + responseMap.get("error_description"));
        }

        return OAuthToken.valueOf(responseMap);
    }

    @Override
    protected KuOAuthUser getUserInfo(KuOAuthToken authToken) {
        String responseBody = httpClientBuilder()
                .fromUrl(oAuthApi.userInfo())
                .addHeader("Authorization", "token " + authToken.getAccessToken())
                .get();

        OAuthUser oAuthUser = JsonUtils.parseObject(responseBody, OAuthUser.class);

        if (StringUtils.isNotEmpty(oAuthUser.getMessage())) {
            throw new KuOAuthException(oAuthUser.getMessage());
        }

        return oAuthUser.valueOf();
    }
}
