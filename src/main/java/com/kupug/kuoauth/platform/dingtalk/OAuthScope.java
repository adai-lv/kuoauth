package com.kupug.kuoauth.platform.dingtalk;

import com.kupug.kuoauth.platform.IOAuthScope;

/**
 * <p>
 * dingtalk oauth scope
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public enum OAuthScope implements IOAuthScope {
    /**
     * {@code scope} 含义，以{@code description} 为准
     */
    SNS_LOGIN("snsapi_login", "获取用户身份（无手机号和企业相关信息）。", true);

    private String scope;
    private String description;
    private boolean isDefault;

    OAuthScope(String scope, String description, boolean isDefault) {
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
