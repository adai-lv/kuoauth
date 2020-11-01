package com.kupug.kuoauth.platform.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.kupug.kuoauth.model.Constants;
import com.kupug.kuoauth.model.KuOAuthCallback;
import com.kupug.kuoauth.model.KuOAuthConfig;
import com.kupug.kuoauth.KuOAuthException;
import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.platform.OAuthPlatform;
import com.kupug.kuoauth.utils.HttpClient;

/**
 * <p>
 * 支付宝授权平台
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
public final class AlipayPlatform extends OAuthPlatform {

    private AlipayClient alipayClient;

    public AlipayPlatform(KuOAuthConfig config) {
        super(config);
        this.oAuthApi = OAuthApi.DEFAULT;
        this.oAuthScopes = OAuthScope.values();

        this.alipayClient = generateAlipayClient();
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
                .queryParam("app_id", config.getClientId())
                .queryParam("redirect_uri", config.getRedirectUri())
                .queryParam("scope", getOAuthScopes())
                .queryParam("state", getRealState(state))
                .build();
    }

    @Override
    public KuOAuthToken refresh(KuOAuthToken authToken) {

        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("refresh_token");
        request.setCode(authToken.getRefreshToken());

        AlipaySystemOauthTokenResponse response;
        try {
            response = this.alipayClient.execute(request);
        } catch (Exception e) {
            throw new KuOAuthException("支付宝 refresh token 失败: " + e.getMessage(), e);
        }

        if (!response.isSuccess()) {
            throw new KuOAuthException("支付宝 refresh token 失败: " + response.getSubMsg());
        }

        return OAuthToken.valueOf(response);
    }

    @Override
    protected KuOAuthToken getAccessToken(KuOAuthCallback authCallback) {

        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("authorization_code");
        request.setCode(authCallback.getCode());

        AlipaySystemOauthTokenResponse response;
        try {
            response = this.alipayClient.execute(request);
        } catch (Exception e) {
            throw new KuOAuthException("支付宝获取access_token 失败: " + e.getMessage(), e);
        }

        if (!response.isSuccess()) {
            throw new KuOAuthException("支付宝获取access_token 失败: " + response.getSubMsg());
        }

        return OAuthToken.valueOf(response);
    }

    @Override
    protected KuOAuthUser getUserInfo(KuOAuthToken authToken) {

        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse response;

        try {
            response = this.alipayClient.execute(request, authToken.getAccessToken());
        } catch (AlipayApiException e) {
            throw new KuOAuthException("支付宝获取用户信息失败: " + e.getErrMsg(), e);
        }

        if (!response.isSuccess()) {
            throw new KuOAuthException("支付宝获取用户信息失败: " + response.getSubMsg());
        }

        return OAuthUser.valueOf(response);
    }

    private AlipayClient generateAlipayClient() {
        return new DefaultAlipayClient(
                oAuthApi.accessToken(),
                config.getClientId(),
                config.getClientSecret(),
                Constants.FORMAT_JSON,
                Constants.ENCODE_CHARSET_STRING,
                config.getAlipayPublicKey(),
                Constants.SING_TYPE_RSA2);
    }
}
