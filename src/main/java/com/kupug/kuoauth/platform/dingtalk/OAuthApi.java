package com.kupug.kuoauth.platform.dingtalk;

import com.kupug.kuoauth.platform.IOAuthApi;

/**
 * <p>
 * dingtalk oauth api
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
enum OAuthApi implements IOAuthApi {
    ACCOUNT {
        @Override
        public String authorize() {
            return "https://oapi.dingtalk.com/connect/oauth2/sns_authorize";
        }

        @Override
        public String userInfo() {
            return "https://oapi.dingtalk.com/sns/getuserinfo_bycode";
        }
    },
    QRCONNECT {
        @Override
        public String authorize() {
            return "https://oapi.dingtalk.com/connect/qrconnect";
        }

        @Override
        public String userInfo() {
            return "https://oapi.dingtalk.com/sns/getuserinfo_bycode";
        }
    }
}
