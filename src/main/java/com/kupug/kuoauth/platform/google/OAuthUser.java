package com.kupug.kuoauth.platform.google;

import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.model.Gender;
import com.kupug.kuoauth.model.Platform;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * Google oauth user
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
final class OAuthUser {

    private String error;
    private String errorDescription;

    private String sub;
    private String name;
    private String givenName;
    private String familyName;
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

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
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
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
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
