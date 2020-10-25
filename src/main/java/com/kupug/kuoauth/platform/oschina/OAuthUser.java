package com.kupug.kuoauth.platform.oschina;

import com.kupug.kuoauth.KuOAuthUser;
import com.kupug.kuoauth.enums.Gender;
import com.kupug.kuoauth.enums.Platform;
import com.kupug.kuoauth.platform.IOAuthUser;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * oschina oauth user
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
final class OAuthUser implements IOAuthUser {

    private String error;
    private String errorDescription;

    private String id;
    private String email;
    private String name;
    private String avatar;
    private String gender;
    private String location;
    private String url;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "OAuthUser{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender='" + gender + '\'' +
                ", location='" + location + '\'' +
                ", url='" + url + '\'' +
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
                .openId(this.getId())
                .username(this.getName())
                .nickname(this.getName())
                .avatar(this.getAvatar())
                .email(this.getEmail())
                .gender(Gender.getRealGender(this.getGender()))
                .location(this.getLocation())
                .platform(Platform.OSCHINA.name())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }
}
