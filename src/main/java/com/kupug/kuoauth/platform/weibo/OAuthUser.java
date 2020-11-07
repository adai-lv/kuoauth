package com.kupug.kuoauth.platform.weibo;

import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.model.Gender;
import com.kupug.kuoauth.model.Platform;
import com.kupug.kuoauth.utils.JsonUtils;

import java.util.Objects;

/**
 * <p>
 * Weibo oauth user
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
final class OAuthUser {

    private String error;
    private Integer errorCode;

    private Long id;
    private String idStr;
    private String name;
    private String screenName;
    private String url;
    private String profileUrl;
    private String profileImageUrl;
    private String province;
    private String city;
    private String location;
    private String description;
    private String gender;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "OAuthUser{" +
                "id=" + id +
                ", idStr='" + idStr + '\'' +
                ", name='" + name + '\'' +
                ", screenName='" + screenName + '\'' +
                ", url='" + url + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    /**
     * 转化统一的 OAuth User
     *
     * @return KuOAuthUser
     */
    public KuOAuthUser valueOf() {

        Gender gender = Objects.isNull(this.getGender())
                ? Gender.UNKNOWN
                : Gender.getRealGender(this.getGender());

        return KuOAuthUser.builder()
                .openId(this.getIdStr())
                .username(this.getName())
                .nickname(this.getScreenName())
                .avatar(this.getProfileImageUrl())
                .location(this.getLocation())
                .remark(this.getDescription())
                .gender(gender)
                .platform(Platform.WEIBO.name())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }
}
