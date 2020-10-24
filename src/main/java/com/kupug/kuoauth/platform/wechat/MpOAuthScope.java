package com.kupug.kuoauth.platform.wechat;

import com.kupug.kuoauth.platform.IOAuthScope;

/**
 * <p>
 * WeChatMp oauth scope
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public enum MpOAuthScope implements IOAuthScope {

    /**
     * {@code scope} 含义，以{@code description} 为准
     */
    SNSAPI_USERINFO("snsapi_userinfo", "弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息", true),
    SNSAPI_BASE("snsapi_base", "不弹出授权页面，直接跳转，只能获取用户openid", false);

    private String scope;
    private String description;
    private boolean isDefault;

    MpOAuthScope(String scope, String description, boolean isDefault) {
        this.scope = scope;
        this.description = description;
        this.isDefault = isDefault;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public boolean isDefault() {
        return this.isDefault;
    }
}
