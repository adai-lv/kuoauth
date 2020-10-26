package com.kupug.kuoauth.platform.alipay;

import com.kupug.kuoauth.platform.IOAuthScope;

/**
 * <p>
 * alipay oauth scope
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public enum OAuthScope implements IOAuthScope {
    /**
     * {@code scope} 含义，以{@code description} 为准
     */
    AUTH_USER("auth_user", "获取登录用户的昵称、头像、性别", true);

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
