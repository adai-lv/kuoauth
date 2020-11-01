package com.kupug.kuoauth.platform.steam;

import com.kupug.kuoauth.platform.IOAuthApi;

/**
 * <p>
 * steam 平台的 URI 配置
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.2
 */
enum OAuthApi implements IOAuthApi {
    DEFAULT {
        @Override
        public String authorize() {
            return "https://steamcommunity.com/openid/login";
        }

        @Override
        public String userInfo() {
            return "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/";
        }
    }
}
