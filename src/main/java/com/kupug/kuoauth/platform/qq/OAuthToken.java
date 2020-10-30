package com.kupug.kuoauth.platform.qq;

import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.utils.JsonUtils;

import java.util.Map;

/**
 * <p>
 * QQ oauth token
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
final class OAuthToken {

    private String accessToken;
    private String refreshToken;
    private Integer expiresIn;

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

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "QqOAuthToken{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
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
                .expiresIn(this.getExpiresIn())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }

    public static KuOAuthToken valueOf(Map<String, String> values) {

        int expiresIn = Integer.parseInt(values.getOrDefault("expires_in", "0"));

        OAuthToken token = new OAuthToken();
        token.setAccessToken(values.get("access_token"));
        token.setRefreshToken(values.get("refresh_token"));
        token.setExpiresIn(expiresIn);

        return token.valueOf();
    }
}
