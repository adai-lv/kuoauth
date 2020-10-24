package com.kupug.kuoauth.platform.baidu;

import com.kupug.kuoauth.platform.IOAuthApi;

/**
 * <p>
 * 百度平台的 URI 配置
 * 官方文档{@see http://developer.baidu.com/wiki/index.php?title=docs/oauth}
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
enum OAuthApi implements IOAuthApi {
    DEFAULT {
        @Override
        public String authorize() {
            return "https://openapi.baidu.com/oauth/2.0/authorize";
        }

        @Override
        public String accessToken() {
            return "https://openapi.baidu.com/oauth/2.0/token";
        }

        @Override
        public String userInfo() {
            return "https://openapi.baidu.com/rest/2.0/passport/users/getInfo";
        }

        @Override
        public String revoke() {
            return "https://openapi.baidu.com/rest/2.0/passport/auth/revokeAuthorization";
        }

        @Override
        public String refresh() {
            return "https://openapi.baidu.com/oauth/2.0/token";
        }
    }
}
