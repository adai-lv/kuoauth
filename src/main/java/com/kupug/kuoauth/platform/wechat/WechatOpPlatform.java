package com.kupug.kuoauth.platform.wechat;

import com.kupug.kuoauth.KuOAuthConfig;
import com.kupug.kuoauth.platform.weibo.OAuthScope;

/**
 * <p>
 * 微信开放平台授权
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public final class WechatOpPlatform extends WechatPlatform {

    public WechatOpPlatform(KuOAuthConfig config) {
        super(config);
        this.oAuthApi = OAuthApi.OPEN_PLATFORM;
        this.oAuthScopes = OAuthScope.values();
    }
}
