package com.kupug.kuoauth.platform.gitee;

import com.kupug.kuoauth.platform.IOAuthScope;

/**
 * <p>
 * gitee scope 配置项
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public enum OAuthScope implements IOAuthScope {

    /**
     * {@code scope} 含义，以{@code description} 为准
     */
    USER_INFO("user_info", "访问用户的个人信息、最新动态等", true),
    PROJECTS("projects", "查看、创建、更新用户的项目", false),
    PULL_REQUESTS("pull_requests", "查看、发布、更新用户的 Pull Request", false),
    ISSUES("issues", "查看、发布、更新用户的 Issue", false),
    NOTES("notes", "查看、发布、管理用户在项目、代码片段中的评论", false),
    KEYS("keys", "查看、部署、删除用户的公钥", false),
    HOOK("hook", "查看、部署、更新用户的 Webhook", false),
    GROUPS("groups", "查看、管理用户的组织以及成员", false),
    GISTS("gists", "查看、删除、更新用户的代码片段", false),
    ENTERPRISES("enterprises", "查看、管理用户的企业以及成员", false),
    EMAILS("emails", "查看用户的个人邮箱信息", false);

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
