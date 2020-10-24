package com.kupug.kuoauth.platform.wechat;

import com.kupug.kuoauth.platform.IOAuthScope;

/**
 * <p>
 * WeChatOp oauth scope
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public enum OpOAuthScope implements IOAuthScope {

    /**
     * {@code scope} 含义，以{@code description} 为准
     */
    SNS_LOGIN("snsapi_login", "获取用户身份和基本信息。", true);

    private String scope;
    private String description;
    private boolean isDefault;

    OpOAuthScope(String scope, String description, boolean isDefault) {
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
