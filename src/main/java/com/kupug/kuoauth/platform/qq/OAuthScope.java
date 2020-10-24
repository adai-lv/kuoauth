package com.kupug.kuoauth.platform.qq;

import com.kupug.kuoauth.platform.IOAuthScope;

/**
 * <p>
 * QQ oauth scope
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public enum OAuthScope implements IOAuthScope {
    /**
     * {@code scope} 含义，以{@code description} 为准
     */
    GET_USER_INFO("get_user_info", "获取登录用户的昵称、头像、性别", true),
    GET_VIP_INFO("get_vip_info", "获取QQ会员的基本信息", false),
    GET_VIP_RICH_INFO("get_vip_rich_info", "获取QQ会员的高级信息", false),
    LIST_ALBUM("list_album", "获取用户QQ空间相册列表", false),
    UPLOAD_PIC("upload_pic", "上传一张照片到QQ空间相册", false),
    ADD_ALBUM("add_album", "在用户的空间相册里，创建一个新的个人相册", false),
    LIST_PHOTO("list_photo", "获取用户QQ空间相册中的照片列表", false);

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
