package com.kupug.kuoauth.platform.google;

import com.kupug.kuoauth.KuOAuthUser;
import com.kupug.kuoauth.platform.IOAuthUser;
import com.kupug.kuoauth.enums.Gender;
import com.kupug.kuoauth.enums.Platform;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * Google oauth user
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
public final class OAuthUser implements IOAuthUser {

    private String error;
    private String errorDescription;

    private String sub;
    private String name;
    private String email;
    private String picture;
    private String locale;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public String toString() {
        return "OAuthUser{" +
                "sub='" + sub + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", picture='" + picture + '\'' +
                ", locale='" + locale + '\'' +
                '}';
    }

    /**
     * 转化统一的 OAuth User
     *
     * @return KuOAuthUser
     */
    @Override
    public KuOAuthUser valueOf() {

        return KuOAuthUser.builder()
                .openId(this.getSub())
                .username(this.getEmail())
                .nickname(this.getName())
                .avatar(this.getPicture())
                .location(this.getLocale())
                .email(this.getEmail())
                .gender(Gender.UNKNOWN)
                .platform(Platform.GOOGLE.name())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }
}
