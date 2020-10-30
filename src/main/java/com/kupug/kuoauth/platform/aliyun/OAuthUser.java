package com.kupug.kuoauth.platform.aliyun;

import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.model.Gender;
import com.kupug.kuoauth.model.Platform;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * 阿里云 oauth user
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.1
 */
final class OAuthUser {

    /**
     * 登录用户的UID
     * scope=openid
     */
    private String sub;

    /**
     * 阿里云账号的登录名
     * scope=profile
     */
    private String loginName;

    /**
     * 登录用户的显示名称
     * scope=profile
     */
    private String name;

    /**
     * 登录用户的UPN（User Principal Name）
     * RAM用户请求时才会返回该参数
     * scope=profile
     */
    private String upn;

    /**
     * 登录用户所属的阿里云账号ID
     * scope=aliuid
     */
    private String aid;

    /**
     * 登录用户的阿里云账号ID
     * scope=aliuid
     */
    private String uid;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpn() {
        return upn;
    }

    public void setUpn(String upn) {
        this.upn = upn;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "OAuthUser{" +
                "sub='" + sub + '\'' +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", upn='" + upn + '\'' +
                ", aid='" + aid + '\'' +
                ", uid='" + uid + '\'' +
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
                .username(this.getLoginName())
                .nickname(this.getName())
                .gender(Gender.UNKNOWN)
                .platform(Platform.ALIYUN.name())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }
}
