package com.kupug.kuoauth.platform.wechat;

import com.kupug.kuoauth.model.KuOAuthCallback;
import com.kupug.kuoauth.model.KuOAuthConfig;
import com.kupug.kuoauth.KuOAuthException;
import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.platform.OAuthPlatform;
import com.kupug.kuoauth.utils.HttpClient;
import com.kupug.kuoauth.utils.JsonUtils;
import com.kupug.kuoauth.utils.StringUtils;

/**
 * <p>
 * 微信开放平台授权
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
abstract class WechatPlatform extends OAuthPlatform {

    public WechatPlatform(KuOAuthConfig config) {
        super(config);
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
                .queryParam("appid", config.getClientId())
                .queryParam("redirect_uri", config.getRedirectUri())
                .queryParam("scope", getOAuthScopes())
                .queryParam("state", getRealState(state))
                .build();
    }

    @Override
    public KuOAuthToken refresh(KuOAuthToken authToken) {

        String responseBody = HttpClient.builder()
                .fromUrl(oAuthApi.refresh())
                .queryParam("grant_type", "refresh_token")
                .queryParam("appid", config.getClientId())
                .queryParam("refresh_token", authToken.getRefreshToken())
                .get();

        return parseOAuthToken(responseBody);
    }

    /**
     * 微信的特殊性，此时返回的信息同时包含 openid 和 access_token
     *
     * @param authCallback 回调返回的参数
     * @return KuOAuthToken
     */
    @Override
    protected KuOAuthToken getAccessToken(KuOAuthCallback authCallback) {

        String responseBody = HttpClient.builder()
                .fromUrl(oAuthApi.accessToken())
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", authCallback.getCode())
                .queryParam("appid", config.getClientId())
                .queryParam("secret", config.getClientSecret())
                .get();

        return parseOAuthToken(responseBody);
    }

    @Override
    protected KuOAuthUser getUserInfo(KuOAuthToken authToken) {

        String responseBody = HttpClient.builder()
                .fromUrl(oAuthApi.userInfo())
                .queryParam("access_token", authToken.getAccessToken())
                .queryParam("openid", authToken.getOpenId())
                .queryParam("lang", "zh_CN")
                .get();

        OAuthUser oAuthUser = JsonUtils.parseObject(responseBody, OAuthUser.class);

        if (StringUtils.isNotEmpty(oAuthUser.getErrcode())) {
            throw new KuOAuthException(String.format("[%s]%s",
                    oAuthUser.getErrcode(), oAuthUser.getErrmsg()));
        }

        return oAuthUser.valueOf();
    }

    private KuOAuthToken parseOAuthToken(String responseBody) {

        OAuthToken oAuthToken = JsonUtils.parseObject(responseBody, OAuthToken.class);

        if (StringUtils.isNotEmpty(oAuthToken.getErrcode())) {
            throw new KuOAuthException(String.format("[%s]%s",
                    oAuthToken.getErrcode(), oAuthToken.getErrmsg()));
        }

        return oAuthToken.valueOf();
    }
}
