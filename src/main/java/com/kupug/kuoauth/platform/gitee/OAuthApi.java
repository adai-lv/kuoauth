package com.kupug.kuoauth.platform.gitee;

import com.kupug.kuoauth.platform.IOAuthApi;

/**
 * <p>
 * gitee 平台的 scope 和 URI 配置
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
enum OAuthApi implements IOAuthApi {
    DEFAULT {
        @Override
        public String authorize() {
            return "https://gitee.com/oauth/authorize";
        }

        @Override
        public String accessToken() {
            return "https://gitee.com/oauth/token";
        }

        @Override
        public String userInfo() {
            return "https://gitee.com/api/v5/user";
        }
    }
}
