package com.kupug.kuoauth.platform.facebook;

import com.kupug.kuoauth.platform.IOAuthApi;

/**
 * <p>
 * facebook oauth api
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
enum OAuthApi implements IOAuthApi {

    DEFAULT {
        @Override
        public String authorize() {
            return "https://www.facebook.com/v3.3/dialog/oauth";
        }

        @Override
        public String accessToken() {
            return "https://graph.facebook.com/v3.3/oauth/access_token";
        }

        @Override
        public String userInfo() {
            return "https://graph.facebook.com/v3.3/me";
        }
    }
}
