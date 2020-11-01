package com.kupug.kuoauth.platform.wechat;

import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.model.Gender;
import com.kupug.kuoauth.model.Platform;
import com.kupug.kuoauth.utils.JsonUtils;

import java.util.List;

/**
 * <p>
 * WeChat oauth user
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
final class OAuthUser {

    private String errcode;
    private String errmsg;

    private String nickname;
    private String headimgurl;
    private String sex;
    private String country;
    private String province;
    private String city;
    private String language;
    private String openid;
    private String unionid;
    private List<String> privilege;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<String> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<String> privilege) {
        this.privilege = privilege;
    }

    @Override
    public String toString() {
        return "OAuthUser{" +
                "nickname='" + nickname + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", sex='" + sex + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", language='" + language + '\'' +
                ", openid='" + openid + '\'' +
                ", unionid='" + unionid + '\'' +
                ", privilege=" + privilege +
                '}';
    }

    /**
     * 转化统一的 OAuth User
     *
     * @return KuOAuthUser
     */
    public KuOAuthUser valueOf() {

        String location = String.format("%s-%s-%s", this.getCountry(), this.getProvince(), this.getCity());

        Gender gender;
        switch (this.getSex()) {
            case "0":
                gender = Gender.MALE;
                break;
            case "1":
                gender = Gender.FEMALE;
                break;
            default:
                gender = Gender.UNKNOWN;
        }

        return KuOAuthUser.builder()
                .openId(this.getOpenid())
                .unionId(this.getUnionid())
                .username(this.getNickname())
                .nickname(this.getNickname())
                .avatar(this.getHeadimgurl())
                .location(location)
                .gender(gender)
                .platform(Platform.WECHAT.name())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }
}
