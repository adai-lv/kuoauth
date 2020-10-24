package com.kupug.kuoauth.platform.aliyun;

import com.kupug.kuoauth.platform.IOAuthScope;

/**
 * <p>
 * 阿里云 scope 配置项
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
public enum OAuthScope implements IOAuthScope {

    /**
     * {@code scope} 含义，以{@code description} 为准
     */
    OPEN_ID("openid", "获取登录用户的基本信息", true),
    PROFILE("profile", "用户名称等个人信息", true),
    ALIUID("aliuid", "阿里云颁发的唯一用户标识符", false);

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
