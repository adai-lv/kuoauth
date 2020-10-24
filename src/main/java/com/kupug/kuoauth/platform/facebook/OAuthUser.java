package com.kupug.kuoauth.platform.facebook;

import com.kupug.kuoauth.KuOAuthUser;
import com.kupug.kuoauth.platform.IOAuthUser;
import com.kupug.kuoauth.enums.Gender;
import com.kupug.kuoauth.enums.Platform;
import com.kupug.kuoauth.utils.JsonUtils;

import java.util.Objects;

/**
 * <p>
 * Facebook oauth user
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
final class OAuthUser implements IOAuthUser {

    private String id;
    private String name;
    private String gender;
    private String birthday;
    private String locale;
    private String email;
    private String devices;
    private String picture;
    private String link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "OAuthUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", locale='" + locale + '\'' +
                ", email='" + email + '\'' +
                ", devices='" + devices + '\'' +
                ", picture='" + picture + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    /**
     * 转化统一的 OAuth User
     *
     * @return KuOAuthUser
     */
    @Override
    public KuOAuthUser valueOf() {

        Gender gender = Objects.isNull(this.getGender())
                ? Gender.UNKNOWN
                : Gender.getRealGender(this.getGender());

        return KuOAuthUser.builder()
                .openId(this.getId())
                .username(this.getName())
                .nickname(this.getName())
                .email(this.getEmail())
                .avatar("")
                .location(this.getLocale())
                .gender(gender)
                .platform(Platform.FACEBOOK.name())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }
}
