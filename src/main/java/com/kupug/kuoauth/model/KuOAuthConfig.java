package com.kupug.kuoauth.model;

import java.util.List;

/**
 * <p>
 * OAuth 配置
 * </p>
 *
 * @author MaoHai.Lv
 * @since 1.0
 */
public final class KuOAuthConfig {

    /**
     * 客户端id：对应各平台的appKey
     */
    private String clientId;

    /**
     * 客户端Secret：对应各平台的appSecret
     */
    private String clientSecret;

    /**
     * 登录成功后的回调地址
     */
    private String redirectUri;

    /**
     * 忽略校验 {@code state} 参数，默认不开启。当 {@code ignoreCheckState} 为 {@code true} 时，
     * {@link } 将不会校验 {@code state} 的合法性。
     * <p>
     * 使用场景：当且仅当使用自实现 {@code state} 校验逻辑时开启
     * <p>
     * 以下场景使用方案仅作参考：
     * 1. 授权、登录为同端，并且全部使用 KuOAuth 实现时，该值建议设为 {@code false};
     * 2. 授权和登录为不同端实现时，比如前端页面拼装 {@code authorizeUrl}，并且前端自行对{@code state}进行校验，
     * 后端只负责使用{@code code}获取用户信息时，该值建议设为 {@code true};
     *
     * <strong>如非特殊需要，不建议开启这个配置</strong>
     * <p>
     */
    private Boolean ignoreCheckState;

    /**
     * 定义授权平台的 scope 内容
     */
    private List<String> scopes;

    /**
     * 是否需要申请 unionId，目前只针对qq登录
     * 如果个人开发者账号中申请了该权限，可以将该值置为true，在获取openId时就会同步获取unionId
     *
     * 注：qq授权登录时，获取unionId需要单独发送邮件申请权限。
     * 参考链接：http://wiki.connect.qq.com/unionid%E4%BB%8B%E7%BB%8D
     */
    private boolean unionId;

    /**
     * 支付宝公钥，会对支付宝授权平台
     * 该值可用对应“RSA2(SHA256)密钥”中的“支付宝公钥”
     */
    private String alipayPublicKey;

    public KuOAuthConfig(Builder builder) {
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.redirectUri = builder.redirectUri;
        this.ignoreCheckState = builder.ignoreCheckState;
        this.scopes = builder.scopes;
        this.unionId = builder.unionId;
        this.alipayPublicKey = builder.alipayPublicKey;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public Boolean isIgnoreCheckState() {
        return ignoreCheckState;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public Boolean isUnionId() {
        return unionId;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    @Override
    public String toString() {
        return "KuOAuthConfig{" +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", ignoreCheckState=" + ignoreCheckState +
                ", scopes=" + scopes +
                ", unionId=" + unionId +
                '}';
    }

    public static Builder builder() {
        return new KuOAuthConfig.Builder();
    }

    public final static class Builder {
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private boolean ignoreCheckState;
        private List<String> scopes;
        private boolean unionId;
        private String alipayPublicKey;

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder redirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public Builder ignoreCheckState(boolean ignoreCheckState) {
            this.ignoreCheckState = ignoreCheckState;
            return this;
        }

        public Builder scopes(List<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public Builder unionId(boolean unionId) {
            this.unionId = unionId;
            return this;
        }

        public Builder alipayPublicKey(String alipayPublicKey) {
            this.alipayPublicKey = alipayPublicKey;
            return this;
        }

        /**
         * 构建 KuOAuthConfig 对象
         * @return KuOAuthConfig 对象
         */
        public KuOAuthConfig build() {
            return new KuOAuthConfig(this);
        }
    }

}
