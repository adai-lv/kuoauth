package com.kupug.kuoauth.platform.wechat;

import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * WeChat oauth token
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public final class OAuthToken {

    private String errcode;
    private String errmsg;

    private String accessToken;
    private String refreshToken;
    private Integer expiresIn;
    private String openid;
    private String unionid;
    private String scope;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "OAuthToken{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", openid='" + openid + '\'' +
                ", unionid='" + unionid + '\'' +
                ", scope='" + scope + '\'' +
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
                .openId(this.getOpenid())
                .unionId(this.getUnionid())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }
}
