package com.kupug.kuoauth.platform.dingtalk;

import com.fasterxml.jackson.databind.JsonNode;
import com.kupug.kuoauth.KuOAuthUser;
import com.kupug.kuoauth.platform.IOAuthUser;
import com.kupug.kuoauth.enums.Gender;
import com.kupug.kuoauth.enums.Platform;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * dingtalk oauth user
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
final class OAuthUser implements IOAuthUser {

    private String nick;
    private String openid;
    private String unionid;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
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

    /**
     * 转化统一的 OAuth User
     *
     * @return KuOAuthUser
     */
    @Override
    public KuOAuthUser valueOf() {
        return KuOAuthUser.builder()
                .openId(this.getOpenid())
                .unionId(this.getUnionid())
                .username(this.getNick())
                .nickname(this.getNick())
                .gender(Gender.UNKNOWN)
                .platform(Platform.DINGTALK.name())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }

    public static KuOAuthUser valueOf(JsonNode userInfo) {

        OAuthUser oAuthUser = new OAuthUser();

        if (userInfo.has("nick")) {
            oAuthUser.setNick(userInfo.get("nick").asText());
        }

        if (userInfo.has("openid")) {
            oAuthUser.setOpenid(userInfo.get("openid").asText());
        }

        if (userInfo.has("unionid")) {
            oAuthUser.setUnionid(userInfo.get("unionid").asText());
        }

        return oAuthUser.valueOf();
    }
}
