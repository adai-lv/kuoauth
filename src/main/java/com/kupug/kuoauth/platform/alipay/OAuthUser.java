package com.kupug.kuoauth.platform.alipay;

import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.model.Gender;
import com.kupug.kuoauth.model.Platform;
import com.kupug.kuoauth.utils.JsonUtils;
import com.kupug.kuoauth.utils.StringUtils;

/**
 * <p>
 * 支付宝 oauth user
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
final class OAuthUser {

    /**
     * 登录用户的UID
     */
    private String userId;
    private String userName;
    private String nickName;
    private String avatar;
    private String province;
    private String city;
    private String gender;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "OAuthUser{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    /**
     * 转化统一的 OAuth User
     *
     * @return KuOAuthUser
     */
    public KuOAuthUser valueOf() {
        String location = String.format("%s-%s", this.getProvince(), this.getCity());
        String userName = StringUtils.isEmpty(this.getUserName())
                ? this.getNickName()
                : this.getUserName();

        return KuOAuthUser.builder()
                .openId(this.getUserId())
                .username(userName)
                .nickname(this.getNickName())
                .avatar(this.getAvatar())
                .gender(Gender.getRealGender(this.getGender()))
                .location(location)
                .platform(Platform.ALIPAY.name())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }

    public static KuOAuthUser valueOf(AlipayUserInfoShareResponse values) {

        OAuthUser user = new OAuthUser();
        user.setUserId(values.getUserId());
        user.setUserName(values.getUserName());
        user.setNickName(values.getNickName());
        user.setProvince(values.getProvince());
        user.setCity(values.getCity());
        user.setAvatar(values.getAvatar());
        user.setGender(values.getGender());

        return user.valueOf();
    }
}
