package com.kupug.kuoauth;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * <p>
 * AuthorizeUrl 访问后，回调时带的参数
 * </p>
 *
 * @author MaoHai.Lv
 * @since 1.0
 */
public final class KuOAuthCallback {

    /**
     * 访问 AuthorizeUrl 后, 回调时带的参数code
     *
     * 大部份平台: code
     * 支付宝登录: auth_code
     * 华为登录: authorization_code
     */
    @JsonAlias(value = {"code", "auth_code", "authorization_code"})
    private String code;

    /**
     * 用于和请求 AuthorizeUrl 前的 state 比较，防止CSRF攻击
     */
    private String state;

    public KuOAuthCallback() {
    }

    private KuOAuthCallback(Builder builder) {
        this.code = builder.code;
        this.state = builder.state;
    }

    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "KuOAuthCallback{" +
                "code='" + code + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public static Builder buider() {
        return new KuOAuthCallback.Builder();
    }

    public final static class Builder {

        private String code;
        private String state;

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public KuOAuthCallback build() {
            return new KuOAuthCallback(this);
        }
    }
}
