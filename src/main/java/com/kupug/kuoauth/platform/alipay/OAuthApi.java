package com.kupug.kuoauth.platform.alipay;

import com.kupug.kuoauth.platform.IOAuthApi;

/**
 * <p>
 * alipay oauth api
 * 官方文档{@see https://opendocs.alipay.com/apis/api_9/alipay.system.oauth.token}
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
public enum OAuthApi implements IOAuthApi {
    DEFAULT {
        @Override
        public String authorize() {
            return "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm";
        }

        @Override
        public String accessToken() {
            return "https://openapi.alipay.com/gateway.do";
        }

        @Override
        public String userInfo() {
            return "https://openapi.alipay.com/gateway.do";
        }
    }
}
