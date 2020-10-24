package com.kupug.kuoauth.platform.google;

import com.kupug.kuoauth.platform.IOAuthApi;

/**
 * <p>
 * Google oauth api
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public enum OAuthApi implements IOAuthApi {

    DEFAULT {
        @Override
        public String authorize() {
            return "https://accounts.google.com/o/oauth2/v2/auth";
        }

        @Override
        public String accessToken() {
            return "https://www.googleapis.com/oauth2/v4/token";
        }

        @Override
        public String userInfo() {
            return "https://www.googleapis.com/oauth2/v3/userinfo";
        }
    }
}
