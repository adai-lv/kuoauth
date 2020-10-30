package com.kupug.kuoauth.model;

/**
 * <p>
 * oauth login data
 * </p>
 *
 * @author MaoHai.Lv
 * @since 1.0
 */
public final class KuOAuthLogin {

    private KuOAuthToken oAuthToken;
    private KuOAuthUser oAuthUser;

    private KuOAuthLogin() {
    }

    private KuOAuthLogin(Builder builder) {
        this.oAuthToken = builder.oAuthToken;
        this.oAuthUser = builder.oAuthUser;
    }

    public KuOAuthToken getoAuthToken() {
        return oAuthToken;
    }

    public KuOAuthUser getoAuthUser() {
        return oAuthUser;
    }

    @Override
    public String toString() {
        return "KuOAuthLogin{" +
                "oAuthToken=" + oAuthToken +
                ", oAuthUser=" + oAuthUser +
                '}';
    }

    public static Builder builder() {
        return new KuOAuthLogin.Builder();
    }

    public final static class Builder {

        private KuOAuthToken oAuthToken;
        private KuOAuthUser oAuthUser;

        public Builder oAuthToken(KuOAuthToken oAuthToken) {
            this.oAuthToken = oAuthToken;

            return this;
        }

        public Builder oAuthUser(KuOAuthUser oAuthUser) {
            this.oAuthUser = oAuthUser;

            return this;
        }

        public KuOAuthLogin build() {
            return new KuOAuthLogin(this);
        }
    }
}
