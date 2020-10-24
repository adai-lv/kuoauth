package com.kupug.kuoauth.platform.wechat;

import com.kupug.kuoauth.KuOAuthConfig;
import com.kupug.kuoauth.platform.weibo.OAuthScope;

/**
 * <p>
 * 微信公众号平台授权
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public final class WechatMpPlatform extends WechatPlatform {

    public WechatMpPlatform(KuOAuthConfig config) {
        super(config);
        this.oAuthApi = OAuthApi.MP_PLATFORM;
        this.oAuthScopes = OAuthScope.values();
    }
}
