package com.kupug.kuoauth.platform.oschina;

import com.kupug.kuoauth.KuHttpClient;
import com.kupug.kuoauth.KuOAuthException;
import com.kupug.kuoauth.model.KuOAuthCallback;
import com.kupug.kuoauth.model.KuOAuthConfig;
import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.platform.OAuthPlatform;
import com.kupug.kuoauth.utils.JsonUtils;
import com.kupug.kuoauth.utils.StringUtils;

/**
 * <p>
 * oschina 授权平台
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
public final class OschinaPlatform extends OAuthPlatform {

    public OschinaPlatform(KuOAuthConfig config) {
        super(config);
        this.oAuthApi = OAuthApi.DEFAULT;
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

        OAuthToken oAuthToken = JsonUtils.parseObject(responseBody, OAuthToken.class);

        if (StringUtils.isNotEmpty(oAuthToken.getError())) {
            throw new KuOAuthException(String.format("[%s]%s",
                    oAuthToken.getError(), oAuthToken.getErrorDescription()));
        }

        return oAuthToken.valueOf();
    }

    @Override
    protected KuOAuthUser getUserInfo(KuOAuthToken authToken) {
        String responseBody = httpClientBuilder()
                .fromUrl(oAuthApi.userInfo())
                .queryParam("access_token", authToken.getAccessToken())
                .get();

        OAuthUser oAuthUser = JsonUtils.parseObject(responseBody, OAuthUser.class);

        if (StringUtils.isNotEmpty(oAuthUser.getError())) {
            throw new KuOAuthException(String.format("[%s]%s",
                    oAuthUser.getError(), oAuthUser.getErrorDescription()));
        }

        return oAuthUser.valueOf();
    }
}
