package com.kupug.kuoauth.platform.github;

import com.kupug.kuoauth.platform.IOAuthApi;

/**
 * <p>
 * github oauth api
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
enum OAuthApi implements IOAuthApi {
    DEFAULT {
        @Override
        public String authorize() {
            return "https://github.com/login/oauth/authorize";
        }

        @Override
        public String accessToken() {
            return "https://github.com/login/oauth/access_token";
        }

        @Override
        public String userInfo() {
            return "https://api.github.com/user";
        }
    }
}
