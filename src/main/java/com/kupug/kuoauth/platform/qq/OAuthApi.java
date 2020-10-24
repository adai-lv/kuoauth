package com.kupug.kuoauth.platform.qq;

import com.kupug.kuoauth.platform.IOAuthApi;

/**
 * <p>
 * QQ oauth api
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
enum OAuthApi implements IOAuthApi {
    DEFAULT {
        @Override
        public String authorize() {
            return "https://graph.qq.com/oauth2.0/authorize";
        }

        @Override
        public String accessToken() {
            return "https://graph.qq.com/oauth2.0/token";
        }

        @Override
        public String userInfo() {
            return "https://graph.qq.com/user/get_user_info";
        }

        @Override
        public String refresh() {
            return "https://graph.qq.com/oauth2.0/token";
        }

        @Override
        public String openId() { return "https://graph.qq.com/oauth2.0/me"; }
    }
}
