package com.kupug.kuoauth.platform.steam;

import com.fasterxml.jackson.databind.JsonNode;
import com.kupug.kuoauth.model.KuOAuthCallback;
import com.kupug.kuoauth.model.KuOAuthConfig;
import com.kupug.kuoauth.KuOAuthException;
import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.platform.OAuthPlatform;
import com.kupug.kuoauth.KuHttpClient;
import com.kupug.kuoauth.utils.JsonUtils;
import com.kupug.kuoauth.utils.StringUtils;

/**
 * <p>
 * steam 授权平台
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.2
 */
public final class SteamPlatform extends OAuthPlatform {

    private final static String NS_URL = "http://specs.openid.net/auth/2.0";
    private final static String NS_IDENTITY_URL = "http://specs.openid.net/auth/2.0/identifier_select";

    public SteamPlatform(KuOAuthConfig config) {
        super(config);
        this.oAuthApi = OAuthApi.DEFAULT;
    }

    /**
     * 返回带{@code state}参数的授权url，授权回调时会带上这个{@code state}
     *
     * @param state state 验证授权流程的参数，可以防止csrf
     * @return 返回授权地址
     */
    @Override
    public String authorize(String state) {

        return KuHttpClient.builder()
                .fromUrl(oAuthApi.authorize())
                .queryParam("openid.mode", "checkid_setup")
                .queryParam("openid.ns", NS_URL)
                .queryParam("openid.identity", NS_IDENTITY_URL)
                .queryParam("openid.claimed_id", NS_IDENTITY_URL)
                .queryParam("openid.return_to", config.getRedirectUri())
                .queryParam("openid.realm", config.getRedirectUri())
                .build();
    }

    @Override
    protected KuOAuthToken getAccessToken(KuOAuthCallback authCallback) {

        String steamId = authCallback.getCode()
                .replace("https://steamcommunity.com/openid/id/", "")
                .trim();

        if (StringUtils.isEmpty(steamId)) {
            throw new KuOAuthException("steam 授权登录失败: 授权code无效");
        }

        return KuOAuthToken.builder().openId(steamId).build();
    }

    @Override
    protected KuOAuthUser getUserInfo(KuOAuthToken authToken) {
        String responseBody = httpClientBuilder()
                .fromUrl(oAuthApi.userInfo())
                .queryParam("key", config.getClientSecret())
                .queryParam("steamids", authToken.getOpenId())
                .get();

        JsonNode responseObject = JsonUtils.parseObject(responseBody);

        if (!responseObject.has("response")) {
            throw new KuOAuthException("steam 授权登录失败: 授权code 或 clientSecret 无效");
        }

        JsonNode players = responseObject.get("response").get("players");
        if (players.isNull() || players.isEmpty()) {
            throw new KuOAuthException("steam 授权登录失败: 授权code 或 clientSecret 无效");
        }

        return OAuthUser.valueOf(players);
    }
}
