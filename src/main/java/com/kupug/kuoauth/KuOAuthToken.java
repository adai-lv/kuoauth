package com.kupug.kuoauth;

/**
 * <p>
 * oauth token
 * </p>
 *
 * @author MaoHai.Lv
 * @since 1.0
 */
public final class KuOAuthToken {

    /**
     * 访问授权码
     */
    private String accessToken;
    private String refreshToken;

    /**
     * token 有效期
     */
    private Integer expiresIn;

    /**
     * 第三方平台返回的原始 oauth token
     * Json String
     */
    private String rawInfo;

    /**
     * 用户第三方平台的唯一id。
     */
    private String openId;

    /**
     * 同一个第三方平台多个应用关联标识
     */
    private String unionId;

    private KuOAuthToken() {
    }

    private KuOAuthToken(Builder builder) {
        this.accessToken = builder.accessToken;
        this.refreshToken = builder.refreshToken;
        this.expiresIn = builder.expiresIn;
        this.rawInfo = builder.rawInfo;
        this.openId = builder.openId;
        this.unionId = builder.unionId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public String getRawInfo() {
        return rawInfo;
    }

    public String getOpenId() {
        return openId;
    }

    public String getUnionId() {
        return unionId;
    }

    @Override
    public String toString() {
        return "KuOAuthToken{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", rawInfo='" + rawInfo + '\'' +
                ", openId='" + openId + '\'' +
                ", unionId='" + unionId + '\'' +
                '}';
    }

    public static Builder builder() {
        return new KuOAuthToken.Builder();
    }

    public static Builder builder(KuOAuthToken authToken) {
        KuOAuthToken.Builder builder = new KuOAuthToken.Builder();

        builder.accessToken = authToken.accessToken;
        builder.refreshToken = authToken.refreshToken;
        builder.expiresIn = authToken.expiresIn;
        builder.rawInfo = authToken.rawInfo;
        builder.openId = authToken.openId;
        builder.unionId = authToken.unionId;

        return builder;
    }

    public final static class Builder {

        private String accessToken;
        private String refreshToken;
        private Integer expiresIn;
        private String rawInfo;
        private String openId;
        private String unionId;

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder expiresIn(Integer expiresIn) {
            this.expiresIn = expiresIn;
            return this;
        }

        public Builder rawInfo(String rawInfo) {
            this.rawInfo = rawInfo;
            return this;
        }

        public Builder openId(String openId) {
            this.openId = openId;
            return this;
        }

        public Builder unionId(String unionId) {
            this.unionId = unionId;
            return this;
        }

        public KuOAuthToken build() {
            return new KuOAuthToken(this);
        }
    }
}
