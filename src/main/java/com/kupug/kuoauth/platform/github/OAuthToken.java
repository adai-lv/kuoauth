package com.kupug.kuoauth.platform.github;

import com.kupug.kuoauth.KuOAuthToken;
import com.kupug.kuoauth.platform.IOAuthToken;
import com.kupug.kuoauth.utils.JsonUtils;

import java.util.Map;

/**
 * <p>
 * github oauth token
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
final class OAuthToken implements IOAuthToken {

    private String accessToken;
    private String scope;
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "GithubOAuthToken{" +
                "accessToken='" + accessToken + '\'' +
                ", scope='" + scope + '\'' +
                ", tokenType='" + tokenType + '\'' +
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
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }

    public static KuOAuthToken valueOf(Map<String, String> values) {

        OAuthToken token = new OAuthToken();
        token.setAccessToken(values.get("access_token"));
        token.setScope(values.get("scope"));
        token.setTokenType(values.get("token_type"));

        return token.valueOf();
    }
}
