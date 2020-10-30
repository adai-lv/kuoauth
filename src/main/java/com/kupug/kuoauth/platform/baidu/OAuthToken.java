package com.kupug.kuoauth.platform.baidu;

import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * 百度平台 oauth token
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
final class OAuthToken {

    /**
     * 要获取的Access Token
     */
    private String accessToken;

    /**
     * 用于刷新Access Token 的 Refresh Token,所有应用都会返回该参数
     * 10年的有效期
     */
    private String refreshToken;

    /**
     * Access Token的有效期，以秒为单位；
     */
    private Integer expiresIn;

    /**
     * Access Token最终的访问范围，即用户实际授予的权限列表
     * 用户在授权页面时，有可能会取消掉某些请求的权限
     */
    private String scope;

    /**
     * 基于http调用Open API时所需要的Session Key，其有效期与Access Token一致；
     */
    private String sessionKey;

    /**
     * 基于http调用Open API时计算参数签名用的签名密钥。
     */
    private String sessionSecret;

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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSessionSecret() {
        return sessionSecret;
    }

    public void setSessionSecret(String sessionSecret) {
        this.sessionSecret = sessionSecret;
    }

    @Override
    public String toString() {
        return "OAuthToken{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", scope='" + scope + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", sessionSecret='" + sessionSecret + '\'' +
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

}
