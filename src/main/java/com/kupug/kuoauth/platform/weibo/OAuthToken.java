package com.kupug.kuoauth.platform.weibo;

import com.kupug.kuoauth.KuOAuthToken;
import com.kupug.kuoauth.platform.IOAuthToken;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * Weibo oauth token
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
final class OAuthToken implements IOAuthToken {

    private String accessToken;
    private Integer expiresIn;
    private String uid;
    private String remindIn;
    private String scope;
    private String isRealName;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRemindIn() {
        return remindIn;
    }

    public void setRemindIn(String remindIn) {
        this.remindIn = remindIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getIsRealName() {
        return isRealName;
    }

    public void setIsRealName(String isRealName) {
        this.isRealName = isRealName;
    }

    @Override
    public String toString() {
        return "OAuthToken{" +
                "accessToken='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", uid='" + uid + '\'' +
                ", remindIn='" + remindIn + '\'' +
                ", scope='" + scope + '\'' +
                ", isRealName='" + isRealName + '\'' +
                '}';
    }

    /**
     * 转化统一的 OAuth Token
     *
     * @return KuOAuthToken对象
     */
    @Override
    public KuOAuthToken valueOf() {
        return KuOAuthToken.builder()
                .accessToken(this.getAccessToken())
                .expiresIn(this.getExpiresIn())
                .openId(this.getUid())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }
}
