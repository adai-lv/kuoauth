package com.kupug.kuoauth.platform.alipay;

import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * alipay oauth token
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
final class OAuthToken {

    private String accessToken;
    private String refreshToken;
    private Integer expireIn;
    private String userId;
    private String alipayUserId;
    private Integer reExpiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Integer expireIn) {
        this.expireIn = expireIn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAlipayUserId() {
        return alipayUserId;
    }

    public void setAlipayUserId(String alipayUserId) {
        this.alipayUserId = alipayUserId;
    }

    public Integer getReExpiresIn() {
        return reExpiresIn;
    }

    public void setReExpiresIn(Integer reExpiresIn) {
        this.reExpiresIn = reExpiresIn;
    }

    @Override
    public String toString() {
        return "OAuthToken{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expireIn=" + expireIn +
                ", userId='" + userId + '\'' +
                ", alipayUserId='" + alipayUserId + '\'' +
                ", reExpiresIn=" + reExpiresIn +
                '}';
    }

    /**
     * 转化统一的 OAuth Token
     *
     * @return KuOAuthToken对象
     */
    public KuOAuthToken valueOf() {
        return KuOAuthToken.builder()
                .accessToken(this.getAccessToken())
                .refreshToken(this.getRefreshToken())
                .expiresIn(this.getExpireIn())
                .openId(this.getUserId())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }

    public static KuOAuthToken valueOf(AlipaySystemOauthTokenResponse values) {

        OAuthToken token = new OAuthToken();
        token.setAccessToken(values.getAccessToken());
        token.setRefreshToken(values.getRefreshToken());
        token.setExpireIn(Integer.parseInt(values.getExpiresIn()));
        token.setReExpiresIn(Integer.parseInt(values.getReExpiresIn()));
        token.setUserId(values.getUserId());
        token.setAlipayUserId(values.getAlipayUserId());

        return token.valueOf();
    }

}
