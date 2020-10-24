package com.kupug.kuoauth;

import com.kupug.kuoauth.enums.Gender;
import com.kupug.kuoauth.utils.StringUtils;

/**
 * <p>
 * oauth user info
 * </p>
 *
 * @author MaoHai.Lv
 * @since 1.0
 */
public final class KuOAuthUser {

    /**
     * 用户第三方平台的唯一id。
     * 在调用方集成该组件时，可以用kuuid + platform 唯一确定一个用户
     */
    private String openId;

    /**
     * 同一个第三方平台多个应用关联标识
     */
    private String unionId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 位置
     */
    private String location;

    /**
     * 用户备注（用户个人介绍）
     */
    private String remark;

    /**
     * 用户来源
     */
    private String platform;

    /**
     * 第三方平台返回的原始用户信息
     */
    private String rawInfo;

    private KuOAuthUser() {
    }

    private KuOAuthUser(Builder builder) {
        this.openId = format(builder.openId);
        this.unionId = format(builder.unionId);
        this.email = format(builder.email);
        this.username = format(builder.username);
        this.nickname = format(builder.nickname);
        this.avatar = format(builder.avatar);
        this.gender = builder.gender;
        this.location = format(builder.location);
        this.remark = format(builder.remark);
        this.platform = builder.platform;
        this.rawInfo = builder.rawInfo;
    }

    public String getOpenId() {
        return openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public String getRemark() {
        return remark;
    }

    public String getPlatform() {
        return platform;
    }

    public String getRawInfo() {
        return rawInfo;
    }

    @Override
    public String toString() {
        return "KuOAuthUser{" +
                "openId='" + openId + '\'' +
                ", unionId='" + unionId + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", location='" + location + '\'' +
                ", remark='" + remark + '\'' +
                ", platform='" + platform + '\'' +
                ", rawInfo='" + rawInfo + '\'' +
                '}';
    }

    private String format(String rawValue) {
        return StringUtils.isEmpty(rawValue) ? "" : rawValue;
    }

    public static Builder builder() {
        return new KuOAuthUser.Builder();
    }

    public static Builder builder(KuOAuthUser oAuthUser) {
        KuOAuthUser.Builder builder = new KuOAuthUser.Builder();

        builder.openId = oAuthUser.openId;
        builder.unionId = oAuthUser.unionId;
        builder.email = oAuthUser.email;
        builder.username = oAuthUser.username;
        builder.nickname = oAuthUser.nickname;
        builder.avatar = oAuthUser.avatar;
        builder.gender = oAuthUser.gender;
        builder.location = oAuthUser.location;
        builder.remark = oAuthUser.remark;
        builder.platform = oAuthUser.platform;
        builder.rawInfo = oAuthUser.rawInfo;

        return builder;
    }

    public final static class Builder {

        private String openId;
        private String unionId;
        private String username;
        private String nickname;
        private String avatar;
        private String email;
        private Gender gender;
        private String location;
        private String remark;
        private String platform;
        private String rawInfo;

        public Builder openId(String openId) {
            this.openId = openId;
            return this;
        }

        public Builder unionId(String unionId) {
            this.unionId = unionId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;

            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;

            return this;
        }

        public Builder avatar(String avatar) {
            this.avatar = avatar;

            return this;
        }

        public Builder email(String email) {
            this.email = email;

            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;

            return this;
        }

        public Builder location(String location) {
            this.location = location;

            return this;
        }

        public Builder remark(String remark) {
            this.remark = remark;

            return this;
        }

        public Builder platform(String platform) {
            this.platform = platform;

            return this;
        }

        public Builder rawInfo(String rawInfo) {
            this.rawInfo = rawInfo;

            return this;
        }

        public KuOAuthUser build() {
            return new KuOAuthUser(this);
        }
    }
}
