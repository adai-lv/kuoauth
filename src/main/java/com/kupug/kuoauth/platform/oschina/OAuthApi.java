package com.kupug.kuoauth.platform.oschina;

import com.kupug.kuoauth.platform.IOAuthApi;

/**
 * <p>
 * oschina 平台的 URI 配置
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
enum OAuthApi implements IOAuthApi {
    DEFAULT {
        @Override
        public String authorize() {
            return "https://www.oschina.net/action/oauth2/authorize";
        }

        @Override
        public String accessToken() {
            return "https://www.oschina.net/action/openapi/token";
        }

        @Override
        public String userInfo() {
            return "https://www.oschina.net/action/openapi/user";
        }
    }
}
