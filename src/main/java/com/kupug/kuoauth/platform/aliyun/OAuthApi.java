package com.kupug.kuoauth.platform.aliyun;

import com.kupug.kuoauth.platform.IOAuthApi;

/**
 * <p>
 * 阿里云平台的 URI 配置
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
enum OAuthApi implements IOAuthApi {
    DEFAULT {
        @Override
        public String authorize() {
            return "https://signin.aliyun.com/oauth2/v1/auth";
        }

        @Override
        public String accessToken() {
            return "https://oauth.aliyun.com/v1/token";
        }

        @Override
        public String userInfo() {
            return "https://oauth.aliyun.com/v1/userinfo";
        }

        @Override
        public String refresh() {
            return "https://oauth.aliyun.com/v1/token";
        }

        @Override
        public String revoke() {
            return "https://oauth.aliyun.com/v1/revoke";
        }
    }
}
