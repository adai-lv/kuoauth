package com.kupug.kuoauth.platform.baidu;

import com.kupug.kuoauth.KuOAuthUser;
import com.kupug.kuoauth.enums.Gender;
import com.kupug.kuoauth.enums.Platform;
import com.kupug.kuoauth.platform.IOAuthUser;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * 百度平台 oauth user
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
final class OAuthUser implements IOAuthUser {

    private Integer errorCode;
    private String errorMsg;

    /**
     * 当前登录用户的openid
     */
    private String  openid;

    /**
     * 当前登录用户的用户名，值可能为空
     */
    private String username;

    /**
     * 是否绑定手机，1是，0否
     */
    private String isBindMobile;

    /**
     * 是否有真实姓名，1是，0否
     */
    private String isRealname;

    /**
     * 当前登录用户的头像
     */
    private String portrait;

    /**
     * 自我简介，可能为空
     */
    private String userdetail;

    /**
     * 生日，以yyyy-mm-dd格式显示
     */
    private String birthday;

    /**
     * 婚姻状况
     */
    private String marriage;

    /**
     * 性别。"1"表示男，"0"表示女
     */
    private String sex;

    /**
     * 血型
     */
    private String blood;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIsBindMobile() {
        return isBindMobile;
    }

    public void setIsBindMobile(String isBindMobile) {
        this.isBindMobile = isBindMobile;
    }

    public String getIsRealname() {
        return isRealname;
    }

    public void setIsRealname(String isRealname) {
        this.isRealname = isRealname;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getUserdetail() {
        return userdetail;
    }

    public void setUserdetail(String userdetail) {
        this.userdetail = userdetail;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    @Override
    public String toString() {
        return "OAuthUser{" +
                "openid='" + openid + '\'' +
                ", username='" + username + '\'' +
                ", isBindMobile='" + isBindMobile + '\'' +
                ", isRealname='" + isRealname + '\'' +
                ", portrait='" + portrait + '\'' +
                ", userdetail='" + userdetail + '\'' +
                ", birthday='" + birthday + '\'' +
                ", marriage='" + marriage + '\'' +
                ", sex='" + sex + '\'' +
                ", blood='" + blood + '\'' +
                '}';
    }

    /**
     * 转化统一的 OAuth User
     *
     * @return KuOAuthUser
     */
    @Override
    public KuOAuthUser valueOf() {

        String avatar = String.format("http://himg.bdimg.com/sys/portrait/item/%s.jpg", this.getPortrait());
        return KuOAuthUser.builder()
                .openId(this.getOpenid())
                .username(this.getUsername())
                .nickname(this.getUsername())
                .avatar(avatar)
                .remark(this.getUserdetail())
                .gender(Gender.getRealGender(this.getSex()))
                .platform(Platform.BAIDU.name())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }
}
