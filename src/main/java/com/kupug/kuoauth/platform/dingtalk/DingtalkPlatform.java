package com.kupug.kuoauth.platform.dingtalk;

import com.fasterxml.jackson.databind.JsonNode;
import com.kupug.kuoauth.model.Constants;
import com.kupug.kuoauth.model.KuOAuthCallback;
import com.kupug.kuoauth.model.KuOAuthConfig;
import com.kupug.kuoauth.KuOAuthException;
import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.platform.OAuthPlatform;
import com.kupug.kuoauth.utils.Base64Utils;
import com.kupug.kuoauth.KuHttpClient;
import com.kupug.kuoauth.utils.JsonUtils;
import com.kupug.kuoauth.utils.OAuthUtils;

/**
 * <p>
 * 钉钉授权平台，分扫码登录和账号密码登录
 * 目前只支持账号密码登录，扫码登录有异常（[40078]不存在的临时授权码）
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public final class DingtalkPlatform extends OAuthPlatform {

    public DingtalkPlatform(KuOAuthConfig config) {
        super(config);
        this.oAuthApi = OAuthApi.ACCOUNT;
        this.oAuthScopes = OAuthScope.values();
    }

    @Override
    public String authorize(String state) {

        return KuHttpClient.builder()
                .fromUrl(oAuthApi.authorize())
                .queryParam("response_type", "code")
                .queryParam("appid", config.getClientId())
                .queryParam("redirect_uri", config.getRedirectUri())
                .queryParam("scope", getOAuthScopes())
                .queryParam("state", getRealState(state))
                .build();
    }

    @Override
    protected KuOAuthToken getAccessToken(KuOAuthCallback authCallback) {
        return KuOAuthToken.builder().accessToken(authCallback.getCode()).build();
    }

    /**
     * 获取钉钉用户的个人资料
     *
     * @param authToken 通过{@link DingtalkPlatform#getAccessToken(KuOAuthCallback)}获取到的{@code authToken}
     * @return KuOAuthUser
     */
    @Override
    protected KuOAuthUser getUserInfo(KuOAuthToken authToken) {

        String responseBody = httpClientBuilder()
                .fromUrl(getUserInfoUrl())
                .queryParam("tmp_auth_code", authToken.getAccessToken())
                .requestBody(true)
                .post();

        JsonNode responseObject = JsonUtils.parseObject(responseBody);
        int errorCode = responseObject.get("errcode").asInt();
        if (errorCode != 0) {
            throw new KuOAuthException(String.format("[%d]%s",
                    errorCode, responseObject.get("errmsg").asText()));
        }

        if (!responseObject.has("user_info")) {
            throw new KuOAuthException("登录失败：" + responseBody);
        }

        JsonNode userInfo = responseObject.get("user_info");

        return OAuthUser.valueOf(userInfo);
    }

    /**
     * 构建 userInfo url
     *
     * @return userInfo url
     */
    private String getUserInfoUrl() {

        // 根据timestamp, appSecret计算签名值
        String timestamp = System.currentTimeMillis() + "";
        String urlEncodeSignature = generateSignature(config.getClientSecret(), timestamp);

        return KuHttpClient.builder()
                .fromUrl(oAuthApi.userInfo())
                .queryParam("signature", urlEncodeSignature)
                .queryParam("timestamp", timestamp)
                .queryParam("accessKey", config.getClientId())
                .build();
    }

    /**
     * 生成钉钉请求的Signature
     *
     * @param secretKey 平台应用的授权密钥
     * @param timestamp 时间戳
     * @return Signature
     */
    private String generateSignature(String secretKey, String timestamp) {

        byte[] signData = OAuthUtils.signature(
                secretKey.getBytes(Constants.ENCODE_CHARSET),
                timestamp.getBytes(Constants.ENCODE_CHARSET),
                Constants.HMAC_SHA_256);

        return Base64Utils.valueOf(signData, false);
    }
}
