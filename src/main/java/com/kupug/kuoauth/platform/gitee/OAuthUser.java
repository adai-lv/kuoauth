package com.kupug.kuoauth.platform.gitee;

import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.model.Gender;
import com.kupug.kuoauth.model.Platform;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * gitee oauth user
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
final class OAuthUser {

    private String id;
    private String login;
    private String avatarUrl;
    private String blog;
    private String name;
    private String company;
    private String address;
    private String email;
    private String bio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "OAuthUser{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", blog='" + blog + '\'' +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }

    /**
     * 转化统一的 OAuth User
     *
     * @return KuOAuthUser
     */
    public KuOAuthUser valueOf() {
        return KuOAuthUser.builder()
                .openId(this.getId())
                .username(this.getLogin())
                .nickname(this.getName())
                .avatar(this.getAvatarUrl())
                .email(this.getEmail())
                .gender(Gender.UNKNOWN)
                .location(this.getAddress())
                .remark(this.getBio())
                .platform(Platform.GITEE.name())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }
}
