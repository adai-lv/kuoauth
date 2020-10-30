package com.kupug.kuoauth.platform.qq;

import com.fasterxml.jackson.databind.JsonNode;
import com.kupug.kuoauth.model.KuOAuthCallback;
import com.kupug.kuoauth.model.KuOAuthConfig;
import com.kupug.kuoauth.KuOAuthException;
import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.platform.OAuthPlatform;
import com.kupug.kuoauth.model.Separator;
import com.kupug.kuoauth.utils.HttpClient;
import com.kupug.kuoauth.utils.JsonUtils;
import com.kupug.kuoauth.utils.UrlUtils;

import java.util.Map;

/**
 * <p>
 * QQ 授权平台
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public final class QqPlatform extends OAuthPlatform {

    public QqPlatform(KuOAuthConfig config) {
        super(config);
        this.oAuthApi = OAuthApi.DEFAULT;
        this.oAuthScopes = OAuthScope.values();
    }

    /**
     * 返回带{@code state}参数的授权url，授权回调时会带上这个{@code state}
     *
     * @param state state 验证授权流程的参数，可以防止csrf
     * @return 返回授权地址
     */
    @Override
    public String authorize(String state) {

        return HttpClient.builder()
                .fromUrl(oAuthApi.authorize())
                .queryParam("response_type", "code")
                .queryParam("client_id", config.getClientId())
                .queryParam("redirect_uri", config.getRedirectUri())
                .queryParam("scope", getOAuthScopes())
                .queryParam("state", getRealState(state))
                .build();
    }

    @Override
    public KuOAuthToken refresh(KuOAuthToken authToken) {

        String responseBody = HttpClient.builder()
                .fromUrl(oAuthApi.refresh())
                .queryParam("client_id", config.getClientId())
                .queryParam("client_secret", config.getClientSecret())
                .queryParam("refresh_token", authToken.getRefreshToken())
                .queryParam("grant_type", "refresh_token")
                .queryParam("redirect_uri", config.getRedirectUri())
                .get();

        return parseOAuthToken(responseBody);
    }

    /**
     * 获取 oauth scope 参数值，以","分隔多个 scope
     *
     * @return String
     */
    @Override
    protected String getOAuthScopes() {
        return separateOAuthScopes(Separator.COMMA.getValue());
    }

    @Override
    protected KuOAuthToken getAccessToken(KuOAuthCallback authCallback) {

        String responseBody = HttpClient.builder()
                .fromUrl(oAuthApi.accessToken())
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", authCallback.getCode())
                .queryParam("client_id", config.getClientId())
                .queryParam("client_secret", config.getClientSecret())
                .queryParam("redirect_uri", config.getRedirectUri())
                .get();

        return parseOAuthToken(responseBody);
    }

    /**
     * 获取QQ用户的个人资料
     *
     * @param authToken 通过{@link QqPlatform#getAccessToken(KuOAuthCallback)}获取到的{@code authToken}
     * @return KuOAuthUser
     */
    @Override
    protected KuOAuthUser getUserInfo(KuOAuthToken authToken) {

        authToken = getOpenId(authToken);

        String responseBody = HttpClient.builder()
                .fromUrl(oAuthApi.userInfo())
                .queryParam("access_token", authToken.getAccessToken())
                .queryParam("oauth_consumer_key", config.getClientId())
                .queryParam("openid", authToken.getOpenId())
                .get();

        OAuthUser oAuthUser = JsonUtils.parseObject(responseBody, OAuthUser.class);

        if (oAuthUser.getRet() != 0) {
            throw new KuOAuthException(oAuthUser.getMsg());
        }

        KuOAuthUser kuOAuthUser = oAuthUser.valueOf();

        return KuOAuthUser.builder(kuOAuthUser)
                .openId(authToken.getOpenId())
                .unionId(authToken.getUnionId())
                .build();
    }

    private KuOAuthToken parseOAuthToken(String responseBody) {

        if (responseBody.contains("error") && responseBody.contains("error_description")) {
            parseResponse(responseBody);
        }

        Map<String, String> responseMap = UrlUtils.valueOf(responseBody, true);

        return OAuthToken.valueOf(responseMap);
    }

    /**
     * 获取QQ用户的OpenId，支持自定义是否启用查询unionid的功能，如果启用查询unionid的功能，
     * 那就需要开发者先通过邮件申请unionid功能，参考链接 {@see http://wiki.connect.qq.com/unionid%E4%BB%8B%E7%BB%8D}
     *
     * @param authToken 通过{@link QqPlatform#getAccessToken(KuOAuthCallback)}获取到的{@code authToken}
     * @return openId
     */
    private KuOAuthToken getOpenId(KuOAuthToken authToken) {

        int bindUnionId = config.isUnionId() ? 1 : 0;

        String responseBody = HttpClient.builder()
                .fromUrl(oAuthApi.openId())
                .queryParam("access_token", authToken.getAccessToken())
                .queryParam("unionid", bindUnionId)
                .get();

        JsonNode object = parseResponse(responseBody);

        String unionId = object.has("unionid")
                ? object.get("unionid").asText()
                : "";

        return KuOAuthToken.builder(authToken)
                .openId(object.get("openid").asText())
                .unionId(unionId)
                .build();
    }

    private JsonNode parseResponse(String responseBody) {

        String responseData = responseBody.replaceAll("(?i:callback|\\(|\\))", "").trim();

        JsonNode object = JsonUtils.parseObject(responseData);
        if (object.has("error")) {
            String errorMsg = String.format("[%s]%s",
                    object.get("error").asText(), object.get("error_description").asText());
            throw new KuOAuthException(errorMsg);
        }

        return object;
    }
}
